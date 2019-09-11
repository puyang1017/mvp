package com.android.puy.puymvpjava.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;


public abstract class LoadCallback {
    public abstract void onLoadFailed(Drawable e);

    public abstract void onLoadReady(Drawable drawable);

    void onLoadCanceled() {
    }
}
