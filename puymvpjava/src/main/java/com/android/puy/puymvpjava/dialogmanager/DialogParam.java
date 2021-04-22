package com.android.puy.puymvpjava.dialogmanager;

/**
 * Created by puy on 2021/4/22 13:34
 */

/**
 * 窗口参数类
 */
public class DialogParam {
    /**
     * “窗口”
     */
    private Dialog dialog;
    /**
     * 优先级，值越大优先级越高
     */
    private int priority;
    /**
     * 当前是否处于show状态
     */
    private boolean isShowing;
    /**
     * 是否准备show（在show之后非用户自己手动关掉弹窗（调dismiss或触摸弹窗外部）
     * 接着show了一个优先级更高的Dialog，当该Dialog dismiss后可自动show剩下的
     * 优先级最高的Dialog
     * ）
     */
    private boolean prepareShow;

    private DialogParam(Builder builder) {
        dialog = builder.dialog;
        priority = builder.priority;
        prepareShow = builder.prepareShow;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    public boolean isPrepareShow() {
        return prepareShow;
    }

    public void setPrepareShow(boolean prepareShow) {
        this.prepareShow = prepareShow;
    }

    public static class Builder {
        /**
         * “窗口”
         */
        private Dialog dialog;
        /**
         * 优先级，值越大优先级越高
         */
        private int priority;
        /**
         * 是否准备show（在show之后非用户自己手动关掉弹窗（调dismiss或触摸弹窗外部）
         * 接着show了一个优先级更高的Dialog，当该Dialog dismiss后可自动show剩下的
         * 优先级最高的Dialog
         * ）
         */
        private boolean prepareShow = true;

        public Builder dialog(Dialog dialog) {
            this.dialog = dialog;
            return this;
        }

        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder prepareShow(boolean prepareShow) {
            this.prepareShow = prepareShow;
            return this;
        }

        public DialogParam build() {
            return new DialogParam(this);
        }
    }
}
