package com.yingke.shengtai.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.activity.ChatActivity;
import com.yingke.shengtai.adapter.ChatSaleAdapter;
import com.yingke.shengtai.db.InviteMessgeDao;
import com.yingke.shengtai.R;
import com.yingke.shengtai.view.DetailMenuView;
import com.yingke.shengtai.view.TitleView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * 显示所有会话记录，比较简单的实现，更好的可能是把陌生人存入本地，这样取到的聊天记录是可控的
 *
 */
public class ChatSaleHistoryFragment extends Fragment implements OnClickListener, WaveSwipeRefreshLayout.OnRefreshListener{

    private InputMethodManager inputMethodManager;
    private ListView listView;
    private ChatSaleAdapter adapter;
    private TitleView titleView;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    private boolean hidden;
    private DetailMenuView menu;
    private List<EMConversation> conversationList = new ArrayList<EMConversation>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conversation_history, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        titleView = (TitleView)getView().findViewById(R.id.fragment_title);
        titleView.setTitleView(R.string.online_guide);
        titleView.getImageBack().setVisibility(View.GONE);
        titleView.getImageBack().setVisibility(View.GONE);
        titleView.getImagePeople().setVisibility(View.VISIBLE);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) getView().findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);

        conversationList.addAll(loadConversationsWithRecentChat());
        listView = (ListView) getView().findViewById(R.id.list);
        adapter = new ChatSaleAdapter(getActivity(), 1, conversationList);
        // 设置adapter
        listView.setAdapter(adapter);

        menu = new DetailMenuView(getActivity());
        titleView.getImagePeople().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menu != null) {
                    if (menu.isShowing()) {
                        menu.dismiss();
                    } else {
                        menu.show(titleView);
                    }
                }

            }
        });



        final String st2 = getResources().getString(R.string.Cant_chat_with_yourself);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = adapter.getItem(position);
                String username = conversation.getUserName();
                if (username.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getName()))
                    Toast.makeText(getActivity(), st2, Toast.LENGTH_SHORT).show();
                else {
                    // 进入聊天页面
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("userId", username);
                    intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
                    intent.putExtra("name", adapter.getName(username));
                    startActivity(intent);
                }
            }
        });
        // 注册上下文菜单
        registerForContextMenu(listView);

        listView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 隐藏软键盘
                hideSoftKeyboard();
                return false;
            }

        });

    }

    void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onRefresh() {
        setRefreFalse();
    }

    private void setRefreFalse(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 更新が終了したらインジケータ非表示
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // if(((AdapterContextMenuInfo)menuInfo).position > 0){ m,
        getActivity().getMenuInflater().inflate(R.menu.delete_message, menu);
        // }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean handled = false;
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
            handled = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
            handled = true;
        }
        EMConversation tobeDeleteCons = adapter.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
        // 删除此会话
        EMChatManager.getInstance().deleteConversation(tobeDeleteCons.getUserName(), tobeDeleteCons.isGroup(), deleteMessage);
        InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
        inviteMessgeDao.deleteMessage(tobeDeleteCons.getUserName());
        adapter.remove(tobeDeleteCons);
        adapter.notifyDataSetChanged();

        // 更新消息未读数
//		((MainActivity) getActivity()).updateUnreadLabel();

        return handled ? true : super.onContextItemSelected(item);
    }

    /**
     * 刷新页面
     */
    public void refresh() {
        conversationList.clear();
        conversationList.addAll(loadConversationsWithRecentChat());
        if(adapter != null)
            adapter.notifyDataSetChanged();
    }

    /**
     * 获取所有会话
     *
     * @param
     * @return */
    private List<EMConversation> loadConversationsWithRecentChat() {
        // 获取所有会话，包括陌生人
        Hashtable<String, EMConversation> conversations = EMChatManager.getInstance().getAllConversations();
        // 过滤掉messages size为0的conversation
        /**
         * 如果在排序过程中有新消息收到，lastMsgTime会发生变化
         * 影响排序过程，Collection.sort会产生异常
         * 保证Conversation在Sort过程中最后一条消息的时间不变
         * 避免并发问题
         */
        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getAllMessages().size() != 0) {
                    //if(conversation.getType() != EMConversationType.ChatRoom){
                    sortList.add(new Pair<Long, EMConversation>(conversation.getLastMessage().getMsgTime(), conversation));
                    //}
                }
            }
        }
        try {
            // Internal is TimSort algorithm, has bug
            sortConversationByLastChatTime(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EMConversation> list = new ArrayList<EMConversation>();
        for (Pair<Long, EMConversation> sortItem : sortList) {
            list.add(sortItem.second);
        }
        return list;
    }

    /**
     * 根据最后一条消息的时间排序
     *
     * @param
     */
    private void sortConversationByLastChatTime(List<Pair<Long, EMConversation>> conversationList) {
        Collections.sort(conversationList, new Comparator<Pair<Long, EMConversation>>() {
            @Override
            public int compare(final Pair<Long, EMConversation> con1, final Pair<Long, EMConversation> con2) {

                if (con1.first == con2.first) {
                    return 0;
                } else if (con2.first > con1.first) {
                    return 1;
                } else {
                    return -1;
                }
            }

        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
//		if (!hidden && ! ((MainActivity)getActivity()).isConflict) {
//			refresh();
//		}
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        if(((MainActivity)getActivity()).isConflict){
//        	outState.putBoolean("isConflict", true);
//        }else if(((MainActivity)getActivity()).getCurrentAccountRemoved()){
//        	outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
//        }
    }

    @Override
    public void onClick(View v) {
    }
}
