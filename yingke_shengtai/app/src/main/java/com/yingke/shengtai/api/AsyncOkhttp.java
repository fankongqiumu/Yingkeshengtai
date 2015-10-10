package com.yingke.shengtai.api;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okio.BufferedSink;


/**
 * Created by yihengyan on 2015/5/21.
 */
public class AsyncOkhttp {

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

    public static void reques(int requestMethord, final int tag, final String url,final RequestListener re, final Map<String, String> map) {
        if(IApi.NETWORK_METHOD_GET == requestMethord){
            getRequest(tag, url, re);
        } else {
            postRequest(tag, url, re, map);
        }
    }

    /*this is not async's methord
    Response response = client.newCall(request).execute();
    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
     */

    /**
     * async get ask methord
     * @param tag
     * @param url
     * @param re
     */
    public static void getRequest(final int tag, final String url, final RequestListener re) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        RequestManager.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                re.onException(tag, e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    re.onCompelete(tag, response.body().string());
                }
            }
        });
    }

    /**
     * async post ask methord
     * @param tag
     * @param url
     * @param re
     * @param map
     */
    public static void postRequest(final int tag, final String url, final RequestListener re , final Map<String, String> map) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for(Map.Entry<String, String> entry : map.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .addHeader("Content-Length", "length")
                .post(formBody)
                .build();
        RequestManager.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                re.onException(tag, e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    re.onCompelete(tag, response.body().string());
                }

            }
        });
    }

    /**
     * async post send file or string methord
     * @param tag
     * @param url
     * @param re
     * @param file
     */
    public static void postSendString(final int tag, final String url, final RequestListener re, File file){
    	Request request = new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file)).build();
        RequestManager.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    re.onCompelete(tag, response.body().toString());
                }
            }
        });
    }

    /**
     * async post send stream methord
     * @param tag
     * @param url
     * @param re
     * @param body
     */
    public static void postStream(final int tag, final String url, final RequestListener re, final String body){
        RequestBody  requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_MARKDOWN;
            }

            @Override
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                bufferedSink.writeUtf8(body);
            }
        };
        Request request = new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE_MARKDOWN, body)).build();
        RequestManager.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(response.isSuccessful()){
                    re.onCompelete(tag, response.body().toString());
                }
            }
        });
    }
}
