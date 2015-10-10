package com.yingke.shengtai.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.Toast;

import com.easemob.EMConnectionListener;
import com.easemob.EMValueCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.util.HanziToPinyin;
import com.yingke.shengtai.db.User;
import com.yingke.shengtai.fragment.ChatSaleHistoryFragment;
import com.yingke.shengtai.fragment.SaleBussinessFragment;
import com.yingke.shengtai.fragment.SaleCustomListFragment;
import com.yingke.shengtai.fragment.SaleOnlineFragment;
import com.yingke.shengtai.fragment.SaleSearchFragment;
import com.yingke.shengtai.moudle.UserDao;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.DemoHXSDKHelper;
import com.yingke.shengtai.utils.HXSDKHelper;
import com.yingke.shengtai.view.NavBottomView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanyiheng on 15-8-16.
 */
public class SaleMainActivity extends FragmentActivity {
    private NavBottomView navbottom;

    private Fragment oldFragment;
    private MyConnectionListener connectionListener = null;

    private static boolean isExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salemain);
        initUi();
        initPage();
        connectionListener = new MyConnectionListener();
        EMChatManager.getInstance().addConnectionListener(connectionListener);
    }

    private void initUi() {
        navbottom = (NavBottomView) findViewById(R.id.activity_main_navbottom);
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
                        if (oldFragment instanceof SaleOnlineFragment) {
                            return;
                        }
                        setChangePage(SaleOnlineFragment.TAG, false);
                        break;
                    case NavBottomView.NAVBOTTOM_CENTER:
                        if (oldFragment instanceof SaleCustomListFragment) {
                            return;
                        }
                        setChangePage(SaleCustomListFragment.TAG, false);
                        break;
                    case NavBottomView.NAVBOTTOM_ONLINE:
                        if (oldFragment instanceof SaleBussinessFragment) {
                            return;
                        }
                        setChangePage(SaleBussinessFragment.TAG, false);
                        break;
                    case NavBottomView.NAVBOTTOM_SEARCHER:
                        if (oldFragment instanceof SaleSearchFragment) {
                            return;
                        }
                        setChangePage(SaleSearchFragment.TAG, false);
                        break;
                }
            }
        });
        setChangePage(SaleOnlineFragment.TAG, true);
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
            if (tag.equals(SaleOnlineFragment.TAG)) {
                currentFragment = new ChatSaleHistoryFragment();
            } else if (tag.equals(SaleCustomListFragment.TAG)) {
                currentFragment = new SaleCustomListFragment();
            } else if (tag.equals(SaleBussinessFragment.TAG)) {
                currentFragment = new SaleBussinessFragment();
            } else if (tag.equals(SaleSearchFragment.TAG)) {
                currentFragment = new SaleSearchFragment();
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
                   /* if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        showAccountRemovedDialog();
                    } else if (error == EMError.CONNECTION_CONFLICT) {
                        // 显示帐号在其他设备登陆dialog
                        showConflictDialog();
                    } else {
                        chatHistoryFragment.errorItem.setVisibility(View.VISIBLE);
                        if (NetUtils.hasNetwork(MainActivity.this))
                            chatHistoryFragment.errorText.setText(st1);
                        else
                            chatHistoryFragment.errorText.setText(st2);

                    }*/
                }

            });
        }


    }
}