package com.yingke.shengtai.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.EMValueCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.util.HanziToPinyin;
import com.easemob.util.NetUtils;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.api.AsyncOkhttp;
import com.yingke.shengtai.db.User;
import com.yingke.shengtai.fragment.CenterFragment;
import com.yingke.shengtai.fragment.ChatAllHistoryFragment;
import com.yingke.shengtai.fragment.GuideFragment;
import com.yingke.shengtai.fragment.OnlineFragment;
import com.yingke.shengtai.fragment.SearchFragment;
import com.yingke.shengtai.moudle.UserDao;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.DemoHXSDKHelper;
import com.yingke.shengtai.utils.HXSDKHelper;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.view.NavBottomView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanyiheng on 15-8-15.
 */
public class CustomerMainActivity extends FragmentActivity implements EMEventListener {
    private NavBottomView navbottom;

    private Fragment oldFragment;
    private MyConnectionListener connectionListener = null;
    private static boolean isExit;
    // 账号在别处登录
    public boolean isConflict = false;
    // 未读消息textview
    private TextView unreadLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getBoolean(Constant.ACCOUNT_REMOVED, false)) {
            // 防止被移除后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
            // 三个fragment里加的判断同理
            DemoHXSDKHelper.getInstance().logout(true,null);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        } else if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            // 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
            // 三个fragment里加的判断同理
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        setContentView(R.layout.activity_cusmain);
        if(savedInstanceState == null){
            String str = getIntent().getStringExtra("actions");
            if(TextUtils.equals("actions", str)){
                try {
                    sendStatisticData(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        initUi();
        if (getIntent().getBooleanExtra("conflict", false) && !isConflictDialogShow) {
            showConflictDialog();
        }
        registerReceiver();
        initPage();
    }

    private void sendStatisticData(boolean bol) throws Exception{
        String phone = MyApplication.getInstance().getUserInfor().getUserdetail().getMobile();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String t=format.format(new Date());
        String url1 = "n=" + phone + "&u=" + phone + "&t=" + t;
        String url2 = url1 + "&l=" + url1.length();
        String url = "http://2.2.2.1/wx.html?href=" + toHexString(url2) + "&id=YingKe";
        AsyncOkhttp.getRequest(0, url, null);
        if(bol){
            ShowDialog(url2, url);
        }

    }

    public  String toHexString(String s)
    {
        String str="";
        for (int i=0;i<s.length();i++)
        {
            int ch = (int)s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    private void ShowDialog(String url2, String url) {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(CustomerMainActivity.this);
        dialog.setTitle("WIFI");
        dialog.setMessage("请求参数:  " + url2 + "\n\n" + "请求链接:  " + url);
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dialog = null;
            }
        });
        dialog.setCancelable(false);
        dialog.create().show();
    }

    private void initUi() {
        navbottom = (NavBottomView) findViewById(R.id.activity_main_navbottom);
        unreadLabel = (TextView)navbottom.findViewById(R.id.unread_msg_number);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (getIntent().getBooleanExtra("conflict", false) && !isConflictDialogShow) {
            showConflictDialog();
        }
    }

    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    try {
                        sendStatisticData(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    private void registerReceiver() {
        registerReceiver(mHomeKeyEventReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        exit();
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次后退键退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            try {
                sendStatisticData(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.finish();
        }
    }

    private static Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        super.onSaveInstanceState(outState);
    }

    /**
     * 初始化页面
     */
    private void initPage() {
        navbottom = (NavBottomView) findViewById(R.id.activity_main_navbottom);
        navbottom.setOnSelectedListener(new NavBottomView.OnSelectedListener() {
            @Override
            public void onSelected(int position) {
                switch (position) {
                    case NavBottomView.NAVBOTTOM_GUIDE:
                        if (oldFragment instanceof GuideFragment) {
                            return;
                        }
                        setChangePage(GuideFragment.TAG, false);
                        break;
                    case NavBottomView.NAVBOTTOM_CENTER:
                        if (oldFragment instanceof CenterFragment) {
                            return;
                        }
                        setChangePage(CenterFragment.TAG, false);
                        break;
                    case NavBottomView.NAVBOTTOM_ONLINE:
                        if (oldFragment instanceof OnlineFragment) {
                            return;
                        }
                        setChangePage(OnlineFragment.TAG, false);
                        break;
                    case NavBottomView.NAVBOTTOM_SEARCHER:
                        if (oldFragment instanceof SearchFragment) {
                            return;
                        }
                        setChangePage(SearchFragment.TAG, false);
                        break;
                }
            }
        });
        setChangePage(GuideFragment.TAG, true);
        connectionListener = new MyConnectionListener();
        EMChatManager.getInstance().addConnectionListener(connectionListener);
    }

    private void setChangePage(String tag, boolean homepage) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (homepage && currentFragment != null) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments != null) {
                for (int i = 0; i < fragments.size(); i++) {
                    ft.remove(fragments.get(i));
                }
            }
            currentFragment = null;
        }
        if (oldFragment != null) {
            ft.hide(oldFragment);
        }
        if (currentFragment == null) {
            if (tag.equals(GuideFragment.TAG)) {
                currentFragment = new GuideFragment();
            } else if (tag.equals(CenterFragment.TAG)) {
                currentFragment = new CenterFragment();
            } else if (tag.equals(OnlineFragment.TAG)) {
                currentFragment = new ChatAllHistoryFragment();
            } else if (tag.equals(SearchFragment.TAG)) {
                currentFragment = new SearchFragment();
            }
            ft.add(R.id.fragment_container, currentFragment, tag);
        } else {
            ft.show(currentFragment);
        }
        oldFragment = currentFragment;
        try {
            ft.commit();
        } catch (IllegalStateException e) {
            //Exception: Can not perform this action after onSaveInstanceState
            //see http://stackoverflow.com/questions/16265733/failure-delivering-result-onactivityforresult
            ft.commitAllowingStateLoss();
        }
        this.getSupportFragmentManager().executePendingTransactions();
    }

    static void asyncFetchContactsFromServer() {
        HXSDKHelper.getInstance().asyncFetchContactsFromServer(new EMValueCallBack<List<String>>() {

            @Override
            public void onSuccess(List<String> usernames) {
                Context context = HXSDKHelper.getInstance().getAppContext();

                System.out.println("----------------" + usernames.toString());
                Map<String, User> userlist = new HashMap<String, User>();
                for (String username : usernames) {
                    User user = new User();
                    user.setUsername(username);
                    setUserHearder(username, user);
                    userlist.put(username, user);
                }
                // 添加user"申请与通知"
                User newFriends = new User();
                newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
                String strChat = context.getString(R.string.Application_and_notify);
                newFriends.setNick(strChat);

                userlist.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
                // 添加"群聊"
                User groupUser = new User();
                String strGroup = context.getString(R.string.group_chat);
                groupUser.setUsername(Constant.GROUP_USERNAME);
                groupUser.setNick(strGroup);
                groupUser.setHeader("");
                userlist.put(Constant.GROUP_USERNAME, groupUser);

                // 添加"聊天室"
                User chatRoomItem = new User();
                String strChatRoom = context.getString(R.string.chat_room);
                chatRoomItem.setUsername(Constant.CHAT_ROOM);
                chatRoomItem.setNick(strChatRoom);
                chatRoomItem.setHeader("");
                userlist.put(Constant.CHAT_ROOM, chatRoomItem);

                // 添加"Robot"
                User robotUser = new User();
                String strRobot = context.getString(R.string.robot_chat);
                robotUser.setUsername(Constant.CHAT_ROBOT);
                robotUser.setNick(strRobot);
                robotUser.setHeader("");
                userlist.put(Constant.CHAT_ROBOT, robotUser);

                // 存入内存
                ((DemoHXSDKHelper) HXSDKHelper.getInstance()).setContactList(userlist);
                // 存入db
                UserDao dao = new UserDao(context);
                List<User> users = new ArrayList<User>(userlist.values());
                dao.saveContactList(users);

                HXSDKHelper.getInstance().notifyContactsSyncListener(true);

                if (HXSDKHelper.getInstance().isGroupsSyncedWithServer()) {
                    HXSDKHelper.getInstance().notifyForRecevingEvents();
                }

                ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getUserProfileManager().asyncFetchContactInfosFromServer(usernames, new EMValueCallBack<List<User>>() {

                    @Override
                    public void onSuccess(List<User> uList) {
                        ((DemoHXSDKHelper) HXSDKHelper.getInstance()).updateContactList(uList);
                        ((DemoHXSDKHelper) HXSDKHelper.getInstance()).getUserProfileManager().notifyContactInfosSyncListener(true);
                    }

                    @Override
                    public void onError(int error, String errorMsg) {
                    }
                });
            }

            @Override
            public void onError(int error, String errorMsg) {
                HXSDKHelper.getInstance().notifyContactsSyncListener(false);
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isConflict) {
            updateUnreadLabel();
            EMChatManager.getInstance().activityResumed();
        }

        // unregister this event listener when this activity enters the
        // background
        DemoHXSDKHelper sdkHelper = (DemoHXSDKHelper) DemoHXSDKHelper.getInstance();
        sdkHelper.pushActivity(this);

        // register the event listener when enter the foreground
        EMChatManager.getInstance().registerEventListener(this,
                new EMNotifierEvent.Event[]{EMNotifierEvent.Event.EventNewMessage, EMNotifierEvent.Event.EventOfflineMessage, EMNotifierEvent.Event.EventConversationListChanged});
    }


    private android.app.AlertDialog.Builder conflictBuilder;
    private android.app.AlertDialog.Builder accountRemovedBuilder;
    private boolean isConflictDialogShow;
    private boolean isAccountRemovedDialogShow;
    private BroadcastReceiver internalDebugReceiver;

    /**
     * 显示帐号在别处登录dialog
     */
    private void showConflictDialog() {
        isConflictDialogShow = true;
        DemoHXSDKHelper.getInstance().logout(false,null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!CustomerMainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (conflictBuilder == null)
                    conflictBuilder = new android.app.AlertDialog.Builder(CustomerMainActivity.this);
                conflictBuilder.setTitle(st);
                conflictBuilder.setMessage(R.string.connect_conflict);
                conflictBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        conflictBuilder = null;
                        MethodUtils.setString(Constant.SHAREDREFERENCE_CONFIG_USER, Constant.SHAREDREFERENCE_CONFIG_USER, "");
                        finish();
                        startActivity(new Intent(CustomerMainActivity.this, LoginActivity.class));
                    }
                });
                conflictBuilder.setCancelable(false);
                conflictBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {

            }

        }

    }

    private static void setUserHearder(String username, User user) {
        String headerName = null;
        if (!TextUtils.isEmpty(user.getNick())) {
            headerName = user.getNick();
        } else {
            headerName = user.getUsername();
        }
        if (username.equals(Constant.NEW_FRIENDS_USERNAME)) {
            user.setHeader("");
        } else if (Character.isDigit(headerName.charAt(0))) {
            user.setHeader("#");
        } else {
            user.setHeader(HanziToPinyin.getInstance().get(headerName.substring(0, 1)).get(0).target.substring(0, 1)
                    .toUpperCase());
            char header = user.getHeader().toLowerCase().charAt(0);
            if (header < 'a' || header > 'z') {
                user.setHeader("#");
            }
        }
    }

    @Override
    public void onEvent(EMNotifierEvent event) {
        switch (event.getEvent()) {
            case EventNewMessage: // 普通消息
            {
                EMMessage message = (EMMessage) event.getData();

                // 提示新消息
                HXSDKHelper.getInstance().getNotifier().onNewMsg(message);

                refreshUI();
                break;
            }

            case EventOfflineMessage: {
                refreshUI();
                break;
            }

            case EventConversationListChanged: {
                refreshUI();
                break;
            }

            default:
                break;
        }
    }

    private void refreshUI() {
        runOnUiThread(new Runnable() {
            public void run() {
                // 刷新bottom bar消息未读数
                updateUnreadLabel();
                if (oldFragment != null && oldFragment instanceof ChatAllHistoryFragment) {
                    // 当前页面如果为聊天历史页面，刷新此页面
                    ((ChatAllHistoryFragment)oldFragment).refresh();
                }
            }
        });
    }

    /**
     * 刷新未读消息数
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(String.valueOf(count));
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 获取未读消息数
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        int unreadMsgCountTotal = 0;
        int chatroomUnreadMsgCount = 0;
        unreadMsgCountTotal = EMChatManager.getInstance().getUnreadMsgsCount();
        for(EMConversation conversation:EMChatManager.getInstance().getAllConversations().values()){
            if(conversation.getType() == EMConversation.EMConversationType.ChatRoom)
                chatroomUnreadMsgCount=chatroomUnreadMsgCount+conversation.getUnreadMsgCount();
        }
        return unreadMsgCountTotal-chatroomUnreadMsgCount;
    }

    /**
     * 连接监听listener
     */
    public class MyConnectionListener implements EMConnectionListener {

        @Override
        public void onConnected() {
            boolean groupSynced = HXSDKHelper.getInstance().isGroupsSyncedWithServer();
            boolean contactSynced = HXSDKHelper.getInstance().isContactsSyncedWithServer();

            // in case group and contact were already synced, we supposed to notify sdk we are ready to receive the events
            if (groupSynced && contactSynced) {
                new Thread() {
                    @Override
                    public void run() {
                        HXSDKHelper.getInstance().notifyForRecevingEvents();
                    }
                }.start();
            } else {
                if (!groupSynced) {
//                    asyncFetchGroupsFromServer();
                }

                if (!contactSynced) {
                    asyncFetchContactsFromServer();
                }

                if (!HXSDKHelper.getInstance().isBlackListSyncedWithServer()) {
//                    asyncFetchBlackListFromServer();
                }
            }

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
//                    chatHistoryFragment.errorItem.setVisibility(View.GONE);
                }

            });
        }

        @Override
        public void onDisconnected(final int error) {
            final String st1 = getResources().getString(R.string.can_not_connect_chat_server_connection);
            final String st2 = getResources().getString(R.string.the_current_network);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                   if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
//                        showAccountRemovedDialog();
                    } else if (error == EMError.CONNECTION_CONFLICT) {
                        // 显示帐号在其他设备登陆dialog
                        showConflictDialog();
                    } else {
//                        chatHistoryFragment.errorItem.setVisibility(View.VISIBLE);
//                        if (NetUtils.hasNetwork(CustomerMainActivity.this))
//                            chatHistoryFragment.errorText.setText(st1);
//                        else
//                            chatHistoryFragment.errorText.setText(st2);

                    }
                }

            });
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mHomeKeyEventReceiver);
        if (conflictBuilder != null) {
            conflictBuilder.create().dismiss();
            conflictBuilder = null;
        }

        if(connectionListener != null){
            EMChatManager.getInstance().removeConnectionListener(connectionListener);
        }

        try {
            unregisterReceiver(internalDebugReceiver);
        } catch (Exception e) {
        }
    }
}