package com.android.puy.mvpkotlin

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.android.puy.puymvpjava.imageloader.*
import com.android.puy.puymvpjava.mvp.XFragmentationActivity
import com.bumptech.glide.load.ImageHeaderParser
import kotlinx.android.synthetic.main.activity_main.*
import persents.Pmain
import views.IVmain

class MainActivity : XFragmentationActivity<Pmain>(), IVmain {
    override fun getLayoutId() = R.layout.activity_main

    override fun newP() = Pmain()

    override fun initData(savedInstanceState: Bundle?) {
        val list = ArrayList<Fragment>()
        list.add(PageOne())
        list.add(PageTwo())
        ViewPager.adapter = Fragment_pager(supportFragmentManager, list)
    }
}
