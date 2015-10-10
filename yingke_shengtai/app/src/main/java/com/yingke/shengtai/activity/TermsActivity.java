package com.yingke.shengtai.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.api.IApi;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.JosnUtil;
import com.yingke.shengtai.utils.MethodUtils;
import com.yingke.shengtai.view.TitleView;

import java.io.Serializable;
import java.lang.reflect.Type;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by yanyiheng on 15-9-5.
 */
public class TermsActivity extends BaseActivity implements WaveSwipeRefreshLayout.OnRefreshListener{
    private TextView text;
    private Type type;
    private TitleView titleView;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private String texts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            texts = savedInstanceState.getString("text");
        } else {
            if(getIntent() != null){
                texts = getIntent().getStringExtra("text");
            }
        }

        setContentView(R.layout.activity_terms);
        initUi();
        if(!TextUtils.isEmpty(texts)){
            titleView.setTitleView(getString(R.string.business_content));
            text.setText(texts);
            return;
        }
        askData();
        setLoadData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", texts);
    }

    private void askData() {
        getData(IApi.NETWORK_METHOD_GET, TAG_SOFT_TERMS, IApi.URL_SOFT_TERMS, null);
    }

    private void initUi() {
        text = (TextView)findViewById(R.id.text);
        type = new TypeToken<TermsData>(){}.getType();
        titleView = (TitleView)findViewById(R.id.fragment_title);
        titleView.getImageBack().setImageResource(R.mipmap.nav_close_icon);
        titleView.setTitleView(R.string.login_shuoming_three);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void handleMsg(Message msg) {
        String json = msg.getData().getString(Constant.JSON_DATA);
        if(TextUtils.equals(json, getString(R.string.try_agin))){
            setRefreFalse();
            MethodUtils.showToast(this, getString(R.string.try_agin), Toast.LENGTH_SHORT);
            return;
        }
        switch (msg.what){
            case TAG_SOFT_TERMS:
                TermsData data = JosnUtil.gson.fromJson(json, type);
                if(TextUtils.equals(data.getResult(), "0")){
                    setRefreFalse();
                    MethodUtils.showToast(this, getString(R.string.try_agin), Toast.LENGTH_SHORT);
                    break;
                }
                setRefreFalse();
                text.setText(data.getText());
                break;
        }

    }

    @Override
    public void onRefresh() {
        if(!TextUtils.isEmpty(texts)){
            return;
        }
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

    private void setLoadData(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mWaveSwipeRefreshLayout.setRefreshing(true);
            }
        }, 300);
    }

    public class TermsData implements Serializable{

        private static final long serialVersionUID = 282670216080298632L;
        /**
         * result : 1
         * message :
         * text : Terms and Conditions
         */

        private String result;
        private String message;
        private String text;

        public void setResult(String result) {
            this.result = result;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getResult() {
            return result;
        }

        public String getMessage() {
            return message;
        }

        public String getText() {
            return text;
        }
    }
}
