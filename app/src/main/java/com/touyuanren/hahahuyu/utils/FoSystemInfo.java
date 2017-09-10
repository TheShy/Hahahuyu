package com.touyuanren.hahahuyu.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.touyuanren.hahahuyu.bean.PackageMod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jing on 2016/5/31.
 * 获取手机系统信息;
 */
public class FoSystemInfo {

    public static Map getSystermInfo() {
        //手机型号
        String phoneModel = android.os.Build.MODEL;
        //sdk版本号
        String phoneVersionSdk = android.os.Build.VERSION.SDK;
        //系统版本
        String phoneVersionRelease = android.os.Build.VERSION.RELEASE;
        Map mMap = new HashMap();
        mMap.put("phone_model", phoneModel);
        mMap.put("phone_version_sdk", phoneVersionSdk);
        mMap.put("phone_version_release", phoneVersionRelease);
        return mMap;
    }

    //获取手机应用列表：自带和安装
    public static  List<PackageMod>  initData(Context context, List<PackageInfo> appList) {
        // 获取图片、应用名、包名
        PackageManager pManager = context.getPackageManager();
        List<PackageMod> datas = new ArrayList<PackageMod>();
        for (int i = 0; i < appList.size(); i++) {
            PackageInfo pinfo = appList.get(i);
            PackageMod shareItem = new PackageMod();
//            // 设置图片
//            shareItem.icon = pManager.getApplicationIcon(pinfo.applicationInfo);
            // 设置应用程序名字
            shareItem.appName = pManager.getApplicationLabel(
                    pinfo.applicationInfo).toString();
            // 设置应用程序的包名
            shareItem.packageName = pinfo.applicationInfo.packageName;
            datas.add(shareItem);
        }
        return  datas;
    }

    //获取安装的应用
    public static List<PackageInfo> getInstallApps(Context context) {

        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        PackageManager pManager = context.getPackageManager();
        // 获取手机内所有应用
        List<PackageInfo> packlist = pManager.getInstalledPackages(0);

//        List<ApplicationInfo> applicationInfos = pManager.getInstalledApplications(0);

        for (int i = 0; i < packlist.size(); i++) {
            PackageInfo pak = (PackageInfo) packlist.get(i);
            // if()里的值如果<=0则为自己装的程序，否则为系统工程自带
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                // 添加自己已经安装的应用程序
                apps.add(pak);
            }
            //添加所有的应用到列表中
//            apps.add(pak);
        }
        return apps;
    }

    //获取系统自带的应用
    public static List<PackageInfo> getBuiltInApps(Context context) {

        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        PackageManager pManager = context.getPackageManager();
        // 获取手机内所有应用
        List<PackageInfo> packlist = pManager.getInstalledPackages(0);
//        List<ApplicationInfo> applicationInfos = pManager.getInstalledApplications(0);
        for (int i = 0; i < packlist.size(); i++) {
            PackageInfo pak = (PackageInfo) packlist.get(i);
            // if()里的值如果<=0则为自己装的程序，否则为系统工程自带
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) > 0) {
                // 添加自己已经安装的应用程序
                apps.add(pak);
            }
            //添加所有的应用到列表中
//            apps.add(pak);
        }
        return apps;
    }
}
