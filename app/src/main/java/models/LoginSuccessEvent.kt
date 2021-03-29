package models

import com.android.puy.puymvpjava.event.IBus

/**
 * Created by puy on 2021/3/29 17:41
 */
class LoginSuccessEvent :IBus.IEvent{
    override fun getTag()= 1
}