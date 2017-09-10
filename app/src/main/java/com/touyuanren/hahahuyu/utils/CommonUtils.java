package com.touyuanren.hahahuyu.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

/**
 * dp 和  px之间的转换
 * px 和 sp之间转化
 *
 * @author LK
 */
public class CommonUtils {

    /*
     * dp->px
     */
    public static int dp2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * 获取缩放的比例
     *
     * @param context
     * @param path    图片的绝对路径
     * @param dp      缩放后的宽度
     * @return 缩放的比例
     */
    public static int getScale(Context context, String path, int dp) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return options.outWidth / CommonUtils.dp2px(context, dp);
    }

}
