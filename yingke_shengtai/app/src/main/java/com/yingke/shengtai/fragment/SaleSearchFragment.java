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
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.adapter.SaleSearchAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.SaleSearchItemData;
import com.yingke.shengtai.moudle.SaleSearchItemData.GainlistEntity;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.view.DetailMenuView;
import com.yingke.shengtai.view.FootView;
import com.yingke.shengtai.view.FooterUIText;
import com.yingke.shengtai.view.TitleView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by yanyiheng on 15-8-15.
 */
public class SaleSearchFragment extends BaseFragment implements FootView.LoadingMoreListener, WaveSwipeRefreshLayout.OnRefreshListener{
    public static final String TAG = SaleSearchFragment.class.getSimpleName();
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private View parentView;
    private TitleView titleView;
    private ListView listView;
    private FootView footView;

    private SaleSearchAdapter adapter;
    private SaleSearchItemData data;

    private Type type;
    private int flag = 1;
    private ArrayList<GainlistEntity> list;
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
        return parentView;
    }

    private void askData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sid", MyApplication.getInstance().getUserInfor().getUserdetail().getSid());
        map.put("token",MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        getData(IApi.NETWORK_METHOD_POST, TAG_SALE_YEJI_LIST, IApi.URL_SALE_YEJI_LIST + flag, map);

    }

    private void initUi() {
        titleView = (TitleView)parentView.findViewById(R.id.fragment_title);
        titleView.setTitleView(R.string.sale_four);
        titleView.getImageBack().setVisibility(View.GONE);
        titleView.getImagePeople().setVisibility(View.VISIBLE);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) parentView.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView)parentView.findViewById(R.id.fragment_listView);

        footView = new FootView(getActivity());
        footView.initFooter(new FooterUIText(getActivity()), this, flag);
        footView.attachToListView(listView);
        listView.addFooterView(footView);
        listView.setOnScrollListener(footView.new AbsFooterScroller());

        list = new ArrayList<GainlistEntity>();
        type = new TypeToken<SaleSearchItemData>() {}.getType();

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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden ){
            if((list == null || list.size() <= 0) && !mWaveSwipeRefreshLayout.isRefreshing()){
                mWaveSwipeRefreshLayout.setRefreshing(true);
            }
        } else {
            mWaveSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!mWaveSwipeRefreshLayout.isRefreshing() && (list == null || list.size() <= 0)){
            mWaveSwipeRefreshLayout.setRefreshing(true);
        }
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
        }, 500);
    }

    @Override
    public void handleMsg(Message msg) {
        String json = msg.getData().getString(Constant.JSON_DATA);
        if(TextUtils.equals(getString(R.string.try_agin), json)){
            MethodUtils.showToast(getActivity(), getResources().getString(R.string.try_agin), Toast.LENGTH_SHORT);
            setRefreFalse();
            return;
        }

        switch (msg.what){
            case TAG_SALE_YEJI_LIST:
                data = JosnUtil.gson.fromJson(json, type);
                ArrayList<GainlistEntity> listData = (ArrayList)data.getGainlist();
                if(listData == null){
                    MethodUtils.showToast(getActivity(), "暂无数据", Toast.LENGTH_SHORT);
                    break;
                }
                if(TextUtils.equals(data.getResult(), "0") || listData.size() <= 0){
                    MethodUtils.showToast(getActivity(), data.getMessage(), Toast.LENGTH_SHORT);
                    setRefreFalse();
                    break;
                }
                if(adapter == null){
                    list.addAll(listData);
                    adapter = new SaleSearchAdapter(getActivity(), list, listView);
                    listView.setAdapter(adapter);
                } else {
                    if(flag == 1){
                        list = listData;
                    } else {
                        list.addAll(listData);
                    }
                    adapter.setData(list);

                }
                if(flag * 15 >= Integer.valueOf(data.getCount())){
                    footView.setFlag(0);
                } else {
                    footView.setFlag(flag);
                }
                flag = flag + 1;
                listData = null;
                setRefreFalse();
                break;
        }
    }

    @Override
    public void loadingMore() {
        askData();
    }
}
