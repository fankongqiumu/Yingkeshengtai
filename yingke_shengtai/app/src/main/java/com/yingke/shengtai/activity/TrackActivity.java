package com.yingke.shengtai.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.adapter.TrackAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.BussinessListData.BusinesslistEntity;
import com.yingke.shengtai.moudle.TrckListData;
import com.yingke.shengtai.moudle.TrckListData.TracklistEntity;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.view.FootView;
import com.yingke.shengtai.view.TitleView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by yanyiheng on 15-9-13. 跟踪记录
 */
public class TrackActivity extends BaseActivity implements View.OnClickListener,FootView.LoadingMoreListener,WaveSwipeRefreshLayout.OnRefreshListener{
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private View headView;
    private TitleView titleView;
    private ListView listView;
    private ImageView avator;
    private TextView name, progress, time;
    private TrackAdapter adapter;
    private BusinesslistEntity bussinessData;
    private FootView footView;
//    private int flag;
    private TrckListData data;
    private ArrayList<TracklistEntity> list;
    private Type type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            bussinessData = (BusinesslistEntity)getIntent().getSerializableExtra(Constant.DATA_GUIDE_CENTER);
        } else {
            bussinessData = (BusinesslistEntity)savedInstanceState.getSerializable(Constant.DATA_GUIDE_CENTER);
        }
        setContentView(R.layout.fragment_guide);
        initUi();
        mWaveSwipeRefreshLayout.setRefreshing(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        askData();
    }

    private void askData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sid", MyApplication.getInstance().getUserInfor().getUserdetail().getSid());
        map.put("token", MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        map.put("action", "view");
        map.put("tid", "0");
        getData(IApi.NETWORK_METHOD_POST, TAG_VIEW_BUSSINESS_CUSTOMER, IApi.URL_TRACKS + bussinessData.getBid(), map);
    }

    private void initUi() {
        titleView = (TitleView) findViewById(R.id.fragment_title);
        titleView.setTitleView("跟踪记录");
        titleView.getImagePeople().setImageResource(R.mipmap.plus_icon);
        titleView.getImagePeople().setVisibility(View.VISIBLE);
        titleView.getImagePeople().setOnClickListener(this);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView) findViewById(R.id.fragment_listView);
        headView = LayoutInflater.from(this).inflate(R.layout.activity_track_head, null);

        name = (TextView) headView.findViewById(R.id.item_text_title);
        progress = (TextView) headView.findViewById(R.id.item_text_progress);
        time = (TextView) headView.findViewById(R.id.item_text_time);

        listView.addHeaderView(headView);
//        footView = new FootView(this);
//        footView.initFooter(new FooterUIText(this), this, flag);
//        footView.attachToListView(listView);
//        listView.addFooterView(footView);

//        listView.setOnScrollListener(footView.new AbsFooterScroller());
        list = new ArrayList<TracklistEntity>();
        type = new TypeToken<TrckListData>(){}.getType();


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constant.DATA_GUIDE_CENTER, bussinessData);
    }


    @Override
    public void onRefresh() {
//        flag = 1;
//        footView.setResetParam();
        askData();
    }

    @Override
    public void handleMsg(Message msg) {
        String json = msg.getData().getString(Constant.JSON_DATA);
        if(TextUtils.equals(getString(R.string.try_agin), json)){
            MethodUtils.showToast(this, getResources().getString(R.string.try_agin), Toast.LENGTH_SHORT);
            setRefreFalse();
            return;
        }

        switch (msg.what){
            //
            case TAG_VIEW_BUSSINESS_CUSTOMER:
                data = JosnUtil.gson.fromJson(json, type);
                ArrayList<TracklistEntity> listData = (ArrayList)data.getTracklist();
                if(TextUtils.equals(data.getResult(), "0") || listData== null || listData.size() <= 0){
                    setRefreFalse();
                    MethodUtils.showToast(this, "暂无记录数据!", Toast.LENGTH_SHORT);
                    break;
                }
                name.setText(data.getTitle());
                progress.setText(data.getStatus());
                time.setText(MethodUtils.returnTime(data.getCreatedate()));
                if(TextUtils.equals(data.getStatusvalue(), "-1")){
                    progress.setBackgroundResource(R.mipmap.dark_red_bg);
                } else if(TextUtils.equals(data.getStatusvalue(), "200")){
                    progress.setBackgroundResource(R.drawable.green_bg);
                } else {
                    progress.setBackgroundResource(R.mipmap.dark_blue_bg);
                }
                if(adapter == null){
                    list.addAll(listData);
                    adapter = new TrackAdapter(this, list, listView);
                    listView.setAdapter(adapter);
                } else {
//                    if(flag == 1){
                        list = listData;
//                    } else {
//                        list.addAll(listData);
//                    }
                    adapter.setData(list);

                }
//                if(flag * 15 >= Integer.valueOf(data.getCount())){
//                    footView.setFlag(0);
//                } else {
//                    footView.setFlag(flag);
//                }
//                flag = flag + 1;
                listData = null;
                setRefreFalse();
                break;
        }

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
    public void loadingMore() {
        askData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())   {
            case R.id.view_title_people:
                Intent intent = new Intent(this, TrackWriteActivity.class);
                intent.putExtra(Constant.DATA_GUIDE_CENTER, bussinessData);
                startActivity(intent);
                break;
        }
    }
}
