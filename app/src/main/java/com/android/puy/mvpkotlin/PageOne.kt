package com.android.puy.mvpkotlin

import android.os.Bundle
import com.android.puy.puymvpjava.mvp.XLazyFragmention
import persents.Pmain
import views.IVmain

/**
 * Created by puy on 2019/9/5 18:37
 */
class PageOne : XLazyFragmention<Pmain>(), IVmain {
    companion object {
        fun newInstance(): PageOne {
            return PageOne()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_page
    }

    override fun newP(): Pmain {
        return Pmain()
    }
}
