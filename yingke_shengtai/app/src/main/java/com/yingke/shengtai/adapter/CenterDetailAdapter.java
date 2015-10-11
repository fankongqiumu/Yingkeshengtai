package com.yingke.shengtai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.moudle.CenterDetailData.Medialist;
import com.yingke.shengtai.R;
import com.yingke.shengtai.view.FrescoImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 15-9-27.
 */
public class CenterDetailAdapter extends BaseAdapter {
    private final int VIEW_TYPE = 2;
    private final int TYPE_1 = 0; //STRING
    private final int TYPE_2 = 1; //IAMGE
    private Context mContext;
    private ArrayList<String> listString;
    private Map<String, Medialist> map;
    private boolean isShouSuo = false; //true is default shousuo
    private boolean isFrom = false; //true is from requid shousuo class

    public CenterDetailAdapter(Context context, ArrayList<String> listString, ArrayList<Medialist> medialists) {
        this.mContext = context;
        this.listString = listString;
        if(medialists == null || medialists.size() ==0){
         return;
        }
        map = new HashMap<String, Medialist>();
        for (int i = 0; i< medialists.size(); i++){
            Medialist medialist = medialists.get(i);
            map.put(medialist.getId(), medialist);
        }

    }

    public CenterDetailAdapter(Context context, ArrayList<String> listString, ArrayList<Medialist> medialists, boolean isShouSuo, boolean isFrom) {
        this.mContext = context;
        this.listString = listString;
        this.isShouSuo = isShouSuo;
        this.isFrom = isFrom;
        if(medialists == null || medialists.size() ==0){
            return;
        }
        map = new HashMap<String, Medialist>();
        for (int i = 0; i< medialists.size(); i++){
            Medialist medialist = medialists.get(i);
            map.put(medialist.getId(), medialist);
        }

    }

    public void setData(ArrayList<String> listString, ArrayList<Medialist> medialists){
        this.listString = listString;
        if(medialists == null || medialists.size() ==0){
            return;
        }
        map = new HashMap<String, Medialist>();
        for (int i = 0; i< medialists.size(); i++){
            Medialist medialist = medialists.get(i);
            map.put(medialist.getId(), medialist);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listString == null ? 0 : listString.size();
    }

    @Override
    public Object getItem(int position) {
       return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 vh1 = null;
        ViewHolder2 vh2 = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case TYPE_1:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_txt, null);
                    vh1 = new ViewHolder1();
                    vh1.textView = (TextView) convertView.findViewById(R.id.txt);
                    convertView.setTag(vh1);
                    break;

                case TYPE_2:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_image, null);
                    vh2 = new ViewHolder2();
                    vh2.imageView = (FrescoImageView) convertView.findViewById(R.id.item_image_view);
//                    vh2.imageView.setFrescoImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
                    convertView.setTag(vh2);
                    break;
            }
        } else {
            switch (type) {
                case TYPE_1:
                    vh1 = (ViewHolder1) convertView.getTag();
                    break;

                case TYPE_2:
                    vh2 = (ViewHolder2) convertView.getTag();
                    break;
            }
        }
        switch (type) {
            case TYPE_1:
                vh1.textView.setText(listString.get(position));
                if(isFrom){
                    if(isShouSuo){
                        if(position == 0){
                            vh1.textView.setMaxLines(4);
                            vh1.textView.requestLayout();
                            vh1.textView.setVisibility(View.VISIBLE);
                        } else {
                            vh1.textView.setVisibility(View.GONE);
                        }
                    } else {
                        if(position == 0){
                            vh1.textView.setMaxLines(Integer.MAX_VALUE);
                            vh1.textView.requestLayout();
                        }
                        vh1.textView.setVisibility(View.VISIBLE);
                    }
                }

                break;

            case TYPE_2:
                Medialist medialist = map.get(listString.get(position));
                ViewGroup.LayoutParams params = vh2.imageView.getLayoutParams();
                if(params !=null){
                    params.height = (int)((MyApplication.getScreenWidth() - 35) *
                            (Integer.valueOf(medialist.getHeight()) * 1.0 / Integer.valueOf(medialist.getWidth())));
                }
                vh2.imageView.displayImage(medialist.getUrl());
                if(isFrom){
                    if(isShouSuo){
                        vh2.imageView.setVisibility(View.GONE);
                    } else {
                        vh2.imageView.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    @Override
    public int getItemViewType(int position) {
        if (listString.get(position).contains("img")) {
            return TYPE_2;
        } else {
            return TYPE_1;
        }
    }

    public class ViewHolder1 {
        TextView textView;
    }

    public class ViewHolder2 {
        FrescoImageView imageView;
    }

    public boolean isShouSuo() {
        return isShouSuo;
    }

    public void setIsShouSuo(boolean isShouSuo) {
        this.isShouSuo = isShouSuo;
    }
}
