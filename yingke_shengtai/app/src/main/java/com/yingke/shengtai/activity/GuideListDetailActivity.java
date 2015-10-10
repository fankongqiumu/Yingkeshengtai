package com.yingke.shengtai.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.adapter.CenterDetailAdapter;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.GuideListData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.UiUtil;
import com.yingke.shengtai.view.FootView;
import com.yingke.shengtai.view.GridView;
import com.yingke.shengtai.view.TitleView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.StringTokenizer;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by yanyiheng on 15-8-15.
 */
public class GuideListDetailActivity extends BaseActivity implements View.OnClickListener,WaveSwipeRefreshLayout.OnRefreshListener, FootView.LoadingMoreListener{
    private TitleView titleView;
    private TextView textHeadTitle;
    private ListView listView;
    private View headView;
    private GridView gridView;
    private ImageView imageArow;
//    private FootView footView;
//    private WebView textHeadContent;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private CenterDetailAdapter adapter;

    private Type type;

    private String title, id;
    private GuideListData data;
//    private int flag = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            title = getIntent().getStringExtra(Constant.DATA_GUIDE_TITLE);
            id = getIntent().getStringExtra(Constant.DATA_GUIDE_ID);

        } else {
            title = savedInstanceState.getString(Constant.DATA_GUIDE_TITLE);
            id = savedInstanceState.getString(Constant.DATA_GUIDE_ID);
        }
        setContentView(R.layout.fragment_guide);
        initUi();
    }

    private void initUi() {
        titleView = (TitleView)findViewById(R.id.fragment_title);
        titleView.setTitleView(title);
        headView = LayoutInflater.from(this).inflate(R.layout.guide_detail_head, null);
        imageArow = (ImageView) headView.findViewById(R.id.arrow);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView)findViewById(R.id.fragment_listView);
        listView.addHeaderView(headView);
        headView.findViewById(R.id.line).setOnClickListener(this);
        textHeadTitle = (TextView)headView.findViewById(R.id.detail_head_title);
//        textHeadContent = (WebView)headView.findViewById(R.id.detail_head_content);

        gridView = new GridView(this);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gridView.setPadding(0, 0 , 0, UiUtil.dip2px(30));
        gridView.setLayoutParams(layoutParams);
        listView.addFooterView(gridView);
        listView.setDividerHeight(0);

        askData();

        type = new TypeToken<GuideListData>() {}.getType();

    }

    private void askData() {
        getData(IApi.NETWORK_METHOD_GET, TAG_GUIDE_TWO, IApi.URL_GUIDLIST_TWO + id, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(data == null){
            mWaveSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.DATA_GUIDE_ID, id);
        outState.putString(Constant.DATA_GUIDE_TITLE, title);
    }

    @Override
    public void handleMsg(Message msg) {
        String json = msg.getData().getString(Constant.JSON_DATA);
        if(TextUtils.isEmpty(json) || json.toString().length() < 30){
            setRefreFalse();
//            footView.setFlag(0);
            return;
        }
//        footView.onSuccess();
        data = JosnUtil.gson.fromJson(json, type);
        textHeadTitle.setText(data.getTitle());
//        textHeadContent.loadDataWithBaseURL(null, data.getText(), "text/html", "utf-8", null);
        switch (msg.what){
            case TAG_GUIDE_TWO:
                gridView.setSouce(true);
                gridView.CreateView(data.getSubchannelinfo());
                StringTokenizer tokenizer = new StringTokenizer(data.getText(), "{}");
                ArrayList<String> list = new ArrayList<String>();
                while (tokenizer.hasMoreTokens()){
                    list.add(tokenizer.nextToken());
                }
                if(adapter == null){
                    adapter = new CenterDetailAdapter(this, list, data.getMedialist(), true, true);
                    listView.setAdapter(adapter);
                } else {
                    adapter.setData(list, data.getMedialist());
                }
                setRefreFalse();
                break;
        }
    }


    private void setRefreFalse(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 更新が終了したらインジケータ非表示
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        }, 300);
    }

    @Override
    public void loadingMore() {
        askData();
    }

    @Override
    public void onRefresh() {
        askData();
    }

    @Override
    public void onClick(View v) {
    if(v.getId() == R.id.line){
        if(adapter != null && adapter.getCount() != 0){
            if(adapter.isShouSuo()){
                imageArow.setBackgroundResource(R.mipmap.arrow_up);
                adapter.setIsShouSuo(false);
                adapter.notifyDataSetChanged();
            } else {
                imageArow.setBackgroundResource(R.mipmap.arrow_down);
                adapter.setIsShouSuo(true);
                adapter.notifyDataSetChanged();
            }
        }
    }
    }
}
