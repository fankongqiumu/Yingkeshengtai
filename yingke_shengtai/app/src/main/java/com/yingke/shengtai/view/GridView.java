package com.yingke.shengtai.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yingke.shengtai.activity.GuideListDetailActivity;
import com.yingke.shengtai.activity.GuideListDetailActivity2;
import com.yingke.shengtai.moudle.GuideListData;
import com.yingke.shengtai.R;
import com.yingke.shengtai.utils.Constant;
import com.yingke.shengtai.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by yanyiheng on 15-9-21.
 */
public class GridView extends LinearLayout {

    private ArrayList<GuideListData> arrayList;
    private Context context;
    private boolean source;

    public GridView(Context context) {
        this(context, null);
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        this.context = context;

    }

    public void CreateView(Object obj){
        if(obj instanceof ArrayList){
            int size = ((ArrayList) obj).size();
            if(size <= 0){
              return;
            }
            this.removeAllViews();
            int addCount = 0;
            int rowCount = 0;
            LinearLayout rowLayout = null;
            LayoutParams itemParams = null;
            for ( int i = 0; i < size; i++) {
                if(addCount == 0 || addCount >= 2) {
                    addCount = 0;
                    rowLayout = addRowLayout();
                    rowCount++;
                }
                addCount++;
                itemParams = new LayoutParams(0, UiUtil.dip2px(85), 1);
                LinearLayout rel = new LinearLayout(context);
//                rel.setVerticalGravity(Gravity.CENTER);
                rel.setOrientation(LinearLayout.VERTICAL);
                rel.setGravity(Gravity.CENTER);
                rel.setBackgroundResource(R.mipmap.guide_bg);
                rowLayout.addView(rel, itemParams);

                TextView text1 = new TextView(context);
                text1.setTextColor(0xFFFFFFFF);
                text1.setTextSize(16);
                text1.setGravity(Gravity.CENTER);

                TextView text2 = new TextView(context);
                text2.setTextColor(0x9EFFFFFF);
                text2.setTextSize(13);

                rel.addView(text1, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                rel.addView(text2, params);

               Object object = ((ArrayList) obj).get(i);
                final int j = i;
                if(object instanceof GuideListData){
                   final GuideListData data= ((GuideListData)object);
                    if(source){
                        text1.setText(data.getSubchannelinfo().get(j).getShorttitle());
                        text2.setText(data.getSubchannelinfo().get(j).getBrief());
                    } else {
                        text1.setText(data.getTitle());
                        text2.setText(data.getBrief());
                    }

                    rel.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(source){
                                Intent intent = new Intent(context, GuideListDetailActivity2.class);
                                intent.putExtra(Constant.DATA_GUIDE_TITLE,data.getSubchannelinfo().get(j).getTitle());
                                intent.putExtra(Constant.DATA_GUIDE_ID,data.getSubchannelinfo().get(j).getId());
                                context.startActivity(intent);
                            } else {
                                Intent intent = new Intent(context, GuideListDetailActivity.class);
                                intent.putExtra(Constant.DATA_GUIDE_TITLE,data.getTitle());
                                intent.putExtra(Constant.DATA_GUIDE_ID,data.getCurrentId());
                                context.startActivity(intent);
                            }

                        }
                    });
                } else if(object instanceof GuideListData.Subchannelinfo){
                    final GuideListData.Subchannelinfo data= ((GuideListData.Subchannelinfo)object);
                    if(source){
                        text1.setText(data.getShorttitle());
                        text2.setText(data.getBrief());
                    } else {
                        text1.setText(data.getTitle());
                        text2.setText(data.getBrief());
                    }

                    rel.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(source){
                                Intent intent = new Intent(context, GuideListDetailActivity2.class);
                                intent.putExtra(Constant.DATA_GUIDE_TITLE,data.getTitle());
                                intent.putExtra(Constant.DATA_GUIDE_ID,data.getId());
                                context.startActivity(intent);
                            }

                        }
                    });
                }
                itemParams.topMargin = verticalSpacing();
                if(addCount == 1) {
                    itemParams.rightMargin = horizontalSpacing();
                }
                if(i == size - 1 && size % 2 == 1){
                    rowLayout.addView(new View(context), new LayoutParams(0, UiUtil.dip2px(85), 1));
                }

            }
        }

    }

    public void setSouce(boolean source){
        this.source = source;
    }

    protected LinearLayout addRowLayout() {
        LinearLayout result = new LinearLayout(context);
        result.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.leftMargin = UiUtil.dip2px(12);
        params.rightMargin = UiUtil.dip2px(12);

        this.addView(result, params);
        return result;
    }

    public int horizontalSpacing() {
        return UiUtil.dip2px(12);
    }

    public int verticalSpacing() {
        return UiUtil.dip2px(12);
    }

}
