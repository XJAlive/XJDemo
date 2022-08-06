package com.xj.demo.mvvn

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.xj.demo.BizResult

class LiveDataWrapper<T>(var liveData: LiveData<BizResult<T>>) {
    var onSuccess: ((T?) -> Unit)? = null
    var onError: ((Int, String) -> Unit)? = null

    fun onSuccess(onSuccess: (T?) -> Unit): LiveDataWrapper<T> {
        this.onSuccess = onSuccess
        return this
    }

    fun onError(onError: (Int, String) -> Unit): LiveDataWrapper<T> {
        this.onError = onError
        return this
    }
}

fun <T> LiveData<BizResult<T>>.wrapLiveData(): LiveDataWrapper<T> {
    return LiveDataWrapper(this)
}

fun <T> LiveDataWrapper<T>.observe(lifecycleOwner: LifecycleOwner) {
    liveData.observe(lifecycleOwner) {
        when (it.errorCode) {
            0 -> onSuccess?.invoke(it.data)
            else -> onError?.invoke(it.errorCode, it.errorMsg)
        }
    }
}


