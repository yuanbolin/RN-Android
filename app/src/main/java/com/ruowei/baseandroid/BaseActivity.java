package com.ruowei.baseandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ruowei.baseandroid.utils.BarUtils;


/**
 * Created by Pierce on 2018/6/29.
 * Project: FireWZ
 * Package: com.ruowei.xu.firewz;
 * ClassName: BaseActivity
 * contact way: 1208950702@qq.com
 * If you want to change this class, make sure you get the permission of the author, please.
 * Tips:
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 返回键
     */
    public TextView title;
    /**
     * 标题
     */
    public ImageView back;

    //网络连接异常
    public LinearLayout onNetWork;
    //动画
    public ImageView loading_anim;
    public AnimationDrawable mAnimationDrawable;
    //loading
    public LinearLayout loadingLayout;
    //暂无数据
    public LinearLayout loadingNoDate;
    //加载失败
    public LinearLayout loadingFailed;




    @Override
    protected void onDestroy() {
        MyApplication.cancelAllRequest();
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void isVisibile(View v1, View v2, View v3, View v4, View v5) {
        v1.setVisibility(View.VISIBLE);
        v2.setVisibility(View.GONE);
        v3.setVisibility(View.GONE);
        v4.setVisibility(View.GONE);
        v5.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();

    }

    /**
     * 全局沉浸式状态栏
     * 可以在Activity中重写该方法
     */
    protected void setStatusBar() {
        BarUtils.setColor(this, Color.BLUE, 00);
    }
    /**
     * 获取颜色
     *
     * @param colorRes 资源ID
     * @return
     */
    protected int getResColor(int colorRes) {
        return getResources().getColor(colorRes);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 增加默认的界面切换动画
     */
    @Override
    public void startActivity(Intent intent) {
        startActivity(intent, true);
    }

    public void startActivity(Intent intent, boolean anim) {
        super.startActivity(intent);
        if (anim) overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode, true);
    }

    public void startActivityForResult(Intent intent, int requestCode, boolean anim) {
        super.startActivityForResult(intent, requestCode);
        if (anim) overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    @Override
    public void finish() {
        finish(true);
    }

    public void finish(boolean anim) {
        super.finish();
        if (anim) overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }


}
