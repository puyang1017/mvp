package com.android.puy.puymvpjava.mvp;

import android.os.Bundle;
import android.view.View;


public interface IView<P> {
    void bindUI(View rootView);

    void bindEvent();

    void initData(Bundle savedInstanceState);

    int getOptionsMenuId();

    int getLayoutId();

    boolean useEventBus();

    boolean useRxBus();

    P newP();
}
