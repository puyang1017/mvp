package com.android.puy.mvpkotlin;

import android.app.Application;
import com.android.puy.puymvpjava.imageloader.ILFactory;

/**
 * Created by puy on 2019/8/23 13:06
 */
public class  App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //图片加载
        ILFactory.getLoader().init(this);
    }
}
