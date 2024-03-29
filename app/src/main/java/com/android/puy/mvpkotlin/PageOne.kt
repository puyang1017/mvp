package com.android.puy.mvpkotlin

import android.os.Bundle
import com.android.puy.puymvpjava.mvp.XFragmentation
import com.android.puy.puymvpjava.router.Router
import kotlinx.android.synthetic.main.activity_page.*
import persents.Pmain
import views.IVmain

/**
 * Created by puy on 2019/9/5 18:37
 */
class PageOne : XFragmentation<Pmain>(), IVmain {
    companion object {
        fun newInstance(): PageOne {
            return PageOne()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        text.setOnClickListener {
            Router.newIntent(context).to(TestActivity::class.java).launch()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_page
    }

    override fun newP(): Pmain {
        return Pmain()
    }
}
