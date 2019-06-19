package com.android.puy.mvpkotlin

import android.os.Bundle
import android.text.TextUtils
import com.android.puy.puymvpjava.mvp.XActivity
import kotlinx.android.synthetic.main.activity_main.*
import persents.Pmain
import views.IVmain

class MainActivity : XActivity<Pmain>(), IVmain {
    override fun getLayoutId() = R.layout.activity_main

    override fun newP() = Pmain()

    override fun initData(savedInstanceState: Bundle?) {
        my_text_one.ellipsize = TextUtils.TruncateAt.END
    }

}
