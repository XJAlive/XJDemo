package com.xj.demo.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xj.demo.mvvm.Repository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 定义状态和事件接口,事件与页面状态分流
 */
interface UIState
interface UISingleEvent

/**
 * 保存页面状态和事件
 */
interface Container<STATE : UIState, EVENT : UISingleEvent> {
    val stateFlow: StateFlow<STATE>
    val eventFlow: Flow<EVENT>
}

class RealContainer<STATE : UIState, EVENT : UISingleEvent>(var initState: STATE) {

    private val _stateFlow = MutableStateFlow<STATE>(initState)
    private val _eventFlow = MutableSharedFlow<EVENT>()

    var stateFlow: StateFlow<UIState> = _stateFlow
    var eventFlow: Flow<UISingleEvent> = _eventFlow

    fun updateState(action: STATE.() -> STATE) {
        _stateFlow.update {
            action.invoke(_stateFlow.value)
        }
    }

    fun sendEvent(event: EVENT) {
        //CoroutineScope.launch{}中执行?
        _eventFlow.tryEmit(event)
    }

}

/**
 * VM层,处理请求
 */
class PageViewModel : ViewModel() {

    private val repository = Repository()

    /**
     * 保存当前页面所有ui状态
     */
    data class PageUIState(
        var list: List<String>? = null,
        var isRefreshing: Boolean = false,
        var currentPage: Int
    ) : UIState

    /**
     * 发送的事件
     */
    sealed class PageSingleEvent : UISingleEvent {
        class ToastEvent(msg: String) : PageSingleEvent()
    }

    /**
     * 请求数据
     */
    fun fetchData() {
        viewModelScope.launch {
            val result = repository.loadDataFromRepo()
            container.updateState {
                //将获取的数据更新至VM
                copy(
                    list = listOf(result)
                )
            }

            //弹个Toast
            container.sendEvent(PageSingleEvent.ToastEvent(result))
        }
    }

    private val _container =
        RealContainer<PageUIState, PageSingleEvent>(PageUIState(emptyList(), false, 0))
    var container: RealContainer<PageUIState, PageSingleEvent> = _container
}



