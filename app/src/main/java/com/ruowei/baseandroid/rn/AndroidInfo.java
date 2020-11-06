package com.ruowei.baseandroid.rn;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.ruowei.baseandroid.MyApplication;
import com.ruowei.baseandroid.R;
import com.ruowei.baseandroid.common.Common;
import com.ruowei.baseandroid.utils.EmptyUtils;
import com.ruowei.baseandroid.utils.SPUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.NO_ID;

public class AndroidInfo extends ReactContextBaseJavaModule implements ActivityEventListener {
    ReactApplicationContext context;
    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";
    private static final String TOKEN_KEY = "TOKEN";
    private static final String ZUZHIJIGOU = "ZUZHIJIGOU";
    private static final String ZUZHIJIGOUDAIMA = "ZUZHIJIGOUDAIMA";
    private static final String RENYUANBIANHAO = "RENYUANBIANHAO";
    private static final String RENYUANMINGCHENG = "RENYUANMINGCHENG";
    private static final String BASEURL = "BASEURL";
    private MyReactActivity myReactActivity;


    private Callback pickerSuccessCallback;
    private Callback pickerCancelCallback;
    public static HashMap<String,Object> map = new HashMap<>();

    public AndroidInfo(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
        reactContext.addActivityEventListener(this);
    }




    @Override
    public void onNewIntent(Intent intent) {

    }

    @NonNull
    @Override
    public String getName() {
        return "AndroidInfo";
    }

    /**
     * 给rn定义模块的一些常量
     *
     * @return 常量的一些键值
     */
    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        myReactActivity = (MyReactActivity) getCurrentActivity();
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
        constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
        constants.put(TOKEN_KEY, (String) SPUtil.spRead(context, Common.LOGIN_FILE, Common.KEY_LOGIN_SP, ""));
        constants.put(ZUZHIJIGOU, (String) SPUtil.spRead(context, Common.LOGIN_FILE, Common.KEY_JG_NAME, ""));
        constants.put(ZUZHIJIGOUDAIMA, (String) SPUtil.spRead(context, Common.LOGIN_FILE, Common.KEY_JG_CODE, ""));
        constants.put(RENYUANBIANHAO, (String) SPUtil.spRead(context, Common.LOGIN_FILE, Common.KEY_LOGIN_ID, ""));
        constants.put(RENYUANMINGCHENG, (String) SPUtil.spRead(context, Common.LOGIN_FILE, Common.KEY_USER_NAME, ""));
        constants.put("LINK_URL",LinkUrl.LINK_URL);
        constants.put(BASEURL, (String) SPUtil.spRead(context, Common.URL_FILE, Common.KEY_BASE_URL, "https://xunte.vip:443"));
        //mainActivity.getBottomHeight()
        constants.put("HEIGHT", "71.5");
        return constants;

    }

    @ReactMethod
    public void selectPic(Callback successCallback, Callback cancelCallback) {
        if (getCurrentActivity() == null) {
            cancelCallback.invoke("activity无法进入");
            return;
        }
        pickerSuccessCallback = successCallback;
        pickerCancelCallback = cancelCallback;

        PictureSelector.create(getCurrentActivity())
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(8)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .setOutputCameraPath("/RuoweiImgPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(false)// 是否压缩
                .recordVideoSecond(30)
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .isGif(false)// 是否显示gif图片
                .openClickSound(false)// 是否开启点击声音
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code


    }

    /**
     * 从原生Activity里面调用该方法，传数据给RN界面
     *
     * rn 中
     *   componentDidMount() {
     *        DeviceEventEmitter.addListener('nativeCallRn',(msg)=>{
     *             title = "React Native界面,收到数据：" + msg;
     *             ToastAndroid.show("发送成功", ToastAndroid.SHORT);
     *        })
     *    }
     **/
    public void sendDataToJS(String path) {
        Log.i("msg", "sendDataToJS: ===path==" + path);
        this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("nativeCallRn", path);
    }


    /**
     * 开启loading动画
     */
    @ReactMethod
    public void showLoading() {
        myReactActivity = (MyReactActivity) getCurrentActivity();
        if (EmptyUtils.isNotEmpty(myReactActivity)){
            myReactActivity.showLoading();
        }
    }
    /**
     * 关闭loading动画
     */
    @ReactMethod
    public void closeLoading() {
        myReactActivity = (MyReactActivity) getCurrentActivity();
        if (EmptyUtils.isNotEmpty(myReactActivity)){
            myReactActivity.closeLoading();
        }
    }

    @ReactMethod
    public void finishReact() {
        myReactActivity = (MyReactActivity) getCurrentActivity();
        if (EmptyUtils.isNotEmpty(myReactActivity)){
            myReactActivity.finish();
        }
    }

    @ReactMethod
    public void showToast(String toastInfo) {
        myReactActivity = (MyReactActivity) getCurrentActivity();
        if (EmptyUtils.isNotEmpty(myReactActivity)){
            myReactActivity.showToast(toastInfo);
        }
    }



    /**
     * 使用ReactMethod注解，使这个方法被js调用
     *
     * @param message  文本
     * @param duration 时长
     */
    @ReactMethod
    public void show(String message, int duration, Callback success, Callback error) {
        try {

            Toast.makeText(context, message, duration).show();
            success.invoke("success");
        } catch (Exception e) {
            error.invoke("error");
        }
    }

    //用ReactNative注解，标示此方法是可以被RN调用的
    @ReactMethod
    public void getToken(Promise promise) {

        String token = (String) SPUtil.spRead(context, Common.LOGIN_FILE, Common.KEY_LOGIN_SP, "");
        promise.resolve(token);
    }
    //用ReactNative注解，标示此方法是可以被RN调用的
    @ReactMethod
    public void getIntentData(Promise promise) {
        HashMap<String,Object> map = this.map;
        String str = MyApplication.gson.toJson(map);
        promise.resolve(str);
    }


    // rn打开native的页面 这里rn中name写法为com.ruowei.baseandroid.MainActivity
    @ReactMethod
    public void startActivityRN(String name, String params) {
        try {
            Activity activity = getCurrentActivity();
            if (activity != null) {
                Class toClass = Class.forName(name);
                Intent intent = new Intent(activity, toClass);
                intent.putExtra("params", params);
                activity.startActivity(intent);
            }
        } catch (Exception ex) {
            throw new JSApplicationIllegalArgumentException("不能打开Activity " + ex.getMessage());
        }
    }
    @ReactMethod
    public void startActivityRN(String name) {
        try {
            Activity activity = getCurrentActivity();
            if (activity != null) {
                Class toClass = Class.forName(name);
                Intent intent = new Intent(activity, toClass);

                activity.startActivity(intent);
            }
        } catch (Exception ex) {
            throw new JSApplicationIllegalArgumentException("不能打开Activity " + ex.getMessage());
        }
    }


    /**
     * 是否有虚拟按键
     *
     * @param success
     * @param error
     */
    @ReactMethod
    public void hasBottomBar(Callback success, Callback error) {
        try {

            if (isNavigationBarExist(getCurrentActivity())) {
                success.invoke(true);
            } else {
                success.invoke(false);
            }

        } catch (Exception e) {
            error.invoke("error");
        }
    }

    private static final String NAVIGATION= "navigationBarBackground";

    // 该方法需要在View完全被绘制出来之后调用，否则判断不了
    //在比如 onWindowFocusChanged（）方法中可以得到正确的结果
    public static  boolean isNavigationBarExist(@NonNull Activity activity){
        ViewGroup vp = (ViewGroup) activity.getWindow().getDecorView();
        if (vp != null) {
            for (int i = 0; i < vp.getChildCount(); i++) {
                vp.getChildAt(i).getContext().getPackageName();
                if (vp.getChildAt(i).getId()!= NO_ID && NAVIGATION.equals(activity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                    return true;
                }
            }
        }
        return false;
    }




    /**
     * 获取虚拟按键高度
     *
     * @param success
     * @param error
     */
    @ReactMethod
    public void getBottomBarHeight(Callback success, Callback error) {
//        try {
//            success.invoke(getNavigationBarHeight(context) + "");
//
//        } catch (Exception e) {
//            error.invoke("error");
//        }
    }

    @ReactMethod
    public void getInitialURL(Promise promise) {
        try {
            String initialURL = myReactActivity.myBlockingQueue.take();
            if (promise != null){
                promise.resolve(initialURL);
            }
        } catch (InterruptedException e) {
            if (promise != null){
                promise.reject(new JSApplicationIllegalArgumentException(
                        "Could not get the initial URL : " + e.getMessage()));
            }
        }
    }


    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:

                if (pickerSuccessCallback != null) {
                    // 图片选择结果回调
                    List<LocalMedia> selectListAll = new ArrayList<>();
                    selectListAll = PictureSelector.obtainMultipleResult(data);
                    List<String> paths = new ArrayList<>();
                    for (LocalMedia media : selectListAll) {
                        paths.add(media.getPath());
                    }


                    String pathAll = "";
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < paths.size(); i++) {
                        if (i == paths.size() - 1) {
                            stringBuffer.append(paths.get(i));
                        } else {
                            stringBuffer.append(paths.get(i) + ",");
                        }
                    }
                    pathAll = stringBuffer.toString();

                    if (paths.size() > 0) {
                        pickerSuccessCallback.invoke(pathAll);
                    } else {
                        pickerCancelCallback.invoke("没有选择图片");
                    }
                } else {
                }

                break;
        }


    }


}
