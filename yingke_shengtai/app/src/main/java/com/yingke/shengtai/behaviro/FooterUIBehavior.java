package com.yingke.shengtai.behaviro;

import android.view.View;

public interface FooterUIBehavior {
	/**
	 *没有数据了
	 * */
	public void nomore();

	/**
	 * 还有更多
	 * */
	public void hasmore();
	
	/**
	 * 正在加载中
	 * */
	public void loading();
	
	/**
	 * 再试一次
	 * */
	public void retry();

	/**
	 * 没有数据
	 * */
	public void empty();
	
	/**
	 * footerView
	 * @return 
	 * */
	public View getFooterView();
}
