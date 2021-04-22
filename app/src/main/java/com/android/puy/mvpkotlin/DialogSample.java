package com.android.puy.mvpkotlin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.android.puy.puymvpjava.dialogmanager.Dialog;
/**
 * Created by puy on 2021/4/22 13:52
 */
class DialogSample extends AlertDialog implements Dialog {
    protected DialogSample(Context context) {
        super(context);
    }

    protected DialogSample(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected DialogSample(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 是否被挤出（每个实现DialogManager.Dialog的窗口类都需要新建该变量）
     */
    private boolean isCrowdOut;
    @Override
    public void dismiss(boolean isCrowdOut) {
        this.isCrowdOut = isCrowdOut;
        super.dismiss();
    }

    @Override
    public void setOnDismissListener(com.android.puy.puymvpjava.dialogmanager.OnDismissListener listener) {
        super.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                listener.onDismiss(isCrowdOut);
            }
        });
        super.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                listener.onDismiss(isCrowdOut);
            }
        });
    }

    @Override
    public void setOnShowListener(com.android.puy.puymvpjava.dialogmanager.OnShowListener listener) {
        super.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                listener.onShow();
            }
        });
    }

    @Override
    public boolean isCanShow() {
        return true;
    }
}
