package com.yingke.shengtai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.MethodUtils;

/**
 * Created by yanyiheng on 15-8-14.
 */
public class LaunchActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGHT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去标题
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_launch);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = null;
                if(TextUtils.isEmpty(MethodUtils.getString(Constant.SHAREDREFERENCE_CONFIG_USER, Constant.SHAREDREFERENCE_CONFIG_USER))){
                    intent = new Intent(LaunchActivity.this, LoginActivity.class);
                } else {
                    if(TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "1") || TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "2") || TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "3") || TextUtils.equals(MyApplication.getInstance().getUserInfor().getUserdetail().getUsertype(), "0")){
                        intent = new Intent(LaunchActivity.this, CustomerMainActivity.class);
                    } else {
                        intent = new Intent(LaunchActivity.this, SaleMainActivity.class);
                    }
                }
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                LaunchActivity.this.finish();

            }

        }, SPLASH_DISPLAY_LENGHT);
    }
}
