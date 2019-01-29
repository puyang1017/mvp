package com.android.puy.puymvpjava.mvp;

import java.lang.ref.WeakReference;


public class XPresent<V extends IView> implements IPresent<V> {
    private WeakReference<V> v;

    /**
     * 用于检查View是否为空对象
     *
     * @return
     */
    public boolean isAttachView() {
        return this.v != null && this.v.get() != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void attachV(V view) {
        v = new WeakReference<V>(view);
    }

    @Override
    public void detachV() {
        if (v.get() != null) {
            v.clear();
        }
        v = null;
    }

    protected V getV() {
        if(v==null||v.get() == null){
            return null;
        }
        return v.get();
    }
}
