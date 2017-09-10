package com.touyuanren.hahahuyu.utils;

import android.content.Context;

/**
 * Created by Jing on 2016/2/17.
 * dp与px互相转换
 */
public class SwitchDpOrPx {


    //把dp转换成px
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    //把px转换成dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
