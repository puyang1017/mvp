package com.android.puy.puymvpjava.dialogmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * dialog 管理器
 * 支持优先级
 * Created by puy on 2021/4/22 13:36
 */
public class DialogManager {
    private List<DialogParam> mDialogs;
    private static DialogManager mDefaultInstance;

    private DialogManager() {
    }


    /**
     * 获取弹窗管理者
     */
    public static DialogManager getInstance() {
        if (mDefaultInstance == null) {
            synchronized (DialogManager.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new DialogManager();
                }
            }
        }
        return mDefaultInstance;
    }

    /**
     * 添加弹窗
     *
     * @param dialogParam 待添加的弹窗
     */
    public synchronized void add(DialogParam dialogParam) {
        if (dialogParam != null && dialogParam.getPriorityDialog() != null) {
            if (mDialogs == null) {
                mDialogs = new ArrayList<>();
            }
            dialogParam.getPriorityDialog().setOnShowListener(new OnShowListener() {
                @Override
                public void onShow() {
                    dialogParam.setShowing(true);
                    dialogParam.setPrepareShow(false);
                }
            });

            dialogParam.getPriorityDialog().setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(boolean isCrowdOut) {
                    dialogParam.setShowing(false);
                    if (isCrowdOut) {
                        dialogParam.setPrepareShow(true);
                    } else {
                        mDialogs.remove(dialogParam);
                        showNext();
                    }
                }
            });
            mDialogs.add(dialogParam);
        }
    }

    /**
     * 展示弹窗  此方法不会出现弹窗覆盖
     *
     * @param dialogParam 待展示的弹窗
     */
    public synchronized void show(DialogParam dialogParam) {
        if (dialogParam != null && dialogParam.getPriorityDialog() != null) {
            if (mDialogs == null) {
                if (dialogParam.getPriorityDialog().isCanShow()) {
                    dialogParam.getPriorityDialog().show();
                }
            } else {
                /*判断优先级及是否可展示*/
                maybeShow(dialogParam);
            }
        }
    }

    /**
     * 展示弹窗（优先级最高的Dialog）  此方法会出现弹窗覆盖
     */
    public synchronized void show() {
        DialogParam dialogParam = getMaxPriorityDialog();
        if (dialogParam != null) {
            PriorityDialog priorityDialog = dialogParam.getPriorityDialog();
            if (priorityDialog != null && priorityDialog.isCanShow()) {
                priorityDialog.show();
            }
        }
    }

    /**
     * 清除弹窗管理者
     */
    public synchronized void clear() {
        if (mDialogs != null) {
            for (int i = 0, size = mDialogs.size(); i < size; i++) {
                if (mDialogs.get(i) != null) {
                    mDialogs.get(i).setPrepareShow(false);
                }
            }
            for (int i = 0, size = mDialogs.size(); i < size; i++) {
                if (mDialogs.get(i) != null) {
                    PriorityDialog priorityDialog = mDialogs.get(i).getPriorityDialog();
                    if (priorityDialog != null) {
                        priorityDialog.dismiss(false);
                    }
                }
            }
            mDialogs.clear();
        }
    }

    /**
     * 清除弹窗管理者
     *
     * @param dismiss 是否同时dismiss掉弹窗管理者维护的弹窗
     */
    public synchronized void clear(boolean dismiss) {
        if (mDialogs != null) {
            for (int i = 0, size = mDialogs.size(); i < size; i++) {
                if (mDialogs.get(i) != null) {
                    mDialogs.get(i).setPrepareShow(false);
                }
            }
            if (dismiss) {
                for (int i = 0, size = mDialogs.size(); i < size; i++) {
                    if (mDialogs.get(i) != null) {
                        PriorityDialog priorityDialog = mDialogs.get(i).getPriorityDialog();
                        if (priorityDialog != null) {
                            priorityDialog.dismiss(false);
                        }
                    }
                }
            }
            mDialogs.clear();
        }
    }

    /**
     * 检测堆栈里是否有弹窗
     *
     * @param dialogParam
     * @return
     */
    public boolean checkDialog(DialogParam dialogParam) {
        if (dialogParam != null && dialogParam.getPriorityDialog() != null) {
            if (mDialogs == null) return false;
            return mDialogs.contains(dialogParam);
        } else {
            return false;
        }
    }

    /**
     * 检测堆栈里是否有弹窗
     *
     * @param priorityDialog
     * @return
     */
    public boolean checkDialog(PriorityDialog priorityDialog) {
        if (priorityDialog != null) {
            if (mDialogs == null) return false;
            for (DialogParam dialogParam : mDialogs) {
                if (dialogParam.getPriorityDialog().equals(priorityDialog)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 移除dialog
     *
     * @param dialogParam
     */
    public void removeDialog(DialogParam dialogParam) {
        if (dialogParam != null && dialogParam.getPriorityDialog() != null) {
            if (mDialogs == null) return;
            mDialogs.remove(dialogParam);
        }
    }

    /**
     * 移除dialog
     *
     * @param priorityDialog
     */
    public void removeDialog(PriorityDialog priorityDialog) {
        if (priorityDialog != null) {
            if (mDialogs == null) return;
            CopyOnWriteArrayList<DialogParam> mDialogsBuffer = new CopyOnWriteArrayList<>();
            mDialogsBuffer.addAll(mDialogs);
            for (DialogParam dialogParam : mDialogsBuffer) {
                if (dialogParam.getPriorityDialog().equals(priorityDialog)) {
                    mDialogsBuffer.remove(dialogParam);
                }
            }
            mDialogs.clear();
            mDialogs.addAll(mDialogsBuffer);
        }
    }

    /**
     * 展示下一个优先级最大的Dialog（非自行调用dismiss而是被优先级高的弹窗show后挤掉）
     */
    private synchronized void showNext() {
        DialogParam dialog = getMaxPriorityDialog();
        if (dialog != null) {
            if (dialog.isPrepareShow() && dialog.getPriorityDialog().isCanShow()) {
                dialog.getPriorityDialog().show();
            }
        }

    }

    /**
     * 展示弹窗（满足条件可展示）
     *
     * @param dialogParam 待展示的弹窗
     */
    private void maybeShow(DialogParam dialogParam) {
        if (dialogParam != null && dialogParam.getPriorityDialog() != null) {
            DialogParam topShowDialog = getShowingDialog();
            if (topShowDialog == null) {
                if (dialogParam.getPriorityDialog().isCanShow()) {
                    dialogParam.getPriorityDialog().show();
                }
            } else {
                /*获取优先级*/
                int priority = dialogParam.getPriority();
                if (priority >= topShowDialog.getPriority()) {
                    if (dialogParam.getPriorityDialog().isCanShow()) {
                        dialogParam.getPriorityDialog().show();
                        topShowDialog.getPriorityDialog().dismiss(true);
                        /*设置参数支持当前show关闭后自动show带该参数的优先级最高的弹窗*/
                        topShowDialog.setPrepareShow(true);
                    }
                }
            }
        }
    }

    /**
     * 获取当前栈中优先级最高的Dialog（优先级相同则返回后添加的弹窗）
     */
    private synchronized DialogParam getMaxPriorityDialog() {
        if (mDialogs != null) {
            int maxPriority = -1;
            int position = -1;
            for (int i = 0, size = mDialogs.size(); i < size; i++) {
                DialogParam dialog = mDialogs.get(i);
                if (i == 0) {
                    position = 0;
                    maxPriority = dialog.getPriority();
                } else {
                    if (dialog.getPriority() >= maxPriority) {
                        position = i;
                        maxPriority = dialog.getPriority();
                    }
                }
            }
            if (position != -1) {
                return mDialogs.get(position);
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * 获取当前处于show状态的弹窗
     */
    private synchronized DialogParam getShowingDialog() {
        if (mDialogs != null) {
            for (int i = 0, size = mDialogs.size(); i < size; i++) {
                DialogParam dialogParam = mDialogs.get(i);
                if (dialogParam != null && dialogParam.getPriorityDialog() != null && dialogParam.isShowing()) {
                    return dialogParam;
                }
            }
        }
        return null;
    }


}