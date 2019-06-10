package com.android.puy.mvpkotlin

import android.os.Bundle
import com.android.puy.puymvpjava.mvp.XActivity
import kotlinx.android.synthetic.main.activity_main.*
import persents.Pmain

class MainActivity : XActivity<Pmain>() {
    override fun getLayoutId() = R.layout.activity_main

    override fun newP() = Pmain()

    override fun initData(savedInstanceState: Bundle?) {
        text.text = "demo"
    }

}
