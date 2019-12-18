package model

import com.android.puy.puyupdateapp.R
import util.GlobalContextProvider

/**
 * desc: UpdateInfo
 */
internal data class UpdateInfo(
    // 更新标题
    var updateTitle: CharSequence = GlobalContextProvider.getGlobalContext().getString(R.string.update_title),
    // 更新内容
    var updateContent: CharSequence = GlobalContextProvider.getGlobalContext().getString(R.string.update_content),
    // apk 下载地址
    var apkUrl: String = "",
    // 更新配置
    var config: UpdateConfig = UpdateConfig(),
    // ui配置
    var uiConfig: UiConfig = UiConfig()
)