package com.yingke.shengtai.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.CustomerListData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.view.TitleView;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by yanyiheng on 15-8-19.
 */
public class SaleCustomerDetailActivity extends BaseActivity implements WaveSwipeRefreshLayout.OnRefreshListener, View.OnClickListener{
    private TextView place, sex, time, number, people, progress, textPhone, times;
    private RelativeLayout  comment, history, write;
    private TitleView titleView;
    private String id, name;
    private ImageView avator, msg;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private CustomerListData data;
    private Type type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            id = getIntent().getStringExtra(Constant.DATA_GUIDE_TWO);
            name = getIntent().getStringExtra(Constant.DATA_GUIDE_TITLE);
        } else {
            id = savedInstanceState.getString(Constant.DATA_GUIDE_TWO);
            name = savedInstanceState.getString(Constant.DATA_GUIDE_TITLE);
        }
        setContentView(R.layout.activity_custom_detail);
        initUi();
        askData();
    }

    private void initUi() {
        place = (TextView) findViewById(R.id.place);
        sex = (TextView) findViewById(R.id.sex);
        textPhone = (TextView) findViewById(R.id.text_phone);
        time = (TextView) findViewById(R.id.time);
        number = (TextView) findViewById(R.id.number);
        people = (TextView) findViewById(R.id.text_name);
        history = (RelativeLayout) findViewById(R.id.history);
        write = (RelativeLayout) findViewById(R.id.write);
        comment = (RelativeLayout) findViewById(R.id.comment);
        progress = (TextView) findViewById(R.id.text_progress);
        avator = (ImageView)findViewById(R.id.image_avator);
        msg = (ImageView)findViewById(R.id.msg);
        times = (TextView) findViewById(R.id.text_time);
        titleView = (TitleView)findViewById(R.id.fragment_title);
        titleView.setTitleView(name);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        mWaveSwipeRefreshLayout.setRefreshing(true);

//

        comment.setOnClickListener(this);
        history.setOnClickListener(this);
        write.setOnClickListener(this);
        msg.setOnClickListener(this);

        type = new TypeToken<CustomerListData>(){}.getType();
    }

    private void askData() {
        Map map = new HashMap<String, String>();
        map.put("sid", MyApplication.getInstance().getUserInfor().getUserdetail().getSid());
        map.put("token", MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        getData(IApi.NETWORK_METHOD_POST, TAG_CUSSTOM_DETAIL, IApi.URL_SALE_CUSTOMER_LIST + "/" + id, map);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.DATA_GUIDE_TWO, id);
        outState.putString(Constant.DATA_GUIDE_TITLE, name);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.comment:
                intent = new Intent(this, CustomerCommentActivity.class);
                intent.putExtra(Constant.DATA_GUIDE_CENTER, data);
                startActivity(intent);
                break;
            case R.id.write:
                intent = new Intent(this, BussinessWriteActivity.class);
                intent.putExtra(Constant.DATA_GUIDE_CENTER, data);
                startActivity(intent);
                break;
            case R.id.history:
            case R.id.msg:
                intent = new Intent(this, ChatActivity.class);
                intent.putExtra("userId", data.getUserdetail().getImid());
                intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
                intent.putExtra("name", data.getUserdetail().getName());
                startActivity(intent);
                break;

        }
    }

    @Override
    public void handleMsg(Message msg) {
        String json = msg.getData().getString(Constant.JSON_DATA);
        if(TextUtils.equals(json, getString(R.string.try_agin))){
            mWaveSwipeRefreshLayout.setRefreshing(false);
           return;
        }
        switch (msg.what){
            case TAG_CUSSTOM_DETAIL:
                data = JosnUtil.gson.fromJson(json, type);
                if(TextUtils.equals("0", data.getResult())){
                    mWaveSwipeRefreshLayout.setRefreshing(false);
                    break;
                }
                if(data.getUserdetail() == null){
                    mWaveSwipeRefreshLayout.setRefreshing(false);
                    break;
                }
//                if(TextUtils.equals(data.getStatusvalue(), "-1")){
//                    progress.setBackgroundResource(R.mipmap.dark_red_bg);
//                } else if(TextUtils.equals(data.getDetail().getStatusvalue(), "200")){
//                    progress.setBackgroundResource(R.drawable.green_bg);
//                } else {
//                    progress.setBackgroundResource(R.mipmap.dark_blue_bg);
//                }
                if(!TextUtils.isEmpty(data.getUserdetail().getImid())) {
                    this.msg.setVisibility(View.VISIBLE);
                }
                textPhone.setText(data.getUserdetail().getMobile());
                place.setText(data.getUserdetail().getLocation());
                sex.setText(TextUtils.equals("0", data.getUserdetail().getSex()) ? "男" : "女");
                time.setText(MethodUtils.returnTime(data.getUserdetail().getRegdate()));
                times.setText(MethodUtils.returnTime(data.getUserdetail().getRegdate()));
                number.setText(data.getBusinessid());
                progress.setText(data.getStatus());
                if(TextUtils.equals(data.getStatusvalue(), "-1")){
                    progress.setBackgroundResource(R.mipmap.dark_red_bg);
                } else if(TextUtils.equals(data.getStatusvalue(), "200")){
                    progress.setBackgroundResource(R.drawable.green_bg);

                } else {
                    progress.setBackgroundResource(R.mipmap.dark_blue_bg);
                }
                people.setText(data.getUserdetail().getName());
                if(TextUtils.equals("0", data.getUserdetail().getSex())){
                    avator.setImageResource(R.mipmap.famel_customer);
                } else {
                    avator.setImageResource(R.mipmap.male_customer);
                }
                mWaveSwipeRefreshLayout.setRefreshing(false);
                break;
        }
    }

    @Override
    public void onRefresh() {
        askData();
    }
}
