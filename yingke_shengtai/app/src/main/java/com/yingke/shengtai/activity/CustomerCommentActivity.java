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
import com.yingke.shengtai.adapter.CustomerCommentAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.CommentItemData;
import com.yingke.shengtai.moudle.CommentItemData.ReviewlistEntity;
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
 * Created by yanyiheng on 15-9-9. 评价列表
 */
public class CustomerCommentActivity extends BaseActivity implements View.OnClickListener,FootView.LoadingMoreListener,WaveSwipeRefreshLayout.OnRefreshListener{
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private View headView;
    private TitleView titleView;
    private ListView listView;
    private ImageView avator, msg;
    private TextView name, phone, time;
    private CustomerListData customerData;
    private ArrayList<ReviewlistEntity> list;
    private CommentItemData data;
    private Type type;
    private CustomerCommentAdapter adapter;
    private FootView footView;
    private int flag;

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
        getData(IApi.NETWORK_METHOD_POST, TAG_CUSTOMER_COMENT, IApi.URL_SALE_CUSTOMER_COMMENT + customerData.getUserdetail().getUid(), map);
    }

    private void initUi() {
        titleView = (TitleView) findViewById(R.id.fragment_title);
        titleView.setTitleView("客户评价");
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
        msg = (ImageView) headView.findViewById(R.id.msg);
        if(TextUtils.isEmpty(customerData.getUserdetail().getImid())){
            msg.setVisibility(View.GONE);
        } else {
            msg.setOnClickListener(this);
        }


        listView.addHeaderView(headView);
//        footView = new FootView(this);
//        footView.initFooter(new FooterUIText(this), this, flag);
//        footView.attachToListView(listView);
//        listView.addFooterView(footView);

//        listView.setOnScrollListener(footView.new AbsFooterScroller());
        list = new ArrayList<ReviewlistEntity>();
        type = new TypeToken<CommentItemData>(){}.getType();

        name.setText(customerData.getUserdetail().getName());
        phone.setText(customerData.getUserdetail().getMobile());
        time.setText(MethodUtils.returnTime(customerData.getUserdetail().getRegdate()));


        avator.setImageResource(R.drawable.mini_avatar_shadow);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constant.DATA_GUIDE_CENTER, customerData);
    }

    @Override
    public void onRefresh() {
        flag = 1;
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
            case TAG_CUSTOMER_COMENT:
                data = JosnUtil.gson.fromJson(json, type);
                ArrayList<ReviewlistEntity> listData = (ArrayList)data.getReviewlist();
                if(TextUtils.equals(data.getResult(), "0") ){
                    MethodUtils.showToast(this, getResources().getString(R.string.try_agin), Toast.LENGTH_SHORT);
                    setRefreFalse();
                    break;
                }
                if(listData== null || listData.size() <= 0){
                    MethodUtils.showToast(this, "暂无评价", Toast.LENGTH_SHORT);
                    setRefreFalse();
                    break;
                }
                if(adapter == null){
                    list.addAll(listData);
                    adapter = new CustomerCommentAdapter(this, list, listView);
                    listView.setAdapter(adapter);
                } else {
                    if(flag == 1){
                        list = listData;
                    } else {
                        list.addAll(listData);
                    }
                    adapter.setData(list);

                }
//                if(flag * 15 >= Integer.valueOf(data.getCount())){
//                    footView.setFlag(0);
//                } else {
//                    footView.setFlag(flag);
//                }
                flag = flag + 1;
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
            Intent intent = new Intent(this, CustomerComentWriteActivity.class);
            intent.putExtra("DATA", customerData);
            startActivity(intent);
            break;
        case R.id.msg:
            Intent intents = new Intent(this, ChatActivity.class);
            intents.putExtra("userId", customerData.getUserdetail().getImid());
            intents.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
            intents.putExtra("name", customerData.getUserdetail().getDisplayname());
            startActivity(intents);
            break;
     }
    }
}
