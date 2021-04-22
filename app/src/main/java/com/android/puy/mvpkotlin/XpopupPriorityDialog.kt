//package com.android.puy.mvpkotlin
//
//import com.android.puy.puymvpjava.dialogmanager.OnDismissListener
//import com.android.puy.puymvpjava.dialogmanager.OnShowListener
//import com.android.puy.puymvpjava.dialogmanager.PriorityDialog
//import com.lxj.xpopup.XPopup
//import com.lxj.xpopup.core.BasePopupView
//import com.lxj.xpopup.interfaces.XPopupCallback
//
///**
// * Created by puy on 2021/4/22 15:40
// */
//class XpopupPriorityDialog(val xPopup: XPopup.Builder) : PriorityDialog {
//    var mBasepopupView: BasePopupView? = null
//    var mDismissListener: OnDismissListener? = null
//    var mShowListener: OnShowListener? = null
//
//    init {
//        xPopup.setPopupCallback(object :XPopupCallback{
//            override fun onBackPressed(popupView: BasePopupView?): Boolean {
//                return false
//            }
//
//            override fun onDrag(popupView: BasePopupView?, value: Int, percent: Float, upOrLeft: Boolean) {
//            }
//
//            override fun onDismiss(popupView: BasePopupView?) {
//                mDismissListener?.onDismiss(isCrowdOut)
//            }
//
//            override fun onKeyBoardStateChanged(popupView: BasePopupView?, height: Int) {
//            }
//
//            override fun beforeShow(popupView: BasePopupView?) {
//            }
//
//            override fun onCreated(popupView: BasePopupView?) {
//            }
//
//            override fun beforeDismiss(popupView: BasePopupView?) {
//            }
//
//            override fun onShow(popupView: BasePopupView?) {
//                mShowListener?.onShow()
//            }
//        })
//    }
//
//    fun asCustom(popupView: BasePopupView): BasePopupView?{
//        mBasepopupView = xPopup.asCustom(popupView)
//        return mBasepopupView
//    }
//
//    /**
//     * 是否被挤出（每个实现DialogManager.Dialog的窗口类都需要新建该变量）
//     */
//    private var isCrowdOut = false
//    override fun dismiss(boolean: Boolean) {
//        isCrowdOut = boolean
//    }
//
//    override fun show() {
//        mBasepopupView?.show()
//    }
//
//    override fun setOnDismissListener(listener: OnDismissListener?) {
//        mDismissListener = listener
//    }
//
//    override fun setOnShowListener(listener: OnShowListener?) {
//        mShowListener = listener
//    }
//
//    override fun isCanShow(): Boolean {
//        return true
//    }
//}