package com.yingke.shengtai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.moudle.BussinessDetailData;
import com.yingke.shengtai.moudle.ResultSuccessData;
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
 * Created by yanyiheng on 15-8-16.
 */
public class SearchDetailActivity extends BaseActivity implements  WaveSwipeRefreshLayout.OnRefreshListener{
    private TextView yewuNumber, hetongNumber, writeNumber, writePeople, source, progress, time, title;
    private ImageView imageMessage, star1, star2,star3,star4,star5;
    private ImageView [] imageArray = {star1, star2,star3,star4,star5};
    private TitleView titleView;
    private String id, titleString, bid;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private Type type;
    private ScrollView scrollView;
    private BussinessDetailData data;
    private LinearLayout line1;
    public static final int  ONE = 1;
    public static final int  TWO = 2;
    public static final int  THREE = 3;
    public static final int  FOUR = 4;
    public static final int  FIVE = 5;
    public static final int  REQUEST_CODE_CONTEXT_MENU = 6;
    private int star_value;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            id = getIntent().getStringExtra(Constant.DATA_GUIDE_ID);
            titleString = getIntent().getStringExtra(Constant.DATA_GUIDE_TITLE);
            bid = getIntent().getStringExtra("ID");

        } else {
            id = savedInstanceState.getString(Constant.DATA_GUIDE_ID);
            titleString = savedInstanceState.getString(Constant.DATA_GUIDE_TITLE);
            bid = savedInstanceState.getString("ID");
        }
        setContentView(R.layout.fragment_search_detail);
        initUi();
    }

    private void initUi() {
        titleView = (TitleView)findViewById(R.id.fragment_title);
        title = (TextView)findViewById(R.id.item_text_title);
        time = (TextView)findViewById(R.id.item_text_time);
        progress = (TextView)findViewById(R.id.item_text_progress);
        yewuNumber = (TextView)findViewById(R.id.business_number);
        hetongNumber = (TextView)findViewById(R.id.hetong_number);
        writeNumber = (TextView)findViewById(R.id.write_time);
        writePeople = (TextView)findViewById(R.id.business_people);
        source = (TextView)findViewById(R.id.source);
        line1 = (LinearLayout)findViewById(R.id.line1);
        scrollView = (ScrollView) findViewById(R.id.fragment_listView);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);

        titleView.setTitleView(titleString);
        title.setText(titleString);


         type = new TypeToken<BussinessDetailData>(){}.getType();
        imageMessage = (ImageView)findViewById(R.id.contact_people);
        imageArray[0] = (ImageView)findViewById(R.id.star1);
        imageArray[1] = (ImageView)findViewById(R.id.star2);
        imageArray[2] = (ImageView)findViewById(R.id.star3);
        imageArray[3] = (ImageView)findViewById(R.id.star4);
        imageArray[4] = (ImageView)findViewById(R.id.star5);
        mWaveSwipeRefreshLayout.setRefreshing(true);
        dialog = new ProgressDialog(this);

        askData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constant.DATA_GUIDE_ID, id);
        outState.putString(Constant.DATA_GUIDE_TITLE, titleString);
        outState.putString("ID", bid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
          switch (resultCode){
              case ONE:
                  star_value = ONE;
                  askStarData(ONE + "");
                  break;
              case TWO:
                  star_value = TWO;
                  askStarData(TWO + "");
                  break;
              case THREE:
                  star_value = THREE;
                  askStarData(THREE + "");
                  break;
              case FOUR:
                  star_value = FOUR;
                  askStarData(FOUR + "");
                  break;
              case FIVE:
                  star_value = FIVE;
                  askStarData(FIVE + "");
                  break;
          }
        }
    }

    @Override
    public void handleMsg(Message msg) {
        switch (msg.what){
            case TAG_SEARCH_DETAIL:
                String json = msg.getData().getString(Constant.JSON_DATA);
                if(TextUtils.equals(json, getString(R.string.try_agin))){
                    mWaveSwipeRefreshLayout.setRefreshing(false);
                    imageMessage.setOnClickListener(null);
                    if(dialog != null){
                        dialog.dismiss();
                    }
                    return;
                }
                data = JosnUtil.gson.fromJson(json, type);
                if(TextUtils.equals(data.getResult(), "0")){
                    mWaveSwipeRefreshLayout.setRefreshing(false);
                    imageMessage.setOnClickListener(null);
                    break;
                }
                scrollView.setVisibility(View.VISIBLE);
                if(TextUtils.equals(data.getDetail().getStatusvalue(), "-1")){
                    progress.setBackgroundResource(R.mipmap.dark_red_bg);
                } else if(TextUtils.equals(data.getDetail().getStatusvalue(), "200")){
                    progress.setBackgroundResource(R.drawable.green_bg);
                    line1.setVisibility(View.VISIBLE);
                    findViewById(R.id.linesssss).setVisibility(View.VISIBLE);
//                    if(!TextUtils.isEmpty(data.getDetail().getStar())){
                    final int size = data.getDetail().getStar();
                        if(size > 0){
                            for (int i = 0; i < size; i++) {
                                imageArray[i].setBackgroundResource(R.mipmap.star_selected);
                            }
                        }

//                    }
                    line1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(size == 0){
                                startActivityForResult(new Intent(SearchDetailActivity.this, ContextMenu.class).putExtra("type", 0x7788), REQUEST_CODE_CONTEXT_MENU);

                            } else {
                                MethodUtils.showToast(SearchDetailActivity.this, "您已经评价过了，不能再次评价!", Toast.LENGTH_SHORT);
                            }

                        }
                    });

                } else {
                    progress.setBackgroundResource(R.mipmap.dark_blue_bg);
                }
                progress.setText(data.getDetail().getStatus());
                yewuNumber.setText(data.getDetail().getBusinessid());
                hetongNumber.setText(data.getDetail().getContractid());
                writeNumber.setText(MethodUtils.returnTime(data.getDetail().getCreatedate()));
                writePeople.setText(data.getSaledetail().getDisplayname());
                time.setText(MethodUtils.returnTime(data.getDetail().getCreatedate()));
                if(data.getSaledetail() != null){
                    source.setText(data.getSaledetail().getChannelname());
                }
                imageMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       Intent intent = new Intent(SearchDetailActivity.this, ChatActivity.class);
                        intent.putExtra("userId",data.getSaledetail().getImid());
                        intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
                        intent.putExtra("name", data.getSaledetail().getDisplayname());
                        startActivity(intent);
                    }
                });
                mWaveSwipeRefreshLayout.setRefreshing(false);
                break;
            case TAG_STAR:
                String jsons = msg.getData().getString(Constant.JSON_DATA);
                if(TextUtils.equals(jsons, getString(R.string.try_agin))){
                    mWaveSwipeRefreshLayout.setRefreshing(false);
                    imageMessage.setOnClickListener(null);
                    if(dialog != null){
                        dialog.dismiss();
                    }
                    return;
                }
                ResultSuccessData successData = JosnUtil.gson.fromJson(jsons, new TypeToken<ResultSuccessData>(){}.getType());
                if(TextUtils.equals(successData.getResult(), "1")){
                    askData();
                    mWaveSwipeRefreshLayout.setRefreshing(true);
                }
                if(dialog != null){
                    dialog.dismiss();
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if(!mWaveSwipeRefreshLayout.isRefreshing() && data == null){
//            mWaveSwipeRefreshLayout.setRefreshing(true);
//        }
    }

    @Override
    public void onRefresh() {
        askData();
    }

    @Override
    protected void onPause() {
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

    private void askData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("uid", MyApplication.getInstance().getUserInfor().getUserdetail().getUid());
        map.put("token",MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        getData(IApi.NETWORK_METHOD_POST, TAG_SEARCH_DETAIL, IApi.URL_SEARCH_DETAIL + bid, map);
    }

    private void askStarData(String star) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("uid", MyApplication.getInstance().getUserInfor().getUserdetail().getUid());
        map.put("token",MyApplication.getInstance().getUserInfor().getUserdetail().getToken());
        map.put("star",star);

        getData(IApi.NETWORK_METHOD_POST, TAG_STAR, IApi.URL_SEARCH_DETAIL + bid , map);
        if(dialog != null){
            dialog.show();
        }
    }

}
