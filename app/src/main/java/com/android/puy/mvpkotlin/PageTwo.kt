package com.android.puy.mvpkotlin

import android.os.Bundle
import android.os.Handler
import com.android.puy.puymvpjava.mvp.XLazyFragmention
import kotlinx.android.synthetic.main.activity_page.*
import persents.Pmain
import views.IVmain

/**
 * Created by puy on 2019/9/5 18:37
 */
class PageTwo : XLazyFragmention<Pmain>(), IVmain {
    companion object {
        fun newInstance(): PageTwo {
            return PageTwo()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
            text.text = "7777"
        text.postDelayed({   text.text = "88888"},1000)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_page
    }

    override fun newP(): Pmain {
        return Pmain()
    }
}
