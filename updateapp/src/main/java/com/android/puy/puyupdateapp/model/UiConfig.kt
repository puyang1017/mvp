package model

import com.android.puy.puyupdateapp.R
import constacne.UiType
import util.GlobalContextProvider

/**
 * desc: UiConfig UI 配置
 */
data class UiConfig(
    // ui类型，默认简洁版
    var uiType: String = UiType.SIMPLE,
    // 自定义UI 布局id
    var customLayoutId: Int? = null,
    // 更新弹窗中的logo
    var updateLogoImgRes: Int? = null,
    // 标题相关设置
    var titleTextSize: Float? = null,
    var titleTextColor: Int? = null,
    // 更新内容相关设置
    var contentTextSize: Float? = null,
    var contentTextColor: Int? = null,
    // 更新按钮相关设置
    var updateBtnBgColor: Int? = null,
    var updateBtnBgRes: Int? = null,
    var updateBtnTextColor: Int? = null,
    var updateBtnTextSize: Float? = null,
    var updateBtnText: CharSequence = GlobalContextProvider.getGlobalContext().getString(R.string.update_now),
    // 取消按钮相关设置
    var cancelBtnBgColor: Int? = null,
    var cancelBtnBgRes: Int? = null,
    var cancelBtnTextColor: Int? = null,
    var cancelBtnTextSize: Float? = null,
    var cancelBtnText: CharSequence = GlobalContextProvider.getGlobalContext().getString(R.string.update_cancel),

    // 开始下载时的Toast提示文字
    var downloadingToastText: CharSequence = GlobalContextProvider.getGlobalContext().getString(R.string.toast_download_apk),
    // 下载中 下载按钮以及通知栏标题前缀，进度自动拼接在后面
    var downloadingBtnText: CharSequence = GlobalContextProvider.getGlobalContext().getString(R.string.downloading),
    // 下载出错时，下载按钮及通知栏标题
    var downloadFailText: CharSequence = GlobalContextProvider.getGlobalContext().getString(R.string.download_fail)
)