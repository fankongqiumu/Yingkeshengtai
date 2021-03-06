package com.yingke.shengtai.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class WelcomViewPagerAdapter extends PagerAdapter {
	
	private ArrayList<View> views;

	public WelcomViewPagerAdapter(ArrayList<View> views) {
		super();
		this.views = views;
	}

	@Override
	public int getCount() {
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if(views != null){
			container.removeView(views.get(position));			
		}
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(views.get(position));
		return views.get(position);			
	}

}
