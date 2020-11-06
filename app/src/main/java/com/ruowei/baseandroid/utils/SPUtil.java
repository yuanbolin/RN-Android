package com.ruowei.baseandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.ruowei.baseandroid.MyApplication;
import com.ruowei.baseandroid.common.Common;

import java.util.List;
import java.util.Map;

/**
 * Created by Pierce on 2018/7/2.
 * Project: FireWZ
 * Package: com.ruowei.xu.firewz.utils;
 * ClassName: SPUtil
 * contact way: 1208950702@qq.com
 * If you want to change this class, make sure you get the permission of the author, please.
 * Tips:
 */

public class SPUtil {

    /**
     * 读取文件中的内容
     *
     * @param context
     * @param fileName
     * @param key
     * @param value
     * @return
     */
    public static Object spRead(Context context, String fileName, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (value instanceof String) {
            return sp.getString(key, (String) value);
        } else if (value instanceof Integer) {
            return sp.getInt(key, (Integer) value);
        } else if (value instanceof Float) {
            return sp.getFloat(key, (Float) value);
        } else if (value instanceof Boolean) {
            return sp.getBoolean(key, (boolean) value);
        } else if (value instanceof Long) {
            return sp.getLong(key, (Long) value);
        } else {
            return sp.getString(key, (String) value);
        }
    }

    /**
     * 写入文件内容
     *
     * @param context
     * @param fileName
     * @param key
     * @param value
     */
    public static boolean spWrite(Context context, String fileName, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, (String) value);
        }
        return editor.commit();
    }

    /**
     * 清楚某个值
     *
     * @param context
     * @param fileName
     * @param key
     */
    public static boolean remove(Context context, String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        return editor.commit();
    }

    /**
     * 清除文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static boolean clear(Context context, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        //editor.commit();
        return editor.commit();
    }

    /**
     * 看是否包含此key的信息
     *
     * @param context
     * @param fileName
     * @param key
     * @return
     */
    public static boolean containContent(Context context, String fileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有数据
     *
     * @param context
     * @param fileName
     * @return
     */
    public static Map<String, ?> getAll(Context context, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sp.getAll();
    }


    /**
     * 权限内容写入
     *
     * @param context
     * @param fileName
     * @param key
     * @param value
     */
    public static boolean spWriteQuanxian(Context context, String fileName, String key, Object value) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else {
            editor.putString(key, (String) value);
        }
        return editor.commit();
    }

    /**
     * 权限内容写入
     *
     * @param key
     */
    public static boolean spReadQuanxian(String key) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(Common.LOGIN_FILE, Context.MODE_PRIVATE);
        String appQuanxianStr =  sp.getString("appQuanxianStr", null);

        if(EmptyUtils.isNotEmpty(appQuanxianStr)){
        AppQuanxian appQuanxian = MyApplication.getInstance().gson.fromJson(appQuanxianStr, AppQuanxian.class);
            return appQuanxian.getData().getCaidan().contains(key);
        }else {
            return false;
        }
    }

    /**
     * 获取权限菜单list
     *
     */
    public static List<String> getQuanxianList() {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(Common.LOGIN_FILE, Context.MODE_PRIVATE);
        String appQuanxianStr =  sp.getString("appQuanxianStr", null);

        AppQuanxian appQuanxian = MyApplication.getInstance().gson.fromJson(appQuanxianStr, AppQuanxian.class);

        return appQuanxian.getData().getCaidan();
    }


    private class AppQuanxian {

        /**
         * data : {"caidan":["app/首页/健康食堂/集中采购","app/首页/健康食堂/美食菜谱","app/首页/健康食堂/周食谱","app/首页/健康食堂/刀具管理","app/首页/健康食堂/伙食公布","app/首页/健康食堂/厨师评价"]}
         * message : 查询成功
         * status : 0
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public class DataBean {
            private List<String> caidan;

            public List<String> getCaidan() {
                return caidan;
            }

            public void setCaidan(List<String> caidan) {
                this.caidan = caidan;
            }
        }
    }

}
