package com.touyuanren.hahahuyu.bean;

/**
 * Created by Jing on 2015/12/16.
 * 手机安装的应用的应用名,包名
 */
public class PackageMod {

    public String packageName;
    public String appName;

    public PackageMod() {
        super();
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public PackageMod(String packageName, String appName) {
        this.packageName = packageName;
        this.appName = appName;

    }
}
