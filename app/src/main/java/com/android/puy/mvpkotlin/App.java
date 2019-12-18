package com.android.puy.mvpkotlin;


import androidx.multidex.MultiDexApplication;

import com.android.puy.puymvpjava.imageloader.ILFactory;

/**
 * Created by puy on 2019/8/23 13:06
 */
public class App extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //图片加载
        ILFactory.getLoader().init(this);
    }
}
