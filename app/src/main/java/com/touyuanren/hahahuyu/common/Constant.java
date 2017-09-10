package com.touyuanren.hahahuyu.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by Liang on 2017/8/4 0004.
 */

public class Constant {
    public static final String HEAD_URL = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "head.jpg";// 相机照相保存路径
}
