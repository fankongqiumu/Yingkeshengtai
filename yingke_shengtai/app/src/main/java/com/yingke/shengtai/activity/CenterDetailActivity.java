package com.yingke.shengtai.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.adapter.CenterDetailAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.CenterDetailData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.UiUtil;
import com.yingke.shengtai.view.TitleView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.StringTokenizer;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by yanyiheng on 15-9-2.
 */
public class CenterDetailActivity extends BaseActivity implements WaveSwipeRefreshLayout.OnRefreshListener{
    private TitleView titleView;
    private TextView title, time;
    private CenterDetailData data;
    private CenterDetailAdapter adapter;
    private TextView bankuai;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private View headView;
    private Type type;
    private ListView listView;
    private TextView textView;

    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            id = getIntent().getStringExtra(Constant.DATA_GUIDE_ID);

        } else {
            id = savedInstanceState.getString(Constant.DATA_GUIDE_ID);
        }
        setContentView(R.layout.fragment_guide);
        initUi();
    }

    private void initUi() {
        titleView = (TitleView)findViewById(R.id.fragment_title);
//        titleView.setTitleView(R.string.zixun_detail);
        headView = LayoutInflater.from(this).inflate(R.layout.activity_center_detail, null);
        bankuai = (TextView) headView.findViewById(R.id.bankuai);
        title = (TextView)headView.findViewById(R.id.title);
        time = (TextView)headView.findViewById(R.id.time);
        time.setVisibility(View.GONE);
        listView = (ListView) findViewById(R.id.fragment_listView);
        listView.setDividerHeight(0);
        listView.addHeaderView(headView);
        textView = new TextView(this);
        textView.setTextSize(15);
        textView.setTextColor(0XFF9b9b9b);
        textView.setPadding(UiUtil.dip2px(15), 0, 0, 0);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, UiUtil.dip2px(50));
        textView.setLayoutParams(params);
        listView.addFooterView(textView);


        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        askData();

        type = new TypeToken<CenterDetailData>() {}.getType();

    }

    private void askData() {
        getData(IApi.NETWORK_METHOD_GET, TAG_CENTER_DETAIL, IApi.URL_CENTER_DETAIL + id, null);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.DATA_GUIDE_ID, id);
    }


    @Override
    public void handleMsg(Message msg) {
        String json = msg.getData().getString(Constant.JSON_DATA);
        if(TextUtils.isEmpty(json) || json.toString().length() < 30){
            setRefreFalse();
            return;
        }
        switch (msg.what){
            case TAG_CENTER_DETAIL:
                data = JosnUtil.gson.fromJson(json, type);
                time.setVisibility(View.VISIBLE);
                title.setText(data.getTitle());
                time.setText(data.getDate());
                if(!TextUtils.isEmpty(data.getChannelname())) {
                    bankuai.setVisibility(View.VISIBLE);
                    bankuai.setText(data.getChannelname());
                    bankuai.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CenterDetailActivity.this, GuideListDetailActivity2.class);
                            intent.putExtra(Constant.DATA_GUIDE_TITLE, data.getTitle());
                            intent.putExtra(Constant.DATA_GUIDE_ID, data.getId());
                            startActivity(intent);

                        }
                    });
                } else {
                    bankuai.setOnClickListener(null);
                    bankuai.setVisibility(View.GONE);
                }
                if(TextUtils.equals(data.getTotal(), "0")){
                    textView.setVisibility(View.GONE);
                } else {
                    textView.setText("浏览量 " + data.getTotal());
                    textView.setVisibility(View.VISIBLE);
                }

                StringTokenizer tokenizer = new StringTokenizer(data.getText(), "{}");
                ArrayList<String> list = new ArrayList<String>();
                while (tokenizer.hasMoreTokens()){
                  list.add(tokenizer.nextToken());
                }
                if(adapter == null){
                    adapter = new CenterDetailAdapter(this, list, data.getMedialist());
                    listView.setAdapter(adapter);
                } else {
                    adapter.setData(list, data.getMedialist());
                }
                setRefreFalse();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
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
    public void onRefresh() {
        askData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(data == null){
            mWaveSwipeRefreshLayout.setRefreshing(true);
        }
    }
}
