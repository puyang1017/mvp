package com.android.puy.mvpkotlin

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.Log
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
        registerNetwork()
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
                context,
                0,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.intentSender)
        }
    }

    fun registerNetwork() {
        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            connMgr.requestNetwork(
                NetworkRequest.Builder().build(),
                object : NetworkCallback() {
                    /**
                     * 网络可用的回调
                     */
                    override fun onAvailable(network: Network) {
                        super.onAvailable(network)
                        Log.e("puy", "onAvailable 网络可用的回调")
                    }

                    /**
                     * 网络丢失的回调
                     */
                    override fun onLost(network: Network) {
                        super.onLost(network)
                        Log.e("puy", "onLost 网络丢失的回调")
                    }

                    /**
                     * 当建立网络连接时，回调连接的属性
                     */
                    override fun onLinkPropertiesChanged(
                        network: Network,
                        linkProperties: LinkProperties
                    ) {
                        super.onLinkPropertiesChanged(network, linkProperties)
                        Log.e("puy", "onLinkPropertiesChanged 当建立网络连接时，回调连接的属性")
                    }

                    /**
                     * 按照官方的字面意思是，当我们的网络的某个能力发生了变化回调，那么也就是说可能会回调多次
                     *
                     * 之后在仔细的研究
                     */
                    override fun onCapabilitiesChanged(
                        network: Network,
                        networkCapabilities: NetworkCapabilities
                    ) {
                        super.onCapabilitiesChanged(network, networkCapabilities)
                        Log.e("puy", "onCapabilitiesChanged ")
                    }

                    /**
                     * 在网络失去连接的时候回调，但是如果是一个生硬的断开，他可能不回调
                     */
                    override fun onLosing(network: Network, maxMsToLive: Int) {
                        super.onLosing(network, maxMsToLive)
                        Log.e("puy", "onLosing 在网络失去连接的时候回调")
                    }

                    /**
                     * 按照官方注释的解释，是指如果在超时时间内都没有找到可用的网络时进行回调
                     */
                    override fun onUnavailable() {
                        super.onUnavailable()
                        Log.e("puy", "onUnavailable 超时时间内都没有找到可用的网络")
                    }
                })
        }
    }
}


