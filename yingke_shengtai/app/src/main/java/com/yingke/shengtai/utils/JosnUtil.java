package com.yingke.shengtai.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yingke.shengtai.moudle.UserInforData.UserdetailEntity;

import java.lang.reflect.Type;

/**
 * Created by yanyiheng on 15-8-30.
 */
public class JosnUtil  {
    public static Gson gson = new Gson();

    public static UserdetailEntity getWodfanUser(String userString) {
        if (TextUtils.isEmpty(userString)) {
            UserdetailEntity user = new UserdetailEntity();
            return user;
        }
        Type type = new TypeToken<UserdetailEntity>() {}.getType();
        return gson.fromJson(userString, type);
    }
}
