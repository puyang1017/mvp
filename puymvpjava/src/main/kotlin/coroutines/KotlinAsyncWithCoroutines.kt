package coroutines

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import kotlinx.coroutines.*

class KotlinAsyncWithCoroutines {
    companion object{
        private var job: Job? = null;

        inline fun <reified T> T.logd(message: () -> String) = Log.d(T::class.simpleName, message())

        inline fun <reified T> T.loge(error: Throwable, message: () -> String) =
            Log.d(T::class.simpleName, message(), error)

        internal class CoroutineLifecycleListener(private val deferred: Deferred<*>) : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun cancelCoroutine() {
                if (!deferred.isCancelled) {
                    deferred.cancel()
                }
            }
        }
        @JvmStatic
        fun <T> LifecycleOwner.loadAsync(loader: suspend () -> T): Deferred<T> {
            val deferred = GlobalScope.async(Dispatchers.IO, start = CoroutineStart.LAZY) {
                loader()
            }
            lifecycle.addObserver(CoroutineLifecycleListener(deferred))
            return deferred
        }

        @JvmStatic
        infix fun <T> Deferred<T>.ui(block: suspend (T) -> Unit): Job {
            return GlobalScope.launch(Dispatchers.Main) {
                try {
                    block(this@ui.await())
                } catch (e: Exception) {
                    loge(e) { "Exception in ui()!" }
                    throw e
                }
            }
        }
    }


}
