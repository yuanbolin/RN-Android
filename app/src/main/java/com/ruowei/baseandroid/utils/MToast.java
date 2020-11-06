package com.ruowei.baseandroid.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Pierce on 2018/7/2.
 * Project: FireWZ
 * Package: com.ruowei.xu.firewz.utils;
 * ClassName: MToast
 * contact way: 1208950702@qq.com
 * If you want to change this class, make sure you get the permission of the author, please.
 * Tips:
 */

public class MToast {
    public static Toast toast = null;

    /**
     * Toast显示
     *
     * @param text
     */
    public static void show(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    /**
     * Toast显示
     *
     * @param text
     */
    public static void showLong(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

}
