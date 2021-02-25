package com.android.puy.puymvpjava.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import butterknife.Unbinder;

import com.android.puy.puymvpjava.R;
import com.android.puy.puymvpjava.XDroidConf;
import com.android.puy.puymvpjava.customs.material.MaterialRippleLayout;
import com.android.puy.puymvpjava.event.BusProvider;
import com.android.puy.puymvpjava.kit.KnifeKit;
import com.android.puy.puymvpjava.utils.PyUIKeyboardHelper;
import com.android.puy.puymvpjava.utils.PyUIStatusBarHelper;
import com.android.puy.puymvpjava.utils.StephenToolUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;


public abstract class XActivity<P extends IPresent> extends RxAppCompatActivity implements IView<P> {
    public static final String ParamIndex = "ParamIndex", ParamBundle = "ParamBundle", ParamBase = "ParamBase", ParamObj1 = "ParamObj1", ParamObj2 = "ParamObj2", ParamObj3 = "ParamObj3", ParamObj4 = "ParamObj4", ParamObj5 = "ParamObj5", ParamObj6 = "ParamObj6";
    private long firstKeyTime = 0;//第一次按键时间

    protected ImmersionBar mImmersionBar;
    private VDelegate vDelegate;
    private P p;
    protected Activity context;
    private RxPermissions rxPermissions;
    private Unbinder unbinder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        } else {
            if (getLayoutView() != null) {
                setContentView(getLayoutView());
            }
        }
        if (needPyUiImmersionBar()) {
            PyUIStatusBarHelper.translucent(context);
        }
        bindUI(null);
        bindEvent();
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        initData(savedInstanceState);
    }

    public View getLayoutView() {
        return null;
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
        if (useRxBus()) {
            BusProvider.getBus().register(this);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getvDelegate().resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getvDelegate().pause();
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
//        if (mImmersionBar != null) {
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN && isCheckBlackHideKeyBoard()) {//点击空白区域,隐藏输入法
            View view = getCurrentFocus();
            if (null != view && StephenToolUtils.isShouldHideKeyboard(view, ev))
                PyUIKeyboardHelper.hideKeyboard(view);
        }//end of if
        return super.dispatchTouchEvent(ev);
    }


    //是否需要点击空白区域隐藏输入法
    public boolean isCheckBlackHideKeyBoard() {
        return true;//default
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (backCheckOperation()) {
                    if (needExitActivity()) {
                        long secondTime = System.currentTimeMillis();
                        if (secondTime - firstKeyTime > 2000) { //如果两次按键时间间隔大于2秒
                            StephenToolUtils.showShortHintInfo(context, "快速点击两次退出应用");
                            firstKeyTime = secondTime;//更新firstTime
                            return true;
                        } else {//两次按键小于2秒时
                            minimizationProgram();
                        }
                    } else {
                        backToPrevActivity();
                    }
                }//end of if
                break;
        }//end of switch_
        return true;
        //return super.onKeyUp(keyCode, event);
    }

    //需要直接退出的activity
    public boolean needExitActivity() {
        return false;//(null != activity && (activity instanceof MainActivity));
    }

    //返回上一步
    public void backToPrevActivity() {
        if (null != context) context.finish();
    }

    //返回时检查操作
    public boolean backCheckOperation() {
        return true;//default
    }

    //菜单左边按键响应
    public void backBtnClickResponse() {
        if (backCheckOperation()) backToPrevActivity();//default
    }

    //关闭程序之前的操作
    public void beforeFinishProgram() {
    }

    //最小化程序
    public void minimizationProgram() {
        beforeFinishProgram();
        moveTaskToBack(true);
        //System.exit(0);
    }

    //kill程序
    public void killSelfProgram() {
        minimizationProgram();
        android.os.Process.killProcess(android.os.Process.myPid());
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

    //是否需要QMUI沉浸式  默认关闭
    public boolean needPyUiImmersionBar() {
        return false;
    }

    //单独设置沉浸式
    public void initStatusBar(int id) {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.titleBar(id);
        mImmersionBar.keyboardEnable(true);
        mImmersionBar.init();
    }

    public void initStatusBar(int id, boolean statusBarDark) {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.titleBar(id);
        mImmersionBar.statusBarDarkFont(statusBarDark);
        mImmersionBar.keyboardEnable(true);
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

    public void setMaterialRipple(int color, View... views) {
        for (View view : views) {
            MaterialRippleLayout.on(view)
                    .rippleColor(color)
                    .rippleOverlay(true)
                    .ripplePersistent(false)
                    .create();
        }
    }
}
