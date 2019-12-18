package extension

import android.view.View

/**
 * desc: View 相关扩展
 */
fun View.visibleOrGone(show: Boolean){
    if (show){
        this.visibility = View.VISIBLE
    }else{
        this.visibility = View.GONE
    }
}