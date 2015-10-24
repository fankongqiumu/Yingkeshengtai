package com.yingke.shengtai.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.BussinessDetailData;
import com.yingke.shengtai.moudle.BussinessListData.BusinesslistEntity;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.view.TitleView;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by yanyiheng on 15-8-16.客户列表合同详情
 */
public class SaleSearchDetailActivity extends BaseActivity implements View.OnClickListener, WaveSwipeRefreshLayout.OnRefreshListener{
    private TextView yewuNumber, hetongNumber, writeNumber, writePeople, progress, time, title, textChage, textTitle;
    private TextView recommentName, totalPrice, price, reCommentPrice, bussinessContent, bussinessJilu;
    private RelativeLayout relContent, relJilu;
    private LinearLayout linearRecommend, linearTotalPrice, linearPrice, recommentPrice;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private BusinesslistEntity data;
    private BussinessDetailData currentData;
    private Type type;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            data = (BusinesslistEntity)getIntent().getSerializableExtra(Constant.DATA_GUIDE_CENTER);
        } else {
            data = (BusinesslistEntity)savedInstanceState.getSerializable(Constant.DATA_GUIDE_CENTER);
        }
        setContentView(R.layout.fragment_sale_search_detail);
        initUi();
        mWaveSwipeRefreshLayout.setRefreshing(true);
    }

    private void initUi() {
        textChage = (TextView) findViewById(R.id.textzhuanjie);
        textTitle = (TextView) findViewById(R.id.view_title_name);
        textChage.setOnClickListener(this);
        textChage.setText("修改");
        textTitle.setText(data.getTitle());

        title = (TextView)findViewById(R.id.item_text_title);
        time = (TextView)findViewById(R.id.item_text_time);
        progress = (TextView)findViewById(R.id.item_text_progress);
        yewuNumber = (TextView)findViewById(R.id.business_number);
        hetongNumber = (TextView)findViewById(R.id.hetong_number);
        writeNumber = (TextView)findViewById(R.id.write_time);
        writePeople = (TextView)findViewById(R.id.custom_name);
        recommentName = (TextView)findViewById(R.id.business_recoment_time);
        totalPrice = (TextView)findViewById(R.id.business_totle_price);
        price = (TextView)findViewById(R.id.business_price);
        reCommentPrice = (TextView)findViewById(R.id.business_recomment_price);
        bussinessContent = (TextView)findViewById(R.id.business_content);
        bussinessJilu = (TextView)findViewById(R.id.business_jilu);

        relContent = (RelativeLayout)findViewById(R.id.relContent);
        relJilu = (RelativeLayout)findViewById(R.id.relJilu);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);

        title.setText(data.getTitle());
        time.setText(MethodUtils.returnTime(data.getCreatedate()));
        progress.setText(data.getStatus());
        if(TextUtils.equals(data.getStatusvalue(), "-1")){
            progress.setBackgroundResource(R.mipmap.dark_red_bg);
        } else if(TextUtils.equals(data.getStatusvalue(), "200")){
            progress.setBackgroundResource(R.drawable.green_bg);
        } else {
            progress.setBackgroundResource(R.mipmap.dark_blue_bg);
        }
        linearRecommend = (LinearLayout)findViewById(R.id.linear_recommend);
        linearTotalPrice = (LinearLayout)findViewById(R.id.linear_totleprice);
        linearPrice = (LinearLayout)findViewById(R.id.linear_price);
        recommentPrice = (LinearLayout)findViewById(R.id.linear_recommendprice);
        relContent = (RelativeLayout)findViewById(R.id.relContent);
        relJilu = (RelativeLayout)findViewById(R.id.relJilu);

        if(!TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getSid(), data.getSid())){
            linearRecommend.setVisibility(View.GONE);
            linearTotalPrice.setVisibility(View.GONE);
            linearPrice.setVisibility(View.GONE);
            recommentPrice.setVisibility(View.GONE);
            relContent.setVisibility(View.GONE);
            relJilu.setVisibility(View.GONE);
        } else {
            relContent.setOnClickListener(this);
            relJilu.setOnClickListener(this);
        }

        findViewById(R.id.view_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleSearchDetailActivity.this.finish();
            }
        });

        type = new TypeToken<BussinessDetailData>(){}.getType();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constant.DATA_GUIDE_CENTER, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
            case TAG_DETAIL_BUSSINESS:
                currentData = JosnUtil.gson.fromJson(json, type);
                if(currentData == null){
                    break;
                }
                if(TextUtils.equals("0", currentData.getResult())){
                    MethodUtils.showToast(this, getResources().getString(R.string.try_agin), Toast.LENGTH_SHORT);
                    setRefreFalse();
                    break;
                }
                if(TextUtils.equals(currentData.getDetail().getStatusvalue(), "-1")){
                    progress.setBackgroundResource(R.mipmap.dark_red_bg);
                } else if(TextUtils.equals(currentData.getDetail().getStatusvalue(), "200")){
                    progress.setBackgroundResource(R.drawable.green_bg);
                } else {
                    progress.setBackgroundResource(R.mipmap.dark_blue_bg);
                }
                title.setText(currentData.getDetail().getTitle());
                time.setText(MethodUtils.returnTime(currentData.getDetail().getCreatedate()));
                progress.setText(currentData.getDetail().getStatus());
                yewuNumber.setText(currentData.getDetail().getContractid());
                hetongNumber.setText(currentData.getDetail().getBusinessid());
                writeNumber.setText(MethodUtils.returnTime(currentData.getDetail().getCreatedate()));
                writePeople.setText(currentData.getDetail().getCusname());
                if(TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getSid(), data.getSid())){
                   if(!TextUtils.isEmpty(currentData.getDetail().getRefereename())){
                       recommentName.setText(currentData.getDetail().getRefereename());
                   }
                    if(!TextUtils.isEmpty(currentData.getDetail().getRefereeamount())){
                        reCommentPrice.setText(new DecimalFormat("0.00").format(Double.valueOf(currentData.getDetail().getRefereeamount())));
                    }
                    if(!TextUtils.isEmpty(currentData.getDetail().getAmount())){
                        totalPrice.setText(new DecimalFormat("0.00").format(Double.valueOf(currentData.getDetail().getAmount())));
                    }
                    if(!TextUtils.isEmpty(currentData.getDetail().getSaleamount())){
                        price.setText(new DecimalFormat("0.00").format(Double.valueOf(currentData.getDetail().getSaleamount())));
                    }
                    content = currentData.getDetail().getText();
                }
                setRefreFalse();
                break;
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.relContent:
            if(TextUtils.isEmpty(content)){
                break;
            }
            intent = new Intent(SaleSearchDetailActivity.this, TermsActivity.class);
            intent.putExtra("text", content);
            startActivity(intent);
                break;
            case R.id.textzhuanjie:
                intent = new Intent(SaleSearchDetailActivity.this, BussinessUpdateActivity.class);
                intent.putExtra("DATA", currentData);
                intent.putExtra("FLAG", "update");
                startActivity(intent);
                break;
            case R.id.relJilu:
                intent = new Intent(this, TrackActivity.class);
                intent.putExtra(Constant.DATA_GUIDE_CENTER, data);
                startActivity(intent);
                break;
        }
    }

    private void askData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sid", MyApplication.getInstance().getUserInfor().getUserdetail().getSid());
        map.put("token", MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        map.put("uid", data.getUid());
        map.put("action", "view");
        getData(IApi.NETWORK_METHOD_POST, TAG_DETAIL_BUSSINESS, IApi.URL_SALE_BUSSINESS_LIST + "/" + data.getBid(), map);
    }

    @Override
    public void onRefresh() {
        askData();
    }

    private void setRefreFalse(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }
}
