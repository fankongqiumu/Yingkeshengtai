package com.yingke.shengtai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yingke.shengtai.activity.GuideListDetailActivity2;
import com.yingke.shengtai.moudle.GuideListData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;

import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-8-15.
 */
public class GuideDetailAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<GuideListData> list;
    private ListView listView;

    public GuideDetailAdapter(Context context, ArrayList<GuideListData> list, ListView listView){
        this.mContext = context;
        this.list = list;
        this.listView = listView;
        initListener();
    }

    public void setData(ArrayList<GuideListData> list){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.guide_detail_list,viewGroup,false);
            vh.title = (TextView)view.findViewById(R.id.list_text);
            view.setTag(vh);
        } else {
            vh = (ViewHolder)view.getTag();
        }
        final GuideListData data = list.get(i);
        vh.title.setText(data.getTitle());
        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    return;
                }
                GuideListData data = list.get(i -1);
                Intent intent = new Intent(mContext, GuideListDetailActivity2.class);
                intent.putExtra(Constant.DATA_GUIDE_TITLE,data.getTitle());
                intent.putExtra(Constant.DATA_GUIDE_ID,data.getCurrentId());
                mContext.startActivity(intent);
            }
        });
    }

    static class ViewHolder{
        TextView title;
    }
}
