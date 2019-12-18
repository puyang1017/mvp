package com.android.puy.mvpkotlin

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.puy.puymvpjava.mvp.XFragmentationActivity
import constacne.DownLoadBy
import constacne.UiType
import kotlinx.android.synthetic.main.activity_main.*
import listener.OnBtnClickListener
import model.UiConfig
import model.UpdateConfig
import persents.Pmain
import update.UpdateAppUtils
import views.IVmain

class MainActivity : XFragmentationActivity<Pmain>(), IVmain {
    override fun getLayoutId() = R.layout.activity_main

    override fun newP() = Pmain()

    override fun initData(savedInstanceState: Bundle?) {
        val list = ArrayList<Fragment>()
        list.add(PageOne())
        list.add(PageTwo())
        ViewPager.adapter = Fragment_pager(supportFragmentManager, list)

        UpdateAppUtils
            .getInstance()
            .apkUrl("https://www.biubiu001.com/apk-download?platform=pc")
            .updateTitle("2222")
            .updateContent("23sdfsdjkvjks\ndvjksvksnlkvn")
            .updateConfig(UpdateConfig().apply {
                downloadBy = DownLoadBy.APP
                isShowNotification = true
                serverVersionName = "2.0.0"
            })
            .uiConfig(UiConfig(uiType = UiType.PLENTIFUL))

            // 设置 取消 按钮点击事件
            .setCancelBtnClickListener(object : OnBtnClickListener {
                override fun onClick(): Boolean {
                    Toast.makeText(this@MainActivity, "cancel btn click", Toast.LENGTH_SHORT).show()
                    return false // 事件是否消费，是否需要传递下去。false-会执行原有点击逻辑，true-只执行本次设置的点击逻辑
                }
            })

            // 设置 立即更新 按钮点击事件
            .setUpdateBtnClickListener(object : OnBtnClickListener {
                override fun onClick(): Boolean {
                    Toast.makeText(this@MainActivity, "update btn click", Toast.LENGTH_SHORT).show()
                    return false // 事件是否消费，是否需要传递下去。false-会执行原有点击逻辑，true-只执行本次设置的点击逻辑
                }
            })

            .update()
    }
}
