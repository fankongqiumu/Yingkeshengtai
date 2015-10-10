package com.yingke.shengtai.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yingke.shengtai.R;

public class NavBottomView  extends LinearLayout implements OnClickListener {
	
	private OnSelectedListener listener;
	private int currPosition = NAVBOTTOM_GUIDE;
	private Context context;
	private boolean isSource;

	public static final int NAVBOTTOM_GUIDE = 1;
	public static final int NAVBOTTOM_CENTER = 2;
	public static final int NAVBOTTOM_ONLINE = 3;
	public static final int NAVBOTTOM_SEARCHER = 4;


	private int [] selectCustomer = {R.mipmap.tab_guid_selected, R.mipmap.tab_news_selected, R.mipmap.tab_chat_selected, R.mipmap.tab_busiess_selected};
	private int [] normalCustomer = {R.mipmap.tab_guid_normal, R.mipmap.tab_news_normal, R.mipmap.tab_chat_normal, R.mipmap.tab_busiess_norma};
	public NavBottomView(Context context) {
		this(context, null);
	}

	public NavBottomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		inflate(context, R.layout.view_navbottom, this);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavBottomView);
		isSource = a.getBoolean(R.styleable.NavBottomView_is_from_sale, false);
		a.recycle();
		if(isSource){
			selectCustomer[0] = R.mipmap.tab_chat_selected;
			selectCustomer[1] = R.mipmap.friends_list_selected;
			selectCustomer[2] = R.mipmap.tab_busiess_selected;
			selectCustomer[3] = R.mipmap.tab_vender_selected;

			normalCustomer[0] = R.mipmap. tab_chat_normal;
			normalCustomer[1] = R.mipmap. friends_list_normal;
			normalCustomer[2] = R.mipmap. tab_busiess_norma;
			normalCustomer[3] = R.mipmap. tab_vender_normal;

			((TextView)findViewById(R.id.view_navbottom_home_tv)).setText(R.string.sale_one);
			((TextView)findViewById(R.id.view_navbottom_match_tv)).setText(R.string.sale_two);
			((TextView)findViewById(R.id.view_navbottom_community_tv)).setText(R.string.sale_three);
			((TextView)findViewById(R.id.view_navbottom_cart_tv)).setText(R.string.sale_four);

		}
		this.findViewById(R.id.view_navbottom_home_rl).setOnClickListener(this);
		this.findViewById(R.id.view_navbottom_match_rl).setOnClickListener(this);
		this.findViewById(R.id.view_navbottom_community_rl).setOnClickListener(this);
		this.findViewById(R.id.view_navbottom_cart_rl).setOnClickListener(this);
		setStyleChange(NAVBOTTOM_GUIDE);
	}
	
	public int getCurrentPosition() {
		return currPosition;
	}
	
	public View getNavButton(int navIndex) {
		if (navIndex == NAVBOTTOM_GUIDE) {
			return this.findViewById(R.id.view_navbottom_home_rl);
		} else if (navIndex == NAVBOTTOM_CENTER) {
			return this.findViewById(R.id.view_navbottom_match_rl);
		} else if (navIndex == NAVBOTTOM_ONLINE) {
			return this.findViewById(R.id.view_navbottom_community_rl);
		} else if (navIndex == NAVBOTTOM_SEARCHER) {
			return this.findViewById(R.id.view_navbottom_cart_rl);
		}
		return this;
	}
	
	@Override
	public void onClick(View v) {
		String tabValue = "";
		switch(v.getId()) {
		case R.id.view_navbottom_home_rl:
			currPosition = NAVBOTTOM_GUIDE;
			break;
		case R.id.view_navbottom_match_rl:
			currPosition = NAVBOTTOM_CENTER;
			break;
		case R.id.view_navbottom_community_rl:
			currPosition = NAVBOTTOM_ONLINE;
			break;
		case R.id.view_navbottom_cart_rl:
			currPosition = NAVBOTTOM_SEARCHER;
			break;
		}
		/*if(currPosition == NAVBOTTOM_CART && !WodfanApplication.getInstance().isLoggedIn()) {
			CommonUtil.showToast(R.string.toast_user_login);
			Intent intent = new Intent(getContext(), AuthorizationActivity.class);
			getContext().startActivity(intent);
		} else {
			setStyleChange(currPosition);
			if (listener != null) {
				listener.onSelected(currPosition);
			}
		}*/
		setStyleChange(currPosition);
		if (listener != null) {
			listener.onSelected(currPosition);
		}
	}

	public void setStyleChange(int site) {
		this.findViewById(R.id.view_navbottom_home_iv).setBackgroundResource(normalCustomer[0]);
		this.findViewById(R.id.view_navbottom_match_iv).setBackgroundResource(normalCustomer[1]);
		this.findViewById(R.id.view_navbottom_community_iv).setBackgroundResource(normalCustomer[2]);
		this.findViewById(R.id.view_navbottom_cart_iv).setBackgroundResource(normalCustomer[3]);
		((TextView)this.findViewById(R.id.view_navbottom_home_tv)).setTextColor(0xFF8A8A8A);
		((TextView)this.findViewById(R.id.view_navbottom_match_tv)).setTextColor(0xFF8A8A8A);
		((TextView)this.findViewById(R.id.view_navbottom_community_tv)).setTextColor(0xFF8A8A8A);
		((TextView)this.findViewById(R.id.view_navbottom_cart_tv)).setTextColor(0xFF8A8A8A);

		switch (site) {
		case NAVBOTTOM_GUIDE:
			this.findViewById(R.id.view_navbottom_home_iv).setBackgroundResource(selectCustomer[0]);
			((TextView)this.findViewById(R.id.view_navbottom_home_tv)).setTextColor(0xFF0a2b4a);
			break;
		case NAVBOTTOM_CENTER:
			this.findViewById(R.id.view_navbottom_match_iv).setBackgroundResource(selectCustomer[1]);
			((TextView)this.findViewById(R.id.view_navbottom_match_tv)).setTextColor(0xFF0a2b4a);
			break;
		case NAVBOTTOM_ONLINE:
			this.findViewById(R.id.view_navbottom_community_iv).setBackgroundResource(selectCustomer[2]);
			((TextView)this.findViewById(R.id.view_navbottom_community_tv)).setTextColor(0xFF0a2b4a);
			break;
		case NAVBOTTOM_SEARCHER:
			this.findViewById(R.id.view_navbottom_cart_iv).setBackgroundResource(selectCustomer[3]);
			((TextView)this.findViewById(R.id.view_navbottom_cart_tv)).setTextColor(0xFF0a2b4a);
			break;
		}
	}

	public void setOnSelectedListener(OnSelectedListener listener) {
		this.listener = listener;
	}
	
	public interface OnSelectedListener {
		void onSelected(int position);
	}
}
