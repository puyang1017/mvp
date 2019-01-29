package com.android.puy.puymvpjava.imageloader;

import android.graphics.Bitmap;


public abstract class LoadCallback {
    public abstract void onLoadFailed(Throwable e);

    public abstract void onLoadReady(Bitmap bitmap);

    void onLoadCanceled() {
    }
}
