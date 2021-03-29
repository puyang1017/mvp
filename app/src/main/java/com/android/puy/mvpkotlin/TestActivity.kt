package com.android.puy.mvpkotlin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.puy.puymvpjava.event.BusProvider
import com.android.puy.puymvpjava.mvp.XActivity
import models.LoginSuccessEvent
import persents.Pmain

class TestActivity : XActivity<Pmain>() {

    override fun getLayoutId()= R.layout.activity_test

    override fun newP() = Pmain()

    override fun useRxBus(): Boolean {
        return true
    }

    @SuppressLint("CheckResult")
    override fun initData(savedInstanceState: Bundle?) {
        BusProvider.getBus().toFlowable(context, LoginSuccessEvent::class.java).subscribe {//登录成功
        }
    }
}