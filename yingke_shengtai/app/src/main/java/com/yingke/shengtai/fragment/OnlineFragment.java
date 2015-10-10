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
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.adapter.CenterListItemAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.CenterListData;
import com.yingke.shengtai.moudle.TotalCustomerData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.view.DetailMenuView;
import com.yingke.shengtai.view.FootView;
import com.yingke.shengtai.view.TitleView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by yanyiheng on 15-8-15.
 */
public class OnlineFragment extends BaseFragment implements FootView.LoadingMoreListener, WaveSwipeRefreshLayout.OnRefreshListener{
    public static final String TAG = OnlineFragment.class.getSimpleName();
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private View parentView;
    private TitleView titleView;
    private ListView listView;
    private FootView footView;

    private CenterListItemAdapter adapter;
    private CenterListData data;

    private Type type;
    private int flag = 1;
    private ArrayList<TotalCustomerData> list;
    private DetailMenuView menu;


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
        parentView = inflater.inflate(R.layout.fragment_guide, container, false);
        initUi();
        askData();
        setLoadData();
        return parentView;
    }

    private void askData() {
        getData(IApi.NETWORK_METHOD_GET, TAG_ONLINELIST, IApi.URL_CENTERLIST + flag, null);

    }

    private void initUi() {
        titleView = (TitleView)parentView.findViewById(R.id.fragment_title);
        titleView.setTitleView(R.string.sale_one);
        titleView.getImageBack().setVisibility(View.GONE);
        titleView.getImagePeople().setVisibility(View.VISIBLE);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) parentView.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView)parentView.findViewById(R.id.fragment_listView);

//        footView = new FootView(getActivity());
//        footView.initFooter(new FooterUIText(getActivity()), this, flag);
//        footView.attachToListView(listView);
//        listView.addFooterView(footView);
//        listView.setOnScrollListener(footView.new AbsFooterScroller());

        list = new ArrayList<TotalCustomerData>();
        type = new TypeToken<CenterListData>() {}.getType();

        menu = new DetailMenuView(getActivity());
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
    }

    @Override
    public void onRefresh() {
        flag = 1;
        footView.setResetParam();
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
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        }, 300);
    }

    private void setLoadData(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mWaveSwipeRefreshLayout.setRefreshing(true);
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
        data = JosnUtil.gson.fromJson(json, type);
        TotalCustomerData  listData = null;
//        switch (msg.what){
//            case TAG_ONLINELIST:
//                if(adapter == null){
//                    list.add(listData);
//                    adapter = new CenterListItemAdapter(getActivity(), list, listView);
//                    listView.setAdapter(adapter);
//                } else {
//                    if(flag == 1){
//                        list = listData;
//                    } else {
//                        list.addAll(listData);
//                    }
//                    adapter.setData(list);
//
//                }
//                if(flag * 15 >= Integer.valueOf(data.getCount())){
//                    footView.setFlag(0);
//                } else {
//                    footView.setFlag(flag);
//                }
//                flag = flag + 1;
//                listData = null;
//                setRefreFalse();
//                break;
//        }
    }

    @Override
    public void loadingMore() {
        askData();
    }
}
