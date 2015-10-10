package com.yingke.shengtai.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yingke.shengtai.moudle.GuideListData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.view.DetailMenuView;
import com.yingke.shengtai.view.TitleView;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by yanyiheng on 15-8-16.
 */
public class SaleOnlineFragment extends Fragment implements WaveSwipeRefreshLayout.OnRefreshListener{
    public static final String TAG = SaleOnlineFragment.class.getSimpleName();
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    private View parentView;
    private TitleView titleView;
    private ListView listView;

    private ArrayList<GuideListData> list;
    private DetailMenuView menu;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(parentView != null){
            if(parentView.getParent() != null){
                ((ViewGroup)parentView.getParent()).removeAllViews();
            }
            return parentView;
        }
        parentView = inflater.inflate(R.layout.fragment_guide, container, false);
        initUi();
        return parentView;
    }

    private void initUi() {
        titleView = (TitleView)parentView.findViewById(R.id.fragment_title);
        titleView.setTitleView(R.string.sale_one);
        titleView.getImageBack().setVisibility(View.GONE);
        titleView.getImagePeople().setVisibility(View.VISIBLE);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) parentView.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView)parentView.findViewById(R.id.fragment_listView);
        menu = new DetailMenuView(getActivity());
        titleView.getImagePeople().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(menu != null){
                    if(menu.isShowing()){
                        menu.dismiss();
                    } else {
                        menu.show(titleView);
                    }
                }

            }
        });

    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 更新が終了したらインジケータ非表示
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
}
