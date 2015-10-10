package com.yingke.shengtai.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yingke.shengtai.behaviro.FooterUIBehavior;
import com.yingke.shengtai.utils.UiUtil;

/**
 * Created by yanyiheng on 15-8-15.
 */
public class FootView extends RelativeLayout {
    private FooterUIBehavior behavior;
    private int flag;
    private LoadingMoreListener listener;
    private FooterLoadState currentLoadingState;

    private AbsListView mListView;

    public FootView(Context context) {
        super(context);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, UiUtil.dip2px(50));
        this.setLayoutParams(params);
        FootView.this.setOnClickListener(null);
    }

    public FootView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public class AbsFooterScroller implements AbsListView.OnScrollListener{
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (visibleItemCount > 0 && firstVisibleItem + visibleItemCount == totalItemCount
                    && currentLoadingState != FooterLoadState.LOADSTATE_LOADING
                    && currentLoadingState != FooterLoadState.LOADSTATE_RETRY
                    && currentLoadingState != FooterLoadState.LOADSTATE_NOMORE
                    && null != listener && hasMore()
                    && totalItemCount >= visibleItemCount) {
                   if(flag != 0){
                       listener.loadingMore();
                       setLoadState(FooterLoadState.LOADSTATE_LOADING);
                   } else {
                       setLoadState(FooterLoadState.LOADSTATE_NOMORE);
                   }

            }
        }
    }

    public void attachToListView(AbsListView listView){
        if(listView instanceof ListView){
            ((ListView) listView).setFooterDividersEnabled(false);
        }
        this.mListView = listView;
    }

    private void setFooterLoadingListener(LoadingMoreListener listener) {
        this.listener = listener;
    }

    public interface LoadingMoreListener {
        public void loadingMore();
    }

    public void setFlag(int flag) {
        this.flag = flag;
        if (hasMore()) {
            setLoadState(FooterLoadState.LOADSTATE_HASMORE);
        } else {
            setLoadState(FooterLoadState.LOADSTATE_EMPTY);
        }
    }

    public void onSuccess(){
        currentLoadingState = FooterLoadState.LOADSTATE_HASMORE;
    }

    public void setLoadState(FooterLoadState state) {
        if (behavior == null) {
            return;
        }
        this.currentLoadingState = state;
        switch (state) {
            case LOADSTATE_NOMORE:
                behavior.nomore();
                break;
            case LOADSTATE_HASMORE:
                behavior.hasmore();
                break;
            case LOADSTATE_LOADING:
                behavior.loading();
                break;
            case LOADSTATE_RETRY:
                behavior.retry();
                break;
            case LOADSTATE_EMPTY:
                behavior.empty();
                break;
            case LOADSTATE_DEFAULT:
            default:
                behavior.loading();
                break;
        }
    }

    public int getFlag() {
        return flag;
    }

    public boolean hasMore() {
        return flag == 0 ? false : true;  //为了防止一页数据过少时，立马请求两次，导致数据重复
    }

    public void initFooter(FooterUIBehavior behavior, LoadingMoreListener listener, int flag) {
        setFooterUIBehavior(behavior);
        setFooterLoadingListener(listener);
        setFlag(flag);
    }

    public void setResetParam(){
        setFlag(1);
    }

    private void setFooterUIBehavior(FooterUIBehavior behavior) {
        this.behavior = behavior;
        this.removeAllViews();
        this.addView(behavior.getFooterView());
    }

    public enum FooterLoadState {
        LOADSTATE_NOMORE, LOADSTATE_HASMORE, LOADSTATE_LOADING, LOADSTATE_RETRY, LOADSTATE_DEFAULT, LOADSTATE_EMPTY
    }
}
