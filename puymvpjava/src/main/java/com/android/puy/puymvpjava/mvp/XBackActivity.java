package com.android.puy.puymvpjava.mvp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import com.android.puy.puymvpjava.XDroidConf;
import com.android.puy.puymvpjava.customs.SwipeBackActivity;
import com.android.puy.puymvpjava.customs.material.MaterialRippleLayout;
import com.android.puy.puymvpjava.event.BusProvider;
import com.android.puy.puymvpjava.kit.KnifeKit;
import com.android.puy.puymvpjava.router.AppManager;
import com.gyf.immersionbar.ImmersionBar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.RxLifecycle;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.RxLifecycleAndroid;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

//侧滑返回Activity  setSwipeBackEnable 进行设置是否打开返回
public abstract class XBackActivity<P extends IPresent> extends SwipeBackActivity implements IView<P>, LifecycleProvider<ActivityEvent> {
    private ImmersionBar mImmersionBar;
    private VDelegate vDelegate;
    private P p;
    protected Activity context;
    private RxPermissions rxPermissions;
    private Unbinder unbinder;
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        context = this;

        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindUI(null);
            bindEvent();
        }
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        if (useRxBus()) {
            BusProvider.getBus().register(this);
        }
        initData(savedInstanceState);
        AppManager.getInstance().addActivity(this);
    }


    @Override
    public void bindUI(View rootView) {
        unbinder = KnifeKit.bind(this);
    }

    protected VDelegate getvDelegate() {
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
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
        getvDelegate().resume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        getvDelegate().pause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public boolean useRxBus() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
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
//        if (mImmersionBar != null){
//            mImmersionBar.destroy();
//        }
        p = null;
        vDelegate = null;
        mImmersionBar = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getOptionsMenuId() > 0) {
            getMenuInflater().inflate(getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    protected RxPermissions getRxPermissions() {
        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(XDroidConf.DEV);
        return rxPermissions;
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    @Override
    public void bindEvent() {

    }


    public void initStatusBar(int id) {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.titleBar(id);
        mImmersionBar.keyboardEnable(true);
        mImmersionBar.init();
    }

    public void initStatusBarNoKeyBorard(int id) {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.titleBar(id);
        mImmersionBar.init();
    }

    public void initStatusBar(int id, boolean statusBarDark, int navigationBarColor) {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.titleBar(id);
        mImmersionBar.statusBarDarkFont(statusBarDark);
        mImmersionBar.keyboardEnable(true);
        mImmersionBar.navigationBarColor(navigationBarColor);
        mImmersionBar.init();
    }

    public void setMaterialRipple(View... views) {
        for (View view : views) {
            MaterialRippleLayout.on(view)
                    .rippleColor(Color.BLACK)
                    .create();
        }
    }

}
