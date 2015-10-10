package com.yingke.shengtai.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;

import com.yingke.shengtai.MyApplication;

/**
 * Created by yanyiheng on 15-8-30.
 */
public class MethodUtils {
    private final static int MODE = Context.MODE_PRIVATE;

    private final static String DEFAULT_STRING = "";

    public static boolean isHasNet(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            // 如果仅仅是用来判断网络连接　　　　　　
            // 则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }

        return false;

    }
    public static void showToast(Context context, String string, int lenth) {
        Toast.makeText(context, string, lenth).show();
    }

    public static String getString(String name, String key) {
        Context context = MyApplication.getContextFromApplication();
        SharedPreferences mSharedPreferences = context.getSharedPreferences(name, MODE);
        return mSharedPreferences.getString(key, DEFAULT_STRING);
    }

    public static boolean setString(String name, String key, String value) {
        Context context = MyApplication.getContextFromApplication();
        SharedPreferences mSharedPreferences = context.getSharedPreferences(name, MODE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(key, value);
        return mEditor.commit();
    }

    public static String returnTime(String times){
        if(TextUtils.isEmpty(times)){
            return "";
        }
        String str = times.toString().substring(0, 10);
        return str;
    }

    public static String returnTime2(String times){
        if(TextUtils.isEmpty(times)){
            return "";
        }
        String str = times.toString().substring(0, 7);
        return str;
    }

}