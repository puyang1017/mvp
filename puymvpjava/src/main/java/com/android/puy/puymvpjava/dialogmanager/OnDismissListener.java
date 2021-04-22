package com.android.puy.puymvpjava.dialogmanager;

/**
 * Created by puy on 2021/4/22 12:07
 */
public interface OnDismissListener {
    /**
     * @param isCrowdOut 是否被挤出
     */
    void onDismiss(boolean isCrowdOut);
}
