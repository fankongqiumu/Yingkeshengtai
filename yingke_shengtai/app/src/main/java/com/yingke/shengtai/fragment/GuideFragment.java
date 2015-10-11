package com.yingke.shengtai.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.adapter.GuideAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.AguideListData;
import com.yingke.shengtai.moudle.GuideListData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.view.DetailMenuView;
import com.yingke.shengtai.view.FootView;
import com.yingke.shengtai.view.GridView;
import com.yingke.shengtai.view.GuideListView;
import com.yingke.shengtai.view.TitleView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by yanyiheng on 15-8-15.
 */
public class GuideFragment extends BaseFragment implements FootView.LoadingMoreListener, WaveSwipeRefreshLayout.OnRefreshListener{
    public static final String TAG = GuideFragment.class.getSimpleName();
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private View parentView;
    private TitleView titleView;
    private GuideListView gridView;
//    private FootView footView;

    private GuideAdapter adapter;
    private DetailMenuView menu;

    private Type type;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(parentView != null){
            if(parentView.getParent() != null){
                ((ViewGroup)parentView.getParent()).removeAllViews();
            }
            return parentView;
        } 
        parentView = inflater.inflate(R.layout.fragment_guide_update, container, false);
        initUi();
        return parentView;
    }

    private void askData() {
        getData(IApi.NETWORK_METHOD_GET, TAG_GUIDLIST, IApi.URL_GUIDLIST, null);


        /**
         * 1.查看方法里的参数  将光标定在当前行  crtl  shift 空格
         * 2.将光标定回你上次修改代码的地方      crtl  shift  F2
         * 3.查看上下文用到的当前选中的字段      Ｆ3 或者shift F3
         * 4.查看当前方法的文档信息             crtl  q
         * 5.最近打开的文件                    crtl  e
         * 6.定位句法错误的行，或者在错误间跳转   Ｆ2 或者 shift F2
         * 7.同上不过是编译错误                 Ctrl+Alt+向上箭头/Ctrl+Alt+向下箭头
         */
    }

    private void initUi() {
        titleView = (TitleView)parentView.findViewById(R.id.fragment_title);
        titleView.setTitleView(R.string.business_guide);
        titleView.getImageBack().setVisibility(View.GONE);
        titleView.getImagePeople().setVisibility(View.VISIBLE);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) parentView.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        gridView = (GuideListView) parentView.findViewById(R.id.fragment_gridview);

        titleView.getImagePeople().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(menu != null){
                    if(menu.isShowing()){
                        menu.dismiss();
                    } else {
                        menu.show(titleView);
                    }
                }

            }
        });

        type = new TypeToken<AguideListData>() {}.getType();
        menu = new DetailMenuView(getActivity());

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        askData();
        mWaveSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
//        flag = 1;
//        footView.setResetParam();
        askData();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    private void setRefreFalse(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 更新が終了したらインジケータ非表示
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        }, 300);
    }

    @Override
    public void handleMsg(Message msg) {
        String json = msg.getData().getString(Constant.JSON_DATA);
        if(TextUtils.equals(json, getString(R.string.try_agin))){
            setRefreFalse();
            return;
        }
        switch (msg.what){
            case TAG_GUIDLIST:
                AguideListData listData = JosnUtil.gson.fromJson(json, type);
                gridView.createView(listData.getChannellist());
                setRefreFalse();
                break;
        }
    }

    @Override
    public void loadingMore() {
        askData();
    }
}
