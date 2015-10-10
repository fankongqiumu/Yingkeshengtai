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

import com.yingke.shengtai.activity.SearchDetailActivity;
import com.yingke.shengtai.moudle.SearchItemData.BusinesslistEntity;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.MethodUtils;

import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-8-16.
 */
public class SearchListAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<BusinesslistEntity> list;
    private ListView listView;
    private boolean isSale;

    public SearchListAdapter(Context context, ArrayList<BusinesslistEntity> list, ListView listView){
        this.mContext = context;
        this.list = list;
        this.listView = listView;
        this.isSale = isSale;
        initListener();
    }

    public void setData(ArrayList<BusinesslistEntity> list){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_search_item,viewGroup,false);
            vh.title = (TextView)view.findViewById(R.id.item_text_title);
            vh.time = (TextView)view.findViewById(R.id.item_text_time);
            vh.progress = (TextView)view.findViewById(R.id.item_text_progress);
            view.setTag(vh);
        } else {
            vh = (ViewHolder)view.getTag();
        }
        final BusinesslistEntity data = list.get(i);
        vh.time.setText(MethodUtils.returnTime(data.getCreatedate()));
        vh.title.setText(data.getTitle());
        vh.progress.setText(data.getStatus());
        if(TextUtils.equals(data.getStatusvalue(), "-1")){
            vh.progress.setBackgroundResource(R.mipmap.dark_red_bg);
        } else if(TextUtils.equals(data.getStatusvalue(), "200")){
            vh.progress.setBackgroundResource(R.drawable.green_bg);
        } else {
            vh.progress.setBackgroundResource(R.mipmap.dark_blue_bg);
        }

        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BusinesslistEntity data = list.get(i);
                Intent intent = new Intent(mContext, SearchDetailActivity.class);
                intent.putExtra(Constant.DATA_GUIDE_ID, data.getUid());
                intent.putExtra(Constant.DATA_GUIDE_TITLE, data.getTitle());
                intent.putExtra("ID", data.getBid());
                mContext.startActivity(intent);
            }
        });
    }

    static class ViewHolder{
        TextView title;
        TextView progress;
        TextView time;
    }
}
