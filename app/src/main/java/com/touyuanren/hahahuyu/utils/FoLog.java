package com.touyuanren.hahahuyu.utils;

import android.util.Log;

/**
 * @author linmu
 */
public class FoLog {

    /**
     * logcat日志开关 true 输出 false 不输出
     */
    private static final boolean isLogCatEnabled = true;

    /**
     * log级别、只打印mPrintLogLevel级别以上的LOG
     */
    private static final int mPrintLogLevel = Log.VERBOSE;

    public static void d(Object tag, Object content) {
        debug(String.valueOf(tag), String.valueOf(content), Log.DEBUG, true);
    }

    public static void i(Object tag, Object content) {
        debug(String.valueOf(tag), String.valueOf(content), Log.INFO, true);
    }

    public static void e(Object tag, Object content) {
        debug(String.valueOf(tag), String.valueOf(content), Log.ERROR, true);
    }

    public static void w(Object tag, Object content) {
        debug(String.valueOf(tag), String.valueOf(content), Log.WARN, true);
    }

    public static void v(Object tag, Object content) {
        debug(String.valueOf(tag), String.valueOf(content), Log.VERBOSE, true);
    }

    private static void debug(String tag, String content, int level,
                              boolean isToLagcat) {

        if (!isLogCatEnabled) {
            return;
        }

        if (mPrintLogLevel > level) {
            return;
        }

        switch (level) {
            case Log.VERBOSE:
                if (isToLagcat && isLogCatEnabled) {
                    Log.v(String.valueOf(tag), String.valueOf(content));
                }
                break;
            case Log.DEBUG:
                if (isToLagcat && isLogCatEnabled) {
                    Log.d(String.valueOf(tag), String.valueOf(content));
                }
                break;
            case Log.INFO:
                if (isToLagcat && isLogCatEnabled) {
                    Log.i(String.valueOf(tag), String.valueOf(content));
                }
                break;
            case Log.WARN:
                if (isToLagcat && isLogCatEnabled) {
                    Log.w(String.valueOf(tag), String.valueOf(content));
                }
                break;
            case Log.ERROR:
                if (isToLagcat && isLogCatEnabled) {
                    Log.e(String.valueOf(tag), String.valueOf(content));
                }
                break;
        }
    }

    public static void d1(String tag, String content) {
        debug("YSL", String.valueOf(tag + "__" + content), Log.DEBUG, true);
    }

    public static void e1(String tag, String content) {
        debug("YSL", String.valueOf(tag + "__" + content), Log.ERROR, true);
    }

    public static void e1(String tag, Exception error) {
        debug("YSL", Log.getStackTraceString(error), Log.ERROR, true);
    }
    //付浩修改的log

    /**
     * 打印d级别的log
     */
    public static void d(String tag, String msg) {
        if (isLogCatEnabled) {
            Log.d(tag, msg);
        }
    }

    /**
     * 打印d级别的log
     */
    public static void d(Object object, String msg) {
        if (isLogCatEnabled) {
            Log.d(object.getClass().getSimpleName(), msg);
        }
    }

    /**
     * 打印e级别的log
     */
    public static void e(String tag, String msg) {
        if (isLogCatEnabled) {
            Log.e(tag, msg);
        }
    }

    /**
     * 打印e级别的log
     */
    public static void e(Object object, String msg) {
        if (isLogCatEnabled) {
            Log.e(object.getClass().getSimpleName(), msg);
        }
    }
}
