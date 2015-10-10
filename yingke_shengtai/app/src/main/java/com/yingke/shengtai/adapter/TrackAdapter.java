package com.yingke.shengtai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yingke.shengtai.moudle.TrckListData.TracklistEntity;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.MethodUtils;

import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-9-15.
 */
public class TrackAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<TracklistEntity> list;
    private ListView listView;

    public TrackAdapter(Context context, ArrayList<TracklistEntity> list, ListView listView){
        this.mContext = context;
        this.list = list;
        this.listView = listView;
        initListener();
    }

    public void setData(ArrayList<TracklistEntity> list){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_comment_item,viewGroup,false);
            vh.title = (TextView)view.findViewById(R.id.title);
            vh.time = (TextView)view.findViewById(R.id.time);
            vh.content = (TextView)view.findViewById(R.id.content);
            view.setTag(vh);
        } else {
            vh = (ViewHolder)view.getTag();
        }
        final TracklistEntity data = list.get(i);
        vh.time.setText(MethodUtils.returnTime(data.getCreatedate()));
        vh.title.setText(data.getSalesdisplayname());
        vh.content.setText(data.getText());
        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    static class ViewHolder{
        TextView title;
        TextView content;
        TextView time;
    }
}
