package com.yingke.shengtai.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.activity.BussinessListFragmntAddActivity;
import com.yingke.shengtai.adapter.BussinessListFragmentAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.BussinessListData;
import com.yingke.shengtai.moudle.BussinessListData.BusinesslistEntity;
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
 * Created by yanyiheng on 15-8-25.
 */
public class SaleBussinessFragment extends BaseFragment implements FootView.LoadingMoreListener, WaveSwipeRefreshLayout.OnRefreshListener{
    public static final String TAG = SaleBussinessFragment.class.getSimpleName();
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private View parentView;
    private ListView listView;
    private FootView footView;

    private BussinessListFragmentAdapter adapter;
    private DetailMenuView menu;

    private Type type;
    private int flag = 1;
    private ArrayList<BusinesslistEntity> list;
    private BussinessListData data;
    private TextView textTitle, textUpdate;


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
        parentView = inflater.inflate(R.layout.activity_busssiness_list_add, container, false);
        initUi();
        return parentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        flag = 1;
        askData();
    }

    private void askData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sid", MyApplication.getInstance().getUserInfor().getUserdetail().getSid());
        map.put("token",MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        map.put("action","view");
        map.put("uid", "0");
        getData(IApi.NETWORK_METHOD_POST, TAG_SALE_BUSSINESS_LIST, IApi.URL_SALE_BUSSINESS_LIST_LIST + "?page=" + flag, map);
    }

    private void initUi() {

        textTitle = (TextView) parentView.findViewById(R.id.view_title_name);
        textUpdate = (TextView) parentView.findViewById(R.id.textzhuanjie);
        parentView.findViewById(R.id.view_title_back).setVisibility(View.GONE);

        textTitle.setText("业务列表");

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) parentView.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView)parentView.findViewById(R.id.fragment_listView);

        textUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BussinessListFragmntAddActivity.class);
                intent.putExtra("FLAG", "add");
                startActivity(intent);
            }
        });

        footView = new FootView(getActivity());
        footView.initFooter(new FooterUIText(getActivity()), this, flag);
        footView.attachToListView(listView);
        listView.addFooterView(footView);
        listView.setOnScrollListener(footView.new AbsFooterScroller());

        list = new ArrayList<BusinesslistEntity>();
        type = new TypeToken<BussinessListData>() {}.getType();
        menu = new DetailMenuView(getActivity());
        mWaveSwipeRefreshLayout.setRefreshing(true);
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
        }, 500);
    }

    @Override
    public void handleMsg(Message msg) {
        String json = msg.getData().getString(Constant.JSON_DATA);
        if(TextUtils.equals(getString(R.string.try_agin), json)){
            MethodUtils.showToast(getActivity(), getResources().getString(R.string.try_agin), Toast.LENGTH_SHORT);
            setRefreFalse();setRefreFalse();
            return;
        }
        BussinessListData sssss = JosnUtil.gson.fromJson(json, type);
        switch (msg.what){
            case TAG_SALE_BUSSINESS_LIST:
                if(TextUtils.equals(sssss.getResult(), "0") ){
                    setRefreFalse();
                    break;
                }
                if(sssss.getBusinesslist() == null){
                    footView.setFlag(0);
                    setRefreFalse();
                    break;
                }
                if(adapter == null){
                    list.addAll(sssss.getBusinesslist());
                    adapter = new BussinessListFragmentAdapter(getActivity(), list, listView);
                    adapter.setSource(true);
                    listView.setAdapter(adapter);
                } else {
                    if(flag == 1){
                        list = sssss.getBusinesslist();
                    } else {
                        list.addAll(sssss.getBusinesslist());
                    }
                    adapter.setData(list);

                }
                if(flag * 15 >= Integer.valueOf(sssss.getCount())){
                    footView.setFlag(0);
                } else {
                    footView.setFlag(flag);
                }
                flag = flag + 1;
                setRefreFalse();
                break;
        }
    }

    @Override
    public void loadingMore() {
        askData();
    }
}
