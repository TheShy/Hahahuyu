package com.touyuanren.hahahuyu.utils;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.touyuanren.hahahuyu.http.HttpApi;

import java.io.File;
import java.util.Map;

/**
 * Created by Jing on 2016/9/7.
 * 封装网络请求
 */
public class FoHttp {

    public static void getMsg(String urlString, Map<String, String> map, StringCallback Callback) {
        map.put("token", FoPreference.getString(FoContents.LOGIN_TOKEN));
        //加入登录的判断接口请求	api/hd/a_reward.php
        OkHttpUtils.post().url(urlString).params(UrlHelper.getInstance().getBaseParams(map)).build().execute(Callback);
    }

    public static void upLoadFile(String urlString, Map<String, String> map, File file, StringCallback Callback) {
        map.put("token", FoPreference.getString(FoContents.LOGIN_TOKEN));
        //加入登录的判断
        OkHttpUtils.post().url(urlString).params(UrlHelper.getInstance().getBaseParams(map)).addFile("pic", file.getName(), file).build().execute(Callback);
    }
    public static void upLoadFile2(String urlString, Map<String, String> map, File file, StringCallback Callback) {
        map.put("token", FoPreference.getString(FoContents.LOGIN_TOKEN));
        //加入登录的判断
        OkHttpUtils.post().url(urlString).params(UrlHelper.getInstance().getBaseParams(map)).addFile("content", file.getName(), file).build().execute(Callback);
    }

    //打赏
    public static void reword(Map<String, String> map, StringCallback Callback) {
        getMsg(HttpApi.ROOT_PATH+HttpApi.REWARD,map,Callback);
    }

}
