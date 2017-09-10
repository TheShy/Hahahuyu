package com.touyuanren.hahahuyu.utils;

import android.content.Context;
import android.widget.Toast;

import com.touyuanren.hahahuyu.Application.MyApplication;

/**
 * @author linmu
 * 封装的toast
 */
public class FoToast
{
    private static Toast toast;
    /**
     * toast
     */
    public static void toast(Context context, int resid)
    {
        Toast toast = Toast.makeText(context, resid, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * toast
     */
    public static void toast(Context context, String info)
    {
        Toast toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(String text) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }

}
