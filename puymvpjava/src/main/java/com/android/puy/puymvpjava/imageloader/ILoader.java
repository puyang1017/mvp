package com.android.puy.puymvpjava.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.android.puy.puymvpjava.XDroidConf;

import java.io.File;


public interface ILoader {

    void init(Context context);

    void loadNet(ImageView target, String url, Options options);

    void loadNet(ImageView target, String url, Options options, boolean anim);

    void loadNet(ImageView target, String url, Options options, NetLoadCallback netLoadCallback);

    void loadNet(Context context, String url, Options options, LoadCallback callback);

    void loadResource(ImageView target, int resId, Options options);

    void loadAssets(ImageView target, String assetName, Options options);

    void loadFile(ImageView target, File file, Options options);

    void loadFile(ImageView target, File file, Options options, boolean cache);

    void clearMemoryCache(Context context);

    void clearDiskCache(Context context);

    void resume(Context context);

    void pause(Context context);


    class Options {

        public Drawable drawable;
        public int loadingResId = RES_NONE;        //加载中的资源id
        public int loadErrorResId = RES_NONE;      //加载失败的资源id
        public ImageView.ScaleType scaleType = null;

        public static final int RES_NONE = -1;

        public static Options defaultOptions() {
            return new Options(XDroidConf.IL_LOADING_RES, XDroidConf.IL_ERROR_RES);
        }

        public Options(int loadingResId, int loadErrorResId) {
            this.loadingResId = loadingResId;
            this.loadErrorResId = loadErrorResId;
        }

        public Options scaleType(ImageView.ScaleType scaleType) {
            this.scaleType = scaleType;
            return this;
        }
    }

}
