package com.yingke.shengtai.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.yingke.shengtai.behaviro.FooterUIBehavior;
import com.yingke.shengtai.R;

public class FooterUIText extends TextView implements FooterUIBehavior {

	public FooterUIText(Context context) {
		this(context, null);
	}
	
	public FooterUIText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setTextColor(Color.BLACK);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.setLayoutParams(params);
		this.setGravity(Gravity.CENTER);
	}

	@Override
	public void nomore() {
		this.setText(R.string.waterfall_nomore);
	}

	@Override
	public void hasmore() {
		this.setText(R.string.waterfall_hasmore);
	}

	@Override
	public void loading() {
		this.setText(R.string.waterfall_loading);
	}

	@Override
	public void retry() {
		this.setText(R.string.waterfall_retry);
	}

	@Override
	public void empty() {
		this.setText("");
	}

	@Override
	public View getFooterView() {
		return this;
	}
}
