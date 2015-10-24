package com.yingke.shengtai.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yingke.shengtai.R;
import com.yingke.shengtai.adapter.WelcomViewPagerAdapter;
import com.yingke.shengtai.utils.MethodUtils;

import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-10-14.
 */
public class WelcomActivity extends Activity {
    private ViewPager viewPager;
    private ArrayList<View> views;
    private View view1, view2, view3;
    private LayoutInflater inflate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去标题
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        if(!TextUtils.isEmpty(MethodUtils.getString("welcom", "welcom"))){
            Intent intent = new Intent(this, LaunchActivity.class);
            startActivity(intent);
            this.finish();
        }
        setContentView(R.layout.activity_welcom);
        MethodUtils.setString("welcom", "welcom", "welcom");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        views = new ArrayList<View>();
        inflate =LayoutInflater.from(this);
        view1 = inflate.inflate(R.layout.aa, null);
        view2 = inflate.inflate(R.layout.bb, null);
        view3 = inflate.inflate(R.layout.cc, null);

        views.add(view1);
        views.add(view2);
        views.add(view3);
        WelcomViewPagerAdapter adapter = new WelcomViewPagerAdapter(views);
        viewPager.setAdapter(adapter);

        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomActivity.this, LaunchActivity.class);
                startActivity(intent);
                WelcomActivity.this.finish();
            }
        });
    }
}
