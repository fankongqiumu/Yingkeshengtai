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

import com.yingke.shengtai.activity.BussinessListFragmentDetailActivity;
import com.yingke.shengtai.moudle.BussinessListData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.MethodUtils;

import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-9-13. 业务列表适配器
 */
public class BussinessListFragmentAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<BussinessListData.BusinesslistEntity> list;
    private ListView listView;
    private boolean bol;

    public BussinessListFragmentAdapter(Context context, ArrayList<BussinessListData.BusinesslistEntity> list, ListView listView){
        this.mContext = context;
        this.list = list;
        this.listView = listView;
        initListener();
    }

    public void setSource(boolean bol){
        this.bol = bol;
    }

    public void setData(ArrayList<BussinessListData.BusinesslistEntity> list){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_bussinesslist_item,viewGroup,false);
            vh.title = (TextView)view.findViewById(R.id.title);
            vh.time = (TextView)view.findViewById(R.id.time);
            vh.progress = (TextView)view.findViewById(R.id.item_text_progress);
            vh.number1 = (TextView)view.findViewById(R.id.number1);
            vh.number2 = (TextView)view.findViewById(R.id.number2);
            vh.name = (TextView)view.findViewById(R.id.name);
            view.setTag(vh);
        } else {
            vh = (ViewHolder)view.getTag();
        }
        final BussinessListData.BusinesslistEntity data = list.get(i);
        vh.time.setText(mContext.getResources().getString(R.string.qianding_time) + MethodUtils.returnTime(data.getCreatedate()));
        vh.title.setText(data.getTitle());
        vh.progress.setText(data.getStatus());
        vh.number1.setText(mContext.getResources().getString(R.string.business_number) + ":" + data.getBusinessid());
        vh.number2.setText(mContext.getResources().getString(R.string.hetong_number) + ":" + data.getContractid());
        vh.name.setText(mContext.getResources().getString(R.string.customer) + data.getCusname());
//
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
                    BussinessListData.BusinesslistEntity data = list.get(i);
                    Intent intent = new Intent(mContext, BussinessListFragmentDetailActivity.class);
                    intent.putExtra(Constant.DATA_GUIDE_CENTER, data);
                    mContext.startActivity(intent);
            }
        });
    }

    static class ViewHolder{
        TextView title;
        TextView progress;
        TextView time;
        TextView number1;
        TextView number2;
        TextView name;
    }
}
