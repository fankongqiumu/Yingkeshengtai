package com.yingke.shengtai.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.yingke.shengtai.activity.AcountActivity;
import com.yingke.shengtai.activity.ChangePassWordActivity;
import com.yingke.shengtai.activity.LoginActivity;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.utils.UiUtil;


public class DetailMenuView extends PopupWindow implements OnClickListener {
	
	private Context mContext;
	
	private View parentView;
	private TextView acount, password, exit;
	private ImageView imgCollect;

	private DetailMenuActionListener mActionListener;
	
	public static final int DETAILMENU_TYPE_SHARE = 0x001;
	public static final int DETAILMENU_TYPE_HOME = 0x002;
	public static final int DETAILMENU_TYPE_COLLECT = 0x003;
	
	public DetailMenuView(Context context) {
		super(context);
		this.mContext = context;
        LayoutInflater localLayoutInflater = LayoutInflater.from(context);
        parentView = localLayoutInflater.inflate(R.layout.view_detailmenu, null);
		setContentView(parentView);
        setWidth(UiUtil.dip2px(120));
        setHeight(LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(context.getResources().getDrawable(android.R.color.transparent));
        setAnimationStyle(R.style.menushow);
        setInputMethodMode(1);
        setTouchable(true);
        setFocusable(true);
        setOutsideTouchable(true);

		acount = (TextView) parentView.findViewById(R.id.detailmenu_acount);
        password = (TextView) parentView.findViewById(R.id.detailmenu_change);
        exit = (TextView) parentView.findViewById(R.id.detailmenu_exit);

		acount.setOnClickListener(this);
		password.setOnClickListener(this);
		exit.setOnClickListener(this);
	}
	
	public void show(View parentView) {
		this.showAsDropDown(parentView, mContext.getResources().getDisplayMetrics().widthPixels - UiUtil.dip2px(120),0);
		update();
	}
	
	private Context getContext() {
		return mContext;
	}
	
	private int getDimension(int id) {
		return getContext().getResources().getDimensionPixelOffset(id);
	}
	
	@Override
	public void onClick(View v) {
		dismiss();
		Intent intent = null;
		switch (v.getId()) {
		case R.id.detailmenu_acount:
			intent = new Intent(mContext, AcountActivity.class);
			mContext.startActivity(intent);
			break;
		case R.id.detailmenu_change:
			intent = new Intent(mContext, ChangePassWordActivity.class);
			mContext.startActivity(intent);
			break;
		case R.id.detailmenu_exit:
			EMChatManager.getInstance().logout(new EMCallBack() {

				@Override
				public void onSuccess() {

				}

				@Override
				public void onProgress(int progress, String status) {

				}

				@Override
				public void onError(int code, String message) {

				}
			});
			intent = new Intent(mContext, LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			mContext.startActivity(intent);
			MethodUtils.setString(Constant.SHAREDREFERENCE_CONFIG_USER, Constant.SHAREDREFERENCE_CONFIG_USER, "");
			((Activity)mContext).finish();;
			break;
		default:
			break;
		}
	}
	
	public interface DetailMenuActionListener {
		public void onDetailMenuAction(View v, int type);
	}
	
	public void setDetailMenuActionListener(DetailMenuActionListener listener) {
		this.mActionListener = listener;
	}
}
