package com.ruowei.baseandroid.utils;

import android.util.Log;

/**
 * Created by Pierce on 2018/7/2.
 * Project: FireWZ
 * Package: com.ruowei.xu.firewz.utils;
 * ClassName: MLog
 * contact way: 1208950702@qq.com
 * If you want to change this class, make sure you get the permission of the author, please.
 * Tips:
 */

public class MLog {
    public static boolean isLog = false;

    public static void setIsLog(boolean isl) {
        isLog = isl;
    }

    public static void e(String tag, String msg) {
        if (isLog) {
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isLog) {
            Log.v(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isLog) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isLog) {
            Log.d(tag, msg);
        }
    }
}
