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
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.adapter.SearchListAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.SearchItemData;
import com.yingke.shengtai.moudle.SearchItemData.BusinesslistEntity;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
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
public class SearchFragment extends BaseFragment implements FootView.LoadingMoreListener, WaveSwipeRefreshLayout.OnRefreshListener{
    public static final String TAG = SearchFragment.class.getSimpleName();
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private View parentView;
    private TitleView titleView;
    private ListView listView;
    private FootView footView;
    private TextView noData;

    private SearchListAdapter adapter;
    private SearchItemData data;
    private DetailMenuView menu;

    private Type type;
    private int flag = 1;
    private ArrayList<BusinesslistEntity> list;


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
        Map<String, String> map = new HashMap<String, String>();
        map.put("page",flag + "");
        map.put("uid", MyApplication.getInstance().getUserInfor().getUserdetail().getUid());
        map.put("token", MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        getData(IApi.NETWORK_METHOD_POST, TAG_SearchLIST, IApi.URL_SEARCH_LIST, map);

    }

    private void initUi() {
        titleView = (TitleView)parentView.findViewById(R.id.fragment_title);
        titleView.setTitleView(R.string.busines_find);
        titleView.getImageBack().setVisibility(View.GONE);
        titleView.getImagePeople().setVisibility(View.VISIBLE);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) parentView.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView)parentView.findViewById(R.id.fragment_listView);
        noData = (TextView)parentView.findViewById(R.id.no_data);

        footView = new FootView(getActivity());
        footView.initFooter(new FooterUIText(getActivity()), this, flag);
        footView.attachToListView(listView);
        listView.addFooterView(footView);
        listView.setOnScrollListener(footView.new AbsFooterScroller());

        list = new ArrayList<BusinesslistEntity>();
        type = new TypeToken<SearchItemData>() {}.getType();

        menu = new DetailMenuView(getActivity());
        titleView.getImagePeople().setOnClickListener(new View.OnClickListener() {
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
        if(hidden ){
            mWaveSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(data == null){
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
        if(TextUtils.isEmpty(json) || json.toString().length() < 30){
            setRefreFalse();
            return;
        }
        data = JosnUtil.gson.fromJson(json, type);
        ArrayList<BusinesslistEntity> listData = data.getBusinesslist();
        if(listData == null && list.size() == 0){
            setRefreFalse();
            noData.setVisibility(View.VISIBLE);
            return;
        }
        noData.setVisibility(View.GONE);
        switch (msg.what){
            case TAG_SearchLIST:
                if(adapter == null){
                    list.addAll(listData);
                    adapter = new SearchListAdapter(getActivity(), list, listView);
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
