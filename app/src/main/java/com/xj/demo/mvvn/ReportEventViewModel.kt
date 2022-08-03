package com.xj.demo.mvvn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReportEventViewModel : ViewModel() {

    private val _reportEvent = MutableLiveData<String>()
    val reportEvent: LiveData<String> get() = _reportEvent

    private fun updateReportEvent(event: String) {
        _reportEvent.value = event
    }

    /**
     * 更新数据
     */
    fun fetchData() {
        val result = Repository().loadDataFromRepo()
        updateReportEvent(result)
    }


}