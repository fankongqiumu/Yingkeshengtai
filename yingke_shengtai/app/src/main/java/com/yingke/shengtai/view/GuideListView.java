package com.yingke.shengtai.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.yingke.shengtai.MyApplication;
import com.yingke.shengtai.activity.GuideListDetailActivity2;
import com.yingke.shengtai.moudle.AguideListData;
import com.yingke.shengtai.utils.Constant;

import java.util.List;

/**
 * Created by yanyiheng on 15-10-11.
 */
public class GuideListView extends AbsoluteLayout{

    public GuideListView(Context context) {
        super(context);
    }

    public GuideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void createView(List<AguideListData.ChannellistEntity> channellist){
        double screenBili = MyApplication.getScreenWidth()  * 1.0 / 733;
        for (final AguideListData.ChannellistEntity data: channellist){
            RelativeLayout relativeLayout = new RelativeLayout(getContext());
            relativeLayout.setGravity(Gravity.CENTER);
            relativeLayout.setBackgroundColor(0xFFFFFFFF);
            int width = (int) (data.getWidth() * screenBili);
            int height = (int) (data.getHeight() * screenBili);
            int x = (int) (data.getX() * screenBili);
            int y = (int) (data.getY() * screenBili);
            this.addView(relativeLayout, new AbsoluteLayout.LayoutParams(width, height, x, Math.abs(y)));


            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setBackgroundColor(Color.parseColor(data.getBgcolor()));
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width - 12, height - 12);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            relativeLayout.addView(linearLayout, params);
            linearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), GuideListDetailActivity2.class);
                    intent.putExtra(Constant.DATA_GUIDE_TITLE,data.getTitle());
                    intent.putExtra(Constant.DATA_GUIDE_ID,data.getId() + "");
                    getContext().startActivity(intent);
                }
            });

            FrescoImageView imageView = new FrescoImageView(getContext());
            imageView.setFrescoImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
            int widthImage = (int) (data.getIconwidth() * screenBili);
            int heightImage = (int) (data.getIconheight() * screenBili);

            LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(widthImage, heightImage);
            linearLayout.addView(imageView, paramsImage);
            imageView.displayAnimatedImage(data.getIconurl());

            LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsText.setMargins(0, 10, 0, 0);
            TextView textView = new TextView(getContext());
            textView.setTextSize(15);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextColor(0xFFFFFFFF);
            textView.setText(data.getShorttitle());
            linearLayout.addView(textView, paramsText);


        }

    }
}
