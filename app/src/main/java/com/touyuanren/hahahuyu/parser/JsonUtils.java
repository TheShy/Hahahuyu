package com.touyuanren.hahahuyu.parser;

import android.text.TextUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JsonUtils {

    public static final String TAG = JsonUtils.class.getName();

    private static final Object lock = new Object();
    private static JsonUtils jsonUtil;
    private Gson gson;

    public static JsonUtils getInstance() {
        synchronized (lock) {
            if (jsonUtil == null) {
                jsonUtil = new JsonUtils();
            }
        }
        return jsonUtil;
    }

    private JsonUtils() {
        GsonBuilder gb = new GsonBuilder();
        gb.serializeNulls();
        gb.setExclusionStrategies(new ExclusionStrategy() {

            @Override
            public boolean shouldSkipField(FieldAttributes arg0) {
                return arg0.getAnnotation(ExcludeJSON.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> arg0) {
                return arg0.getAnnotation(ExcludeJSON.class) != null;
            }
        });
        gson = gb.create();
    }

    /**
     * 将实体类序列化为Json
     *
     * @param obj 数据实体类
     * @return Json字符串
     */
    public String toJSON(Object obj) {
        if (obj == null) {
            return "";
        }
        return gson.toJson(obj);
    }

    /**
     * 根据传入的类型，得到对应的实体类
     * 此方法用于返回Class
     *
     * @param clazz 传入的Class
     * @param json  Json字符串
     * @param <T>   泛型
     * @return 对应的数据实体类实例
     */
    public  <T> T fromJSON(Class<T> clazz, String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return gson.fromJson(json, clazz);
    }

    /**
     * 根据传入的类型，得到对应的实体类集合
     * 此方法用于返回集合
     *
     * @param type 数据类型的Type
     * @param json Json字符串
     * @param <T>  泛型
     * @return 对应的数据实体类集合
     */
    public <T> T fromJSON(Type type, String json) {
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, type);
    }

}