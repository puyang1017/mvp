package com.android.puy.puymvpjava.mvp;


public interface IPresent<V> {
    void attachV(V view);

    void detachV();
}
