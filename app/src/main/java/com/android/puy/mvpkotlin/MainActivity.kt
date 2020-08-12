package com.android.puy.mvpkotlin

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Build.*
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.android.puy.puymvpjava.mvp.XFragmentationActivity
import kotlinx.android.synthetic.main.activity_main.*
import persents.Pmain
import views.IVmain


class MainActivity : XFragmentationActivity<Pmain>(), IVmain {
    override fun getLayoutId() = R.layout.activity_main

    override fun newP() = Pmain()

    @RequiresApi(VERSION_CODES.O)
    override fun initData(savedInstanceState: Bundle?) {
        val list = ArrayList<Fragment>()
        list.add(PageOne())
        list.add(PageTwo())
        ViewPager.adapter = Fragment_pager(supportFragmentManager, list)
//        videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.video))
//        videoView.start()
//        videoView.setOnCompletionListener {
//            videoView.start()
//        }
        button.setOnClickListener { addShortCut(context) }

    }
    @RequiresApi(api = VERSION_CODES.O)
    fun addShortCut(context: Context) {
        val shortcutManager =
            context.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager
        if (shortcutManager.isRequestPinShortcutSupported) {
            val shortcutInfoIntent = Intent(context, MainActivity::class.java)
            shortcutInfoIntent.action = Intent.ACTION_VIEW //action必须设置，不然报错
            val info = ShortcutInfo.Builder(context, "The only id")
                .setIcon(Icon.createWithResource(context, R.drawable.umeng_socialize_wechat))
                .setShortLabel("Short Label")
                .setIntent(shortcutInfoIntent)
                .build()

            //当添加快捷方式的确认弹框弹出来时，将被回调
            val shortcutCallbackIntent = PendingIntent.getBroadcast(
                context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT
            )
            shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.intentSender)
        }
    }

}


