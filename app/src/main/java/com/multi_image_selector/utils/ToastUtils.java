package com.multi_image_selector.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by admin on 2015/11/17.
 * <p/>
 * Toast工具类
 */
public class ToastUtils {

    private static String oldMessage;

    private static Toast toast;

    private static long beforeTime = 0;
    private static long afterTime = 0;

    public static void showToast(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
            toast.show();
            beforeTime = System.currentTimeMillis();
        } else {
            afterTime = System.currentTimeMillis();
            if (message.equals(oldMessage)) {
                if (afterTime - beforeTime > Toast.LENGTH_SHORT) {
                    toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
                    toast.show();
                }
            } else {
                oldMessage = message;
                toast.setText(message);
                toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        }
        beforeTime = afterTime;
    }

    public static void showToast(Context context, @StringRes int resId) {
        showToast(context, context.getResources().getString(resId));
    }

}