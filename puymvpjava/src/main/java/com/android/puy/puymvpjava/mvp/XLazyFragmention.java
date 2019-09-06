package com.android.puy.puymvpjava.mvp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import butterknife.Unbinder;
import com.android.puy.puymvpjava.XDroidConf;
import com.android.puy.puymvpjava.customs.material.MaterialRippleLayout;
import com.android.puy.puymvpjava.event.BusProvider;
import com.android.puy.puymvpjava.kit.KnifeKit;
import com.gyf.immersionbar.ImmersionBar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import org.greenrobot.eventbus.EventBus;

//尽用于FragmentPagerAdapter 非第一个页面中
public abstract class XLazyFragmention<P extends IPresent> extends LazyFragmention implements IView<P> {
    protected ImmersionBar mImmersionBar;
    private VDelegate vDelegate;
    private P p;

    private RxPermissions rxPermissions;
    private Unbinder unbinder;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindUI(getRealRootView());
        }
        if (useRxBus()) {
            BusProvider.getBus().register(this);
        }
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        bindEvent();
        initData(savedInstanceState);
    }

    @Override
    public void bindUI(View rootView) {
        unbinder = KnifeKit.bind(this, rootView);
    }

    @Override
    public void bindEvent() {

    }


    public VDelegate getvDelegate() {
        if (vDelegate == null) {
            vDelegate = VDelegateBase.create(context);
        }
        return vDelegate;
    }

    protected P getP() {
        if (p == null) {
            p = newP();
            if (p != null) {
                p.attachV(this);
            }
        }
        return p;
    }

    @Override
    protected void onDestoryLazy() {
        super.onDestoryLazy();
        if (useRxBus()) {
            BusProvider.getBus().unregister(this);
        }
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (getP() != null) {
            getP().detachV();
        }
        getvDelegate().destory();

        p = null;
        vDelegate = null;
    }


    protected RxPermissions getRxPermissions() {
        rxPermissions = new RxPermissions(getActivity());
        rxPermissions.setLogging(XDroidConf.DEV);
        return rxPermissions;
    }


    @Override
    public int getOptionsMenuId() {
        return 0;
    }


    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public boolean useRxBus() {
        return false;
    }

    public void setMaterialRipple(View ...views){
        for (View view:views){
            MaterialRippleLayout.on(view)
                    .rippleColor(Color.BLACK)
                    .create();
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        //在onSupportVisible实现沉浸式
        initImmersionBar();
    }

    public void initImmersionBar() {
    }
}
