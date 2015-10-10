package com.yingke.shengtai.utils;

import android.util.TypedValue;

import com.yingke.shengtai.MyApplication;

/**
 * Created by yanyiheng on 15-8-15.
 */
public class UiUtil {

    public static int dip2px(float dpValue) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, MyApplication.getContextFromApplication().getResources()
                .getDisplayMetrics()) + 0.5f);
    }

}
