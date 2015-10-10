package com.yingke.shengtai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yingke.shengtai.moudle.CenterListData;
import com.yingke.shengtai.R;

import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-8-24.
 */
public class AskHistoryAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<CenterListData> list;
    private ListView listView;

    public AskHistoryAdapter(Context context, ArrayList<CenterListData> list, ListView listView){
        this.mContext = context;
        this.list = list;
        this.listView = listView;
        initListener();
    }

    public void setData(ArrayList<CenterListData> list){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.sale_askhistory_item,viewGroup,false);
            vh.title = (TextView)view.findViewById(R.id.title);
            vh.time = (TextView)view.findViewById(R.id.time);
            vh.content = (TextView)view.findViewById(R.id.content);
            view.setTag(vh);
        } else {
            vh = (ViewHolder)view.getTag();
        }
        final CenterListData data = list.get(i);
//        vh.time.setText(data.getTime());
//        vh.title.setText(data.getTitle());
//        vh.content.setText(data.getContent());
        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                CenterListData data = list.get(i -1);
//                Intent intent = new Intent(mContext, WebViewActivity.class);
//                intent.putExtra(Constant.DATA_GUIDE_CENTER, data);
//                mContext.startActivity(intent);
            }
        });
    }

    static class ViewHolder{
        TextView title;
        TextView content;
        TextView time;
    }
}
