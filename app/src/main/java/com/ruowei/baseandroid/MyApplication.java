package com.ruowei.baseandroid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.react.BuildConfig;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ruowei.baseandroid.common.CrashHandler;
import com.ruowei.baseandroid.rn.AndroidReactPackage;
import com.ruowei.baseandroid.utils.AppUtils;
import com.ruowei.baseandroid.utils.MLog;
import com.swmansion.gesturehandler.react.RNGestureHandlerPackage;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.Arrays;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Pierce on 2018/6/29.
 * Project: FireWZ
 * Package: com.ruowei.xu.firewz;
 * ClassName: MyApplication
 * contact way: 1208950702@qq.com
 * If you want to change this class, make sure you get the permission of the author, please.
 * Tips:
 */

public class MyApplication extends Application implements ReactApplication {

    public static RequestQueue mQueue;
    public static Context context = null;
    private static final String TAG = "JPush";
    public static Gson gson;
    private static SharedPreferences.Editor editor;
    @SuppressLint("StaticFieldLeak")
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        mQueue = Volley.newRequestQueue(context);
        MLog.setIsLog(true);
        super.onCreate();
        instance = this;
        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        gson = new Gson();
        //异常捕获
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getMyApplicationContext());
//        ZXingLibrary.initDisplayOpinion(this);
        initBugly();
        SoLoader.init(this, /* native exopackage */ false);
    }

    /**
     * 初始化腾讯bug管理平台
     */
    private void initBugly() {
        /* Bugly SDK初始化
         * 参数1：上下文对象
         * 参数2：APPID，平台注册时得到,注意替换成你的appId
         * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
         * 注意：如果您之前使用过Bugly SDK，请将以下这句注释掉。
         */
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(AppUtils.getVersionName(getApplicationContext()));
        strategy.setAppPackageName(AppUtils.getPackageName(getApplicationContext()));
        strategy.setAppReportDelay(20000);                          //Bugly会在启动20s后联网同步数据

        /*  第三个参数为SDK调试模式开关，调试模式的行为特性如下：
            输出详细的Bugly SDK的Log；
            每一条Crash都会被立即上报；
            自定义日志将会在Logcat中输出。
            建议在测试阶段建议设置成true，发布时设置为false。*/

        CrashReport.initCrashReport(getApplicationContext(), "b10912406d", true ,strategy);

        //Bugly.init(getApplicationContext(), "1374455732", false);
    }



   /**
     * 获得当前app运行的AppContext
     *
     * @return AppContext
     */
    public static MyApplication getInstance() {
        return instance;
    }



    public static Context getMyApplicationContext() {
        return context;
    }

    public static RequestQueue getQueue() {
        return mQueue;
    }

    public static void cancelAllRequest() {
        if (mQueue != null) {
            mQueue.cancelAll("FireWZDZ");
        }
    }

    // 获取功能权限
    public static boolean isHasQuanxian(Context context, String str){
        String appQuanxianStr = context.getSharedPreferences("data",MODE_PRIVATE).getString("appQuanxianStr",null);
        JsonElement je = new JsonParser().parse(appQuanxianStr);
        boolean flag =je.getAsJsonObject().get(str).getAsBoolean();
        return flag;
    }

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {

        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    //将我们创建的包管理器给添加进来
                    new AndroidReactPackage(),
                    new RNGestureHandlerPackage()

            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

}
