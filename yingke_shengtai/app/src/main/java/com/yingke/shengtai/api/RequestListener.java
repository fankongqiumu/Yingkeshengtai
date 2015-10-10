package com.yingke.shengtai.api;

/**
 * Created by yihengyan on 2015/5/21.
 */
public interface RequestListener {
    void onCompelete(int tag, String json);
    void onException(int tag, String json);
}
