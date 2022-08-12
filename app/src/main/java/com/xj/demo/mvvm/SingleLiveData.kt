package com.xj.demo.mvvm

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 替换observer
 */
class SingleLiveData<T> : MutableLiveData<T>() {
    var mPending: AtomicBoolean = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        //替换Observer，只执行一次OnChange
        val singleObserver = Observer<T> { t ->
            if (mPending.compareAndSet(false, true)) {
                observer.onChanged(t)
            }
        }
        super.observe(owner, singleObserver)
    }

}

/**
 * 事件包装器
 */
class Event<out T>(private val value: T) {
    var hasHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasHandled) {
            null
        } else {
            hasHandled = true
            value
        }
    }
}

fun addObserver(lifecycleOwner: LifecycleOwner) {
    val liveData = MutableLiveData<Event<String>>()
    liveData.postValue(Event("version=1"))
    liveData.observe(lifecycleOwner) {
        it.getContentIfNotHandled()?.run {
            //更新页面
        }
    }
}


