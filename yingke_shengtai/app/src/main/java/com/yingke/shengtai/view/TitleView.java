package com.yingke.shengtai.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yingke.shengtai.R;

/**
 * Created by yanyiheng on 15-8-15.
 */
public class TitleView extends RelativeLayout implements View.OnClickListener{
    private TextView titleView;
    private ImageView  imagePeople, imageSearch, imageBack;

    private Context mContext;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.view_title, this);
        this.mContext = context;
        this.setBackgroundColor(0xFF0A2b4A);
        titleView = (TextView)findViewById(R.id.view_title_name);
        imagePeople = (ImageView)findViewById(R.id.view_title_people);
        imageBack = (ImageView)findViewById(R.id.view_title_back);
        imageBack.setOnClickListener(this);
    }

    public void setTitleView(int stringId){
        titleView.setText(stringId);
    }
    public void setTitleView(String title){
        titleView.setText(title);
    }

    public TextView getTitleView() {
        return titleView;
    }

    public ImageView getImagePeople() {
        return imagePeople;
    }

    public ImageView getImageBack() {
        return imageBack;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.view_title_back:
                ((Activity)mContext).finish();
                break;
        }
    }
}
