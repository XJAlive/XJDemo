package com.xj.demo.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StateFlowViewModel : ViewModel() {
    private val select = MutableStateFlow("defaultValue")
    private val repository by lazy { Repository() }
    private val sharedFlow = MutableSharedFlow<Int>(5, 3, BufferOverflow.DROP_OLDEST)

    //根据当前的品牌名称暂时具体页面
    val shoe: Flow<String> = select.flatMapLatest {
        when (it) {
            "adidas" -> flowOf("adidas shoe")
            "nike" -> flowOf("nike shoe")
            else -> flowOf("all shoe")
        }
    }

    /**
     * V -> VM
     */
    fun updateSelect(brand: String) {
        select.value = brand
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                val result = repository.loadDataFromRepo()
                select.value = result
            } catch (e: Exception) {
                select.value = "error"
                e.printStackTrace()
            }
        }
    }

}