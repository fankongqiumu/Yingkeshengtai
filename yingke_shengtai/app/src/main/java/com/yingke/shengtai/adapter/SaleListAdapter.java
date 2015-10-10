package com.yingke.shengtai.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yingke.shengtai.activity.ChatActivity;
import com.yingke.shengtai.activity.SalesListActivity.SalesData.SaleslistEntity;
import com.yingke.shengtai.R;

import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-9-24.
 */
public class SaleListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<SaleslistEntity> list;
    private ListView listView;

    public SaleListAdapter(Context context, ArrayList<SaleslistEntity> list, ListView listView){
        this.mContext = context;
        this.list = list;
        this.listView = listView;
        initListener();
    }

    public void setData(ArrayList<SaleslistEntity> list){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_sale_list,viewGroup,false);
            vh.title = (TextView)view.findViewById(R.id.name);
            vh.image = (ImageView)view.findViewById(R.id.right);
            view.setTag(vh);
        } else {
            vh = (ViewHolder)view.getTag();
        }
        final SaleslistEntity data = list.get(i);
        vh.title.setText(data.getDisplayname());
        if(TextUtils.equals(data.getStatus(), "offline")){
            vh.image.setBackgroundResource(R.mipmap.a2);
        } else {
            vh.image.setBackgroundResource(R.mipmap.a1);
        }

        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SaleslistEntity data = list.get(i);
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("userId", data.getImid());
                intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
                intent.putExtra("name", data.getDisplayname());
                mContext.startActivity(intent);
            }
        });
    }

    static class ViewHolder{
        TextView title;
        ImageView image;
    }
}
