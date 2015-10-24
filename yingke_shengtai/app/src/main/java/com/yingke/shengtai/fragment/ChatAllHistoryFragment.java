package com.yingke.shengtai.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.activity.ChatActivity;
import com.yingke.shengtai.activity.CustomerMainActivity;
import com.yingke.shengtai.adapter.ChatAllHistoryAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.db.InviteMessgeDao;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.view.DetailMenuView;
import com.yingke.shengtai.view.TitleView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class ChatAllHistoryFragment extends BaseFragment implements OnClickListener, WaveSwipeRefreshLayout.OnRefreshListener{

	private InputMethodManager inputMethodManager;
	private ListView listView;
	private ChatAllHistoryAdapter adapter;
	private TitleView titleView;
	private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
	private View headView;

	private boolean hidden;
	private DetailMenuView menu;
	private KeFuData data;
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
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.row_chat_history, null);
		conversationList.addAll(loadConversationsWithRecentChat());
		listView = (ListView) getView().findViewById(R.id.list);
		listView.addHeaderView(headView);
		adapter = new ChatAllHistoryAdapter(getActivity(), 1, conversationList, headView, listView);
		// 设置adapter
		listView.setAdapter(adapter);
        getData(IApi.NETWORK_METHOD_GET, TAG_SALE_BUSSINESS_LIST, IApi.URL_CUSTOMER_LIST, null);
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
				if(data == null || data.getSaledetail() == null ){
					return;
				}
				if(listView.getHeaderViewsCount() == 0){
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
				} else if(listView.getHeaderViewsCount() == 1){
					if(data == null || data.getSaledetail() == null ){
						return;
					}
					if(position == 0){
						Intent intent = new Intent(getActivity(), ChatActivity.class);
						intent.putExtra("userId", data.getSaledetail().getS_imid());
						intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
						intent.putExtra("name", data.getSaledetail().getS_showname());
						startActivity(intent);

						return;
					}
					EMConversation conversation = adapter.getItem(position - 1);
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

			}
		});
		// 注册上下文菜单
		registerForContextMenu(listView);

		listView.setOnTouchListener(new View.OnTouchListener() {

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
				refresh();
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
		if(listView.getHeaderViewsCount() >= 1){
			EMConversation tobeDeleteCons = adapter.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position - 1);
			// 删除此会话
			EMChatManager.getInstance().deleteConversation(tobeDeleteCons.getUserName(), tobeDeleteCons.isGroup(), deleteMessage);
			InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
			inviteMessgeDao.deleteMessage(tobeDeleteCons.getUserName());
			adapter.remove(tobeDeleteCons);
			adapter.notifyDataSetChanged();
		} else {
			EMConversation tobeDeleteCons = adapter.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
			// 删除此会话
			EMChatManager.getInstance().deleteConversation(tobeDeleteCons.getUserName(), tobeDeleteCons.isGroup(), deleteMessage);
			InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
			inviteMessgeDao.deleteMessage(tobeDeleteCons.getUserName());
			adapter.remove(tobeDeleteCons);
			adapter.notifyDataSetChanged();
		}


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

	public void setAdapter(){
		if(listView != null && adapter != null){
			listView.setAdapter(adapter);
		}
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
			if(adapter != null){
				conversationList.clear();
				conversationList.addAll(loadConversationsWithRecentChat());
				if(conversationList.size() == 0){
					return;
				}
				if(data == null || data.getSaledetail() == null){
					return;
				}
				for (int i= 0; i < conversationList.size(); i++){
					EMConversation  emcon = conversationList.get(i);
					if(TextUtils.equals(emcon.getUserName(), data.getSaledetail().getS_imid())){
						if(headView != null){
							listView.removeHeaderView(headView);
							listView.setAdapter(adapter);
						}

					}
				}


			}
		}

	}

	@Override
    public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
        if(((CustomerMainActivity)getActivity()).isConflict){
        	outState.putBoolean("isConflict", true);
        }
    }

    @Override
    public void onClick(View v) {        
    }

	@Override
	public void handleMsg(Message msg) {
		String json = msg.getData().getString(Constant.JSON_DATA);
		if(TextUtils.equals(json, getString(R.string.try_agin))){
			setRefreFalse();
			return;
		}
		switch (msg.what){
			case TAG_SALE_BUSSINESS_LIST:
				data = JosnUtil.gson.fromJson(json, new TypeToken<KeFuData>(){}.getType());
				if(TextUtils.equals("0", data.getResult())){
					break;
				}
                if(listView.getHeaderViewsCount() <1){
					break;
				}
				((TextView)headView.findViewById(R.id.name)).setText(data.getSaledetail().getS_showname());
				((TextView)headView.findViewById(R.id.time)).setText(MethodUtils.returnTime(data.getSaledetail().getS_lastim()));
				if(TextUtils.equals("0", data.getSaledetail().getS_sex())){
					((ImageView)headView.findViewById(R.id.avatar)).setImageResource(R.mipmap.male_yewu);
				} else {
					((ImageView)headView.findViewById(R.id.avatar)).setImageResource(R.mipmap.meal_yewu);
				}
				if(adapter != null){
					conversationList.clear();
					conversationList.addAll(loadConversationsWithRecentChat());
					for (int i= 0; i < conversationList.size(); i++){
						EMConversation  emcon = conversationList.get(i);
						if(TextUtils.equals(emcon.getUserName(), data.getSaledetail().getS_imid())){
							if(headView != null){
								listView.removeHeaderView(headView);
								listView.setAdapter(adapter);
							}

						}
					}


				}
				break;
		}
	}

	public class  KeFuData implements Serializable{

		private static final long serialVersionUID = 1993998031278875693L;
		/**
		 * result : 1
		 * message :
		 * saledetail : {"s_id":"2","s_keyid":"e0382f8b-07ce-4fc2-b7f0-ddeecffe73cf","s_memberid":"SAL10002","s_username":"18610000002","s_password":"","s_deviceid":"null","s_imid":"bes_root_servicemanager","s_token":"","s_name":"总客服","s_sex":"1","SalesSex":"1","s_showname":"总客服","s_membertype":"4","MemberType":"4","s_sectorid":"1","s_rate":"0.1","s_managerid":"0","s_childids":"null","s_latestlogin":"null","s_location":"北京","s_block":"false","s_delete":"false","s_rowspage":"5","s_default":"true","s_userids":"","s_imresponse":"","s_amount":"15000","s_lastim":"2015-09-22T23:47:22.9","DataKey":"s_id"}
		 */

		private String result;
		private String message;
		private SaledetailEntity saledetail;

		public void setResult(String result) {
			this.result = result;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public void setSaledetail(SaledetailEntity saledetail) {
			this.saledetail = saledetail;
		}

		public String getResult() {
			return result;
		}

		public String getMessage() {
			return message;
		}

		public SaledetailEntity getSaledetail() {
			return saledetail;
		}

		public  class SaledetailEntity {
			/**
			 * s_id : 2
			 * s_keyid : e0382f8b-07ce-4fc2-b7f0-ddeecffe73cf
			 * s_memberid : SAL10002
			 * s_username : 18610000002
			 * s_password :
			 * s_deviceid : null
			 * s_imid : bes_root_servicemanager
			 * s_token :
			 * s_name : 总客服
			 * s_sex : 1
			 * SalesSex : 1
			 * s_showname : 总客服
			 * s_membertype : 4
			 * MemberType : 4
			 * s_sectorid : 1
			 * s_rate : 0.1
			 * s_managerid : 0
			 * s_childids : null
			 * s_latestlogin : null
			 * s_location : 北京
			 * s_block : false
			 * s_delete : false
			 * s_rowspage : 5
			 * s_default : true
			 * s_userids :
			 * s_imresponse :
			 * s_amount : 15000
			 * s_lastim : 2015-09-22T23:47:22.9
			 * DataKey : s_id
			 */

			private String s_id;
			private String s_keyid;
			private String s_memberid;
			private String s_username;
			private String s_password;
			private String s_deviceid;
			private String s_imid;
			private String s_token;
			private String s_name;
			private String s_sex;
			private String SalesSex;
			private String s_showname;
			private String s_membertype;
			private String MemberType;
			private String s_sectorid;
			private String s_rate;
			private String s_managerid;
			private String s_childids;
			private String s_latestlogin;
			private String s_location;
			private String s_block;
			private String s_delete;
			private String s_rowspage;
			private String s_default;
			private String s_userids;
			private String s_imresponse;
			private String s_amount;
			private String s_lastim;
			private String DataKey;

			public void setS_id(String s_id) {
				this.s_id = s_id;
			}

			public void setS_keyid(String s_keyid) {
				this.s_keyid = s_keyid;
			}

			public void setS_memberid(String s_memberid) {
				this.s_memberid = s_memberid;
			}

			public void setS_username(String s_username) {
				this.s_username = s_username;
			}

			public void setS_password(String s_password) {
				this.s_password = s_password;
			}

			public void setS_deviceid(String s_deviceid) {
				this.s_deviceid = s_deviceid;
			}

			public void setS_imid(String s_imid) {
				this.s_imid = s_imid;
			}

			public void setS_token(String s_token) {
				this.s_token = s_token;
			}

			public void setS_name(String s_name) {
				this.s_name = s_name;
			}

			public void setS_sex(String s_sex) {
				this.s_sex = s_sex;
			}

			public void setSalesSex(String SalesSex) {
				this.SalesSex = SalesSex;
			}

			public void setS_showname(String s_showname) {
				this.s_showname = s_showname;
			}

			public void setS_membertype(String s_membertype) {
				this.s_membertype = s_membertype;
			}

			public void setMemberType(String MemberType) {
				this.MemberType = MemberType;
			}

			public void setS_sectorid(String s_sectorid) {
				this.s_sectorid = s_sectorid;
			}

			public void setS_rate(String s_rate) {
				this.s_rate = s_rate;
			}

			public void setS_managerid(String s_managerid) {
				this.s_managerid = s_managerid;
			}

			public void setS_childids(String s_childids) {
				this.s_childids = s_childids;
			}

			public void setS_latestlogin(String s_latestlogin) {
				this.s_latestlogin = s_latestlogin;
			}

			public void setS_location(String s_location) {
				this.s_location = s_location;
			}

			public void setS_block(String s_block) {
				this.s_block = s_block;
			}

			public void setS_delete(String s_delete) {
				this.s_delete = s_delete;
			}

			public void setS_rowspage(String s_rowspage) {
				this.s_rowspage = s_rowspage;
			}

			public void setS_default(String s_default) {
				this.s_default = s_default;
			}

			public void setS_userids(String s_userids) {
				this.s_userids = s_userids;
			}

			public void setS_imresponse(String s_imresponse) {
				this.s_imresponse = s_imresponse;
			}

			public void setS_amount(String s_amount) {
				this.s_amount = s_amount;
			}

			public void setS_lastim(String s_lastim) {
				this.s_lastim = s_lastim;
			}

			public void setDataKey(String DataKey) {
				this.DataKey = DataKey;
			}

			public String getS_id() {
				return s_id;
			}

			public String getS_keyid() {
				return s_keyid;
			}

			public String getS_memberid() {
				return s_memberid;
			}

			public String getS_username() {
				return s_username;
			}

			public String getS_password() {
				return s_password;
			}

			public String getS_deviceid() {
				return s_deviceid;
			}

			public String getS_imid() {
				return s_imid;
			}

			public String getS_token() {
				return s_token;
			}

			public String getS_name() {
				return s_name;
			}

			public String getS_sex() {
				return s_sex;
			}

			public String getSalesSex() {
				return SalesSex;
			}

			public String getS_showname() {
				return s_showname;
			}

			public String getS_membertype() {
				return s_membertype;
			}

			public String getMemberType() {
				return MemberType;
			}

			public String getS_sectorid() {
				return s_sectorid;
			}

			public String getS_rate() {
				return s_rate;
			}

			public String getS_managerid() {
				return s_managerid;
			}

			public String getS_childids() {
				return s_childids;
			}

			public String getS_latestlogin() {
				return s_latestlogin;
			}

			public String getS_location() {
				return s_location;
			}

			public String getS_block() {
				return s_block;
			}

			public String getS_delete() {
				return s_delete;
			}

			public String getS_rowspage() {
				return s_rowspage;
			}

			public String getS_default() {
				return s_default;
			}

			public String getS_userids() {
				return s_userids;
			}

			public String getS_imresponse() {
				return s_imresponse;
			}

			public String getS_amount() {
				return s_amount;
			}

			public String getS_lastim() {
				return s_lastim;
			}

			public String getDataKey() {
				return DataKey;
			}
		}
	}
}
