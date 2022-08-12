package com.xj.demo.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xj.demo.BizResult
import kotlinx.coroutines.launch

class LiveDataViewModel : ViewModel() {

    private val _reportEvent = MutableLiveData<BizResult<String>>()
    val reportEvent: LiveData<BizResult<String>> get() = _reportEvent

    private fun updateReportEvent(result: BizResult<String>) {
        _reportEvent.postValue(result)
    }

    /**
     * 更新数据
     */
    fun fetchData() {
        //清除数据时会调用CoroutineContext.cancel()终止协程
        viewModelScope.launch {
            try {
                val result = Repository().loadDataFromRepo()
                updateReportEvent(BizResult(result, 0, ""))
            } catch (e: Exception) {
                updateReportEvent(BizResult("", -1000, "获取数据异常"))
            }
        }
    }

}