package com.android.puy.mvpkotlin

/**
 * Created by puy on 2019/9/5 19:07
 */
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import java.util.ArrayList

class Fragment_pager//通过构造获取list集合
    (fm: FragmentManager, internal var list: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {
    //设置每一个的内容
    override fun getItem(arg0: Int): Fragment {
        // TODO Auto-generated method stub
        return list[arg0]
    }

    //设置有多少内容
    override fun getCount(): Int {
        // TODO Auto-generated method stub
        return list.size
    }

}
