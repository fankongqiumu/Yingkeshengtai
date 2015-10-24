package com.yingke.shengtai.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.yingke.shengtai.activity.ChatActivity;
import com.yingke.shengtai.activity.SalesListActivity.SalesData;
import com.yingke.shengtai.activity.SalesListActivity.SalesData.TranslistEntity;
import com.yingke.shengtai.R;
import com.yingke.shengtai.moudle.SaleslistEntity;
import com.yingke.shengtai.view.AnimatedExpandableListView;
import com.yingke.shengtai.view.AnimatedExpandableListView.AnimatedExpandableListAdapter;

import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-9-24.
 */
public class SaleListAdapter extends AnimatedExpandableListAdapter {
    private Context mContext;
    private ArrayList<TranslistEntity> list;
    private AnimatedExpandableListView listView;
    private LayoutInflater inflater;

    public SaleListAdapter(Context context, ArrayList<TranslistEntity> list, AnimatedExpandableListView listView){
        this.mContext = context;
        this.list = list;
        this.listView = listView;
        inflater = LayoutInflater.from(context);
        initListener();
    }

    public void setData(ArrayList<TranslistEntity> list){
        this.list = list;
        notifyDataSetChanged();;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        ViewHolder vh = null;
        if(view == null){
            vh = new ViewHolder();
            view = inflater.inflate(R.layout.activity_sale_list, null);
            vh.title = (TextView)view.findViewById(R.id.name);
            vh.image = (ImageView)view.findViewById(R.id.right);
            vh.left = (ImageView) view.findViewById(R.id.left);
            view.setTag(vh);
        } else {
            vh = (ViewHolder)view.getTag();
        }
        final SaleslistEntity data = list.get(groupPosition).getSaleslist().get(childPosition);
        vh.title.setText(data.getDisplayname());
        if(TextUtils.equals(data.getStatus(), "offline")){
            vh.image.setBackgroundResource(R.mipmap.a2);
        } else {
            vh.image.setBackgroundResource(R.mipmap.a1);
        }

        if(TextUtils.equals("0", data.getSex() + "")){
            vh.left.setImageResource(R.mipmap.male_yewu);
        } else {
            vh.left.setImageResource(R.mipmap.meal_yewu);
        }

        return view;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return list.get(groupPosition).getSaleslist() == null ? 0 : list.get(groupPosition).getSaleslist().size();
    }

    @Override
    public TranslistEntity getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        TranslistEntity item = getGroup(groupPosition);
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = inflater.inflate(R.layout.group_item, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.textTitle);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }

        holder.title.setText(item.getChannelname());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

    private void initListener() {
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (listView.isGroupExpanded(groupPosition)) {
                    listView.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                SaleslistEntity data = list.get(groupPosition).getSaleslist().get(childPosition);
                Intent intent = new Intent();
                intent.putExtra("data", data);
                ((Activity)mContext).setResult(Activity.RESULT_OK, intent);
                ((Activity)mContext).finish();
                return false;
            }
        });
    }

    static class ViewHolder{
        TextView title;
        ImageView image, left;
    }

    static class GroupHolder {
        TextView title;
    }
}
