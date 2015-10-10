package com.yingke.shengtai.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.yingke.shengtai.api.AsyncOkhttp;
import com.yingke.shengtai.api.RequestListener;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;

import java.util.Map;

/**
 * Created by yanyiheng on 15-8-30.
 */
public abstract class BaseFragment extends Fragment implements RequestListener{
    public static final int TAG_GUIDLIST = 101;
    public static final int TAG_CENTERLIST = 102;
    public static final int TAG_ONLINELIST = 103;
    public static final int TAG_SearchLIST = 104;



    public static final int TAG_SALE_CUSTOMER_LIST = 121;
    public static final int TAG_SALE_BUSSINESS_LIST = 122;
    public static final int TAG_SALE_YEJI_LIST = 123;


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
