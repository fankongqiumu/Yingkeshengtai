package com.yingke.shengtai.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yingke.shengtai.moudle.SaleSearchItemData.GainlistEntity;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.MethodUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-9-9.
 */
public class SaleSearchAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<GainlistEntity> list;
    private ListView listView;

    public SaleSearchAdapter(Context context, ArrayList<GainlistEntity> list, ListView listView){
        this.mContext = context;
        this.list = list;
        this.listView = listView;
        initListener();
    }

    public void setData(ArrayList<GainlistEntity> list){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.sale_search_item,viewGroup,false);
            vh.text1 = (TextView)view.findViewById(R.id.text1);
            vh.time = (TextView)view.findViewById(R.id.time);
            vh.text2 = (TextView)view.findViewById(R.id.text2);
            view.setTag(vh);
        } else {
            vh = (ViewHolder)view.getTag();
        }
        final GainlistEntity data = list.get(i);
        if(!TextUtils.isEmpty(data.getAmount())){
            vh.text1.setText("业绩总额：" + "RMB " + new DecimalFormat("0.00").format(Double.valueOf(data.getAmount())));
        }
        vh.time.setText(MethodUtils.returnTime2(data.getDate()));
        vh.text2.setText("满意度：" + data.getCount());
        return view;
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GainlistEntity data = list.get(i );
//                Intent intent = new Intent(mContext, SaleCustomerDetailActivity.class);
//                intent.putExtra(Constant.DATA_GUIDE_TWO, data.getUid());
//                intent.putExtra(Constant.DATA_GUIDE_TITLE, data.getName());
//                mContext.startActivity(intent);
            }
        });
    }

    static class ViewHolder{
        TextView text1, text2, time;
    }
}
