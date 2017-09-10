package com.touyuanren.hahahuyu.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * application
 */
public class MyApplication extends Application {
    /**
     * 版本下载
     */
    private boolean isDownload;

    public boolean isDownload() {
        return isDownload;
    }

    public void setIsDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }

    //获取屏幕的高，宽
    public static float screenHeight, screenWidth;
    //    private SQLHelper sqlHelper;
    private static Handler mainHandler;//全局的主线程的handler
    private List<Activity> activities = new ArrayList<Activity>();
    /**
     * bajiebaoapp对象
     */
    private static MyApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        instance = this;
        //在主线程中new的handler就是主线程的handler
        mainHandler = new Handler();//初始化Handler
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
    }
//
//    /**
//     * 获取数据库Helper
//     */
//    public SQLHelper getSQLHelper() {
//        if (sqlHelper == null)
//            sqlHelper = new SQLHelper(instance);
//        return sqlHelper;
//    }

    /**
     * 获得实例
     *
     * @return
     */
    public static MyApplication getInstance() {
        return instance;
    }


    /**
     * 把每个打开的activity放入activity列表中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 应用退出，结束所有的activity
     */
    public void exit() {
        try {
            for (Activity activity : activities) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * 获取bajiebaoapp的context对象
     */
    public static Context getContext() {
        return instance.getApplicationContext();
    }

    /**
     * 获取全局的主线程的handler对象
     *
     * @return
     */
    public static Handler getMainHandler() {
        return mainHandler;
    }
}
