package com.yingke.shengtai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yingke.shengtai.activity.SaleCustomerDetailActivity;
import com.yingke.shengtai.moudle.SaleCustomListData.UserlistEntity;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.MethodUtils;

import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-8-16.
 */
public class SaleCustomAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<UserlistEntity> list;
    private ListView listView;

    public SaleCustomAdapter(Context context, ArrayList<UserlistEntity> list, ListView listView){
        this.mContext = context;
        this.list = list;
        this.listView = listView;
        initListener();
    }

    public void setData(ArrayList<UserlistEntity> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return list == null?0:list.size();
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
        ViewHolder vh = null;
        if(view == null){
            vh = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_sale_custom,viewGroup,false);
            vh.name = (TextView)view.findViewById(R.id.text_name);
            vh.time = (TextView)view.findViewById(R.id.text_time);
            vh.phone = (TextView)view.findViewById(R.id.text_phone);
            vh.imageView = (ImageView)view.findViewById(R.id.image_avator);
            view.setTag(vh);
        } else {
            vh = (ViewHolder)view.getTag();
        }
        final UserlistEntity data = list.get(i);
        vh.name.setText(data.getName());
        vh.time.setText("注册时间: " + MethodUtils.returnTime(data.getRegdate()));
        vh.phone.setText(data.getMobile());
        vh.imageView.setBackgroundResource(R.drawable.mini_avatar_shadow);
        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserlistEntity data = list.get(i);
                Intent intent = new Intent(mContext, SaleCustomerDetailActivity.class);
                intent.putExtra(Constant.DATA_GUIDE_TWO, data.getUid());
                intent.putExtra(Constant.DATA_GUIDE_TITLE, data.getName());
                mContext.startActivity(intent);
            }
        });
    }

    static class ViewHolder{
        TextView name, phone, time;
        ImageView imageView;
    }
}
