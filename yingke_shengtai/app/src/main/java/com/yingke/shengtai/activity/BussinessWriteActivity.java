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
import com.yingke.shengtai.adapter.BussinessListAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.BussinessListData;
import com.yingke.shengtai.moudle.BussinessListData.BusinesslistEntity;
import com.yingke.shengtai.moudle.CustomerListData;
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
 * Created by yanyiheng on 15-8-24.客户列表详情客户业务的详情页
 */
public class BussinessWriteActivity extends BaseActivity implements View.OnClickListener, FootView.LoadingMoreListener,WaveSwipeRefreshLayout.OnRefreshListener{

    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private View headView;
    private TitleView titleView;
    private ListView listView;
    private ImageView avator;
    private TextView name, phone, time;
    private CustomerListData customerData;
    private ArrayList<BusinesslistEntity> list;
    private BussinessListData data;
    private Type type;
    private BussinessListAdapter adapter;
//    private FootView footView;
//    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            customerData = (CustomerListData)getIntent().getSerializableExtra(Constant.DATA_GUIDE_CENTER);
        } else {
            customerData = (CustomerListData)savedInstanceState.getSerializable(Constant.DATA_GUIDE_CENTER);
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
        map.put("uid", customerData.getUserdetail().getUid());
        map.put("action", "view");
        getData(IApi.NETWORK_METHOD_POST, TAG_CUSSTOM_DETAIL_BUSSINESS, IApi.URL_SALE_BUSSINESS_LIST, map);
    }

    private void initUi() {
        titleView = (TitleView) findViewById(R.id.fragment_title);
        titleView.setTitleView(R.string.customer_detail_write);
        titleView.getImagePeople().setImageResource(R.mipmap.plus_icon);
        titleView.getImagePeople().setVisibility(View.VISIBLE);
        titleView.getImagePeople().setOnClickListener(this);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView) findViewById(R.id.fragment_listView);
        headView = LayoutInflater.from(this).inflate(R.layout.activity_customer_comment_head, null);

        avator = (ImageView) headView.findViewById(R.id.image_avator);
        name = (TextView) headView.findViewById(R.id.text_name);
        phone = (TextView) headView.findViewById(R.id.text_phone);
        time = (TextView) headView.findViewById(R.id.text_time);

        listView.addHeaderView(headView);
//        footView = new FootView(this);
//        footView.initFooter(new FooterUIText(this), this, flag);
//        footView.attachToListView(listView);
//        listView.addFooterView(footView);

//        listView.setOnScrollListener(footView.new AbsFooterScroller());
        list = new ArrayList<BusinesslistEntity>();
        type = new TypeToken<BussinessListData>(){}.getType();

        name.setText(customerData.getUserdetail().getName());
        phone.setText(customerData.getUserdetail().getMobile());
        time.setText(MethodUtils.returnTime(customerData.getUserdetail().getRegdate()));

        avator.setImageResource(R.drawable.mini_avatar_shadow);
        headView.findViewById(R.id.msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BussinessWriteActivity.this, ChatActivity.class);
                intent.putExtra("userId", customerData.getUserdetail().getImid());
                intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
                intent.putExtra("name", customerData.getUserdetail().getName());
                startActivity(intent);
            }
        });
        if(TextUtils.isEmpty(customerData.getUserdetail().getImid())){
            headView.findViewById(R.id.msg).setVisibility(View.GONE);
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constant.DATA_GUIDE_CENTER, customerData);
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
            case TAG_CUSSTOM_DETAIL_BUSSINESS:
                data = JosnUtil.gson.fromJson(json, type);
                ArrayList<BusinesslistEntity> listData = data.getBusinesslist();
                if(TextUtils.equals(data.getResult(), "0") || data.getBusinesslist()== null || data.getBusinesslist().size() <= 0){
                    setRefreFalse();
                    break;
                }


                if(adapter == null){
                    list.addAll(listData);
                    adapter = new BussinessListAdapter(this, list, listView);
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
        switch (view.getId()){
            case R.id.view_title_people:
                Intent intent = new Intent(this, BussinessAddActivity.class);
                intent.putExtra("DATA", customerData);
                intent.putExtra("FLAG", "add");
                startActivity(intent);
                break;
        }
    }
}
