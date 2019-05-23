package com.ff.factoryimage.utils;

import android.util.Log;

public class LogUtil {
    private static final String TAG_PREFIX = "FactoryImage--";

    public static void d(String msg) {
        Log.d(TAG_PREFIX, msg);
    }

    public static void i(String msg) {
        Log.i(TAG_PREFIX, msg);
    }

    public static void e(String msg) {
        Log.e(TAG_PREFIX, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(TAG_PREFIX + tag, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(TAG_PREFIX + tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(TAG_PREFIX + tag, msg);
    }
}
