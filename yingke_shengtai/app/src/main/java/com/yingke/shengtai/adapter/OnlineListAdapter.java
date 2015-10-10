package com.yingke.shengtai.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yingke.shengtai.moudle.TotalCustomerData;

import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-9-5.
 */
public class OnlineListAdapter extends BaseAdapter {

    private ArrayList<TotalCustomerData> list;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return view;
    }


}
