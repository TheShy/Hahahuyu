package com.touyuanren.hahahuyu.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.touyuanren.hahahuyu.Application.MyApplication;

/**
 * 创建一些零碎的方法
 */
public class CommonUtil {
    /**
     * 在主线程执行runnable
     *
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        MyApplication.getMainHandler().post(runnable);
    }

    /**
     * 将子View从父View中移除
     *
     * @param child
     */
    public static void removeSelfFromParent(View child) {
        if (child != null) {
            ViewParent parent = child.getParent();
            if (parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(child);//将child从父view中移除
            }
        }
    }
}
