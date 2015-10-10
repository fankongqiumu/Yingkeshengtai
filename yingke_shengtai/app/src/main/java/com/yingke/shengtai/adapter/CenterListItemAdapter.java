package com.yingke.shengtai.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yingke.shengtai.activity.CenterDetailActivity;
import com.yingke.shengtai.moudle.CenterListData.NewslistEntity;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.view.FrescoImageView;

import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-8-16.
 */
public class CenterListItemAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<NewslistEntity> list;
    private ListView listView;

    public CenterListItemAdapter(Context context, ArrayList<NewslistEntity> list, ListView listView){
        this.mContext = context;
        this.list = list;
        this.listView = listView;
        initListener();
    }

    public void setData(ArrayList<NewslistEntity> list){
        this.list = list;
        notifyDataSetChanged();;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.center_list_item,viewGroup,false);
            vh.title = (TextView)view.findViewById(R.id.center_item_title);
            vh.time = (TextView)view.findViewById(R.id.center_item_time);
            vh.content = (TextView)view.findViewById(R.id.center_item_content);
            vh.iamge = (FrescoImageView) view.findViewById(R.id.center_item_image);
            view.setTag(vh);
        } else {
            vh = (ViewHolder)view.getTag();
        }
        final NewslistEntity data = list.get(i);
        vh.time.setText(data.getDate());
        vh.title.setText(data.getTitle());
        vh.content.setText(data.getBrief());
        if(TextUtils.isEmpty(data.getImageUrl())){
            vh.iamge.setVisibility(View.GONE);
        } else {
            vh.iamge.setVisibility(View.VISIBLE);
            vh.iamge.displayAnimatedImage(data.getImageUrl());
        }
        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewslistEntity data = list.get(i);
                Intent intent = new Intent(mContext, CenterDetailActivity.class);
                intent.putExtra(Constant.DATA_GUIDE_ID, data.getId());
                mContext.startActivity(intent);
            }
        });
    }

    static class ViewHolder{
        TextView title;
        TextView content;
        TextView time;
        FrescoImageView iamge;
    }
}

