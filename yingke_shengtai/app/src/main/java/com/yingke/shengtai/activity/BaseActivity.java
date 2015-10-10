package com.yingke.shengtai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.yingke.shengtai.api.AsyncOkhttp;
import com.yingke.shengtai.api.RequestListener;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;

import java.util.Map;


/**
 * Created by yanyiheng on 15-8-30.
 */
public abstract class BaseActivity extends Activity implements RequestListener{
    public static final int ZHUCE_RECIVED_CODE = 1;
    public static final int ZHUCE_TWO = 2;
    public static final int GET_PHONE_PLACE = 3;
    public static final int LOGIN = 4;   //登录

    public static final int TAG_GUIDE_TWO = 5;
    public static final int TAG_GUIDE_THREE = 6;
    public static final int TAG_CENTER_DETAIL = 7;
    public static final int TAG_SEARCH_DETAIL = 8; //业务查询详情
    public static final int TAG_UPDATE_INFOR = 9; //更新用户信息
    public static final int TAG_UPDATE_PASSWORD = 10; //修改密码
    public static final int TAG_SOFT_TERMS = 11; //软件协议
    public static final int TAG_PHONE_ADRESS = 12; //手机号地址


    public static final int TAG_CUSSTOM_DETAIL = 13; //客户列表详情
    public static final int TAG_CUSSTOM_DETAIL_BUSSINESS = 14; //客户详情业务记录
    public static final int TAG_DETAIL_BUSSINESS = 15; //业务详细
    public static final int TAG_CUSTOMER_COMENT = 16; //客户评价
    public static final int TAG_CUSTOMER_COMENT_ADD = 17; //客户评价添加
    public static final int TAG_STATUS = 17; //状态列表
    public static final int TAG_SOURCE = 18; //来源板块类表
    public static final int TAG_ADD = 19; //客户详情也添加业务
    public static final int TAG_BUSSINESS_LIST_FRAGMENT_DETAIL = 20; //业务列表Fragment的业务详情
    public static final int TAG_ADD_BUSSINESS_LIST_FRAGMENT = 21; //业务列表添加业务
    public static final int TAG_UPDATE_BUSSINESS_LIST_FRAGMENT = 22; //业务列表修改业务
    public static final int TAG_UPDATE_BUSSINESS_CUSTOMER = 23; //客户详情修改业务
    public static final int TAG_ADD_BUSSINESS_CUSTOMER = 24; //客户详添加业务
    public static final int TAG_VIEW_BUSSINESS_CUSTOMER = 25; //业务记录查看
    public static final int TAG_ADD_BUSSINESS_WRITE = 26; //添加业务记录
    public static final int TAG_STAR = 27;
    public static final int FORGET_PASSWORD = 28;
    public static final int TAG_GUIDE_FOUR = 29;
    public static final int TAG_SALE_LIST = 30;









    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            handleMsg(msg);
        };
    };

    public abstract void handleMsg(Message msg);

    public void getData(int requestMethord, int tag, final String url, Map<String, String> map){
        AsyncOkhttp.reques(requestMethord, tag, url, this, map);
    }

    @Override
    public void onCompelete(int tag, String json) {
        Message msg = handler.obtainMessage();
        msg.what = tag;
        Bundle data = new Bundle();
        data.putString(Constant.JSON_DATA, json);
        msg.setData(data);
        handler.sendMessage(msg);
    }

    @Override
    public void onException(int tag, String json) {
        Message msg = handler.obtainMessage();
        msg.what = tag;
        Bundle data = new Bundle();
        data.putString(Constant.JSON_DATA, getString(R.string.try_agin));
        msg.setData(data);
        handler.sendMessage(msg);
    }


}