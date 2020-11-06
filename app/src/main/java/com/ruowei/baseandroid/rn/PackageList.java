package com.ruowei.baseandroid.rn;

import com.BV.LinearGradient.LinearGradientPackage;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.swmansion.gesturehandler.react.RNGestureHandlerPackage;
import com.th3rdwave.safeareacontext.SafeAreaContextPackage;
import com.reactnativecommunity.asyncstorage.AsyncStoragePackage;

import java.util.Arrays;
import java.util.List;

import ca.jaysoo.extradimensions.ExtraDimensionsPackage;

public class PackageList {
    public List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
                new MainReactPackage(),
                //将我们创建的包管理器给添加进来
                new AndroidReactPackage(),
                new RNGestureHandlerPackage(),
                new SafeAreaContextPackage(),
                new LinearGradientPackage(),
                new ExtraDimensionsPackage(),
                new AsyncStoragePackage()
        );
    }
}
