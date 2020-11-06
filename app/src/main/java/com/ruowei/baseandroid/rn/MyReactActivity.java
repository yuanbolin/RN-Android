package com.ruowei.baseandroid.rn;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.ruowei.baseandroid.BuildConfig;
import com.ruowei.baseandroid.R;
import com.ruowei.baseandroid.utils.EmptyUtils;
import com.ruowei.baseandroid.utils.MToast;
import com.ruowei.baseandroid.utils.ProgressLoading;
import com.swmansion.gesturehandler.react.RNGestureHandlerEnabledRootView;

import java.util.concurrent.ArrayBlockingQueue;


public class MyReactActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {

    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    QMUITipDialog tipDialog;

    public static ArrayBlockingQueue<String> myBlockingQueue = new ArrayBlockingQueue<String>(1);//临时和RN交互的数据


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null && getIntent().getData() != null) {
            myBlockingQueue.clear();
            myBlockingQueue.add(getIntent().getData().toString());
        }
        tipDialog = new QMUITipDialog.Builder(MyReactActivity.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        mReactRootView = new RNGestureHandlerEnabledRootView(this);
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setCurrentActivity(this)
                .setBundleAssetName("index.android.bundle")
                .setJSMainModulePath("index")
                .addPackages(new PackageList().getPackages())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        // 注意这里的MyReactNativeApp必须对应“index.js”中的
        // “AppRegistry.registerComponent()”的第一个参数
        mReactRootView.startReactApplication(mReactInstanceManager, "BaseAndroid", null);

        setContentView(mReactRootView);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostDestroy(this);
        }
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
        }
    }

    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void showLoading() {
        tipDialog.show();
    }

    public void closeLoading() {
        tipDialog.dismiss();
    }

    public void showToast(String info) {
        if (EmptyUtils.isNotEmpty(info))
            MToast.show(MyReactActivity.this, info);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处必写 否则AndroidInfo拿不到回调
        mReactInstanceManager.onActivityResult(this, requestCode, resultCode, data);
    }
}
