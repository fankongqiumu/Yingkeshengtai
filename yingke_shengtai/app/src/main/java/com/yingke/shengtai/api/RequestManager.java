package com.yingke.shengtai.api;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by yihengyan on 2015/5/21.
 */
public class RequestManager {

    public static OkHttpClient client;

    public static void init(Context context){
        client = new OkHttpClient();
    }

    public static OkHttpClient getOkHttpClient(){
        if(client != null){
            return client;
        } else {
            client = new OkHttpClient();
            return client;
//            throw  new IllegalStateException("Not initialized");
        }
    }
}