package com.touyuanren.hahahuyu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.touyuanren.hahahuyu.Application.MyApplication;

/**
 * 保存临时文件SP
 */
public class FoPreference
{
    private static String share_name = "hahahuyu_share";

    public static SharedPreferences getPreference()
    {
        return MyApplication.getContext().getSharedPreferences(share_name, Context.MODE_PRIVATE);
    }

    public static void putString(String key, String value)
    {
        Editor editor = getPreference().edit();

        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key)
    {
        return getPreference().getString(key, "");
    }

    public static void putInt(String key, int value)
    {
        Editor editor = getPreference().edit();

        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(String key)
    {
        return getPreference().getInt(key, 0);
    }

    public static void putBoolean(String key, boolean value)
    {
        Editor editor = getPreference().edit();

        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(String key)
    {
        return getPreference().getBoolean(key, false);
    }

    public static void putLong(String key, long value)
    {
        Editor editor = getPreference().edit();

        editor.putLong(key, value);
        editor.commit();
    }

    public static Long getLong(String key)
    {
        return getPreference().getLong(key, 0);
    }
}
