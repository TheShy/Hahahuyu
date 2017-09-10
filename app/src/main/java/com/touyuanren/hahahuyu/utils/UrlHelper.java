package com.touyuanren.hahahuyu.utils;

import android.text.TextUtils;

import com.touyuanren.hahahuyu.http.HttpApi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ice_coffee on 2016/4/13.
 */
public class UrlHelper
{
    private static UrlHelper instance = new UrlHelper();

    public static UrlHelper getInstance()
    {
        return instance;
    }

    /**
     * map集合转string
     *
     * @param map
     * @return
     */
    private String getStrFormMap(Map<String, String> map)
    {
        if (null == map)
        {
            map = new HashMap<String, String>();
        }

        StringBuffer sb = new StringBuffer();

        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            if (i == 0)
            {
                sb.append("?");
            } else
            {
                sb.append("&");
            }
            sb.append(entry.getKey() + "=" + entry.getValue());
            i++;
        }

        return sb.toString().trim();
    }

    /**
     * 添加时间戳,authtoken,checktoken到请求参数map中
     *
     * @param map
     * @return
     */
    public Map<String, String> getBaseParams(Map<String, String> map)
    {
        if (null == map)
        {
            map = new HashMap<>();
        }

        //时间戳并添加
        String stampToken = System.currentTimeMillis() + "";
        map.put(HttpApi.STAMPTOKEN, stampToken);

        String authToken = FoPreference.getString(HttpApi.AUTHTOKEN);
        if (!TextUtils.isEmpty(authToken))
        {
//            map.put(HttpApi.AUTHTOKEN, authToken);
        }

        //checktoken并添加
        String checkToken = getCheckToken(map);
        map.put(HttpApi.CHECKTOKEN, checkToken);

        return map;
    }

    private static final String FLAG = "=";

    /**
     * 通过把所有的请求参数使用MD5编码生成checktoken
     *
     * @param map
     * @return
     */
    private String getCheckToken(Map<String, String> map)
    {
        if (null == map)
        {
            return null;
        }

        /**
         * 定义数组保存参数"key=value"字符串
         *      通过"key=value"这种形式进行排序
         */
        String[] arrys = new String[map.size()];
        int lengthId = 0;
        for (Map.Entry<String, String> entry : map.entrySet())
        {
            arrys[lengthId] = getTokenParams(entry.getKey(), entry.getValue());
            lengthId++;
        }

        //数组排序
        Arrays.sort(arrys);

        //获取参数value字符串
        String check = "";
        for (String s : arrys)
        {
            check = check + s.substring(s.indexOf(FLAG) + 1);
        }

        //添加请求key
        check += HttpApi.KEY_REQUEST;
        //使用MD5编码
//        String checkToken = StringHelper.toMD5(check);
        String checkToken = null;

        try
        {
            checkToken = MD5Util.md5Encode(check);
        } catch (Exception e)
        {

        }

        return checkToken;
    }

    private static String getTokenParams(String name, String value)
    {
        return name + FLAG + value;
    }
}
