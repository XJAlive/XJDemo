package com.xj.demo.mvvm

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.xj.demo.Student

class DataViewModel : ViewModel() {

    private val _studentLiveData = MutableLiveData<Student>()
    var studentLiveData: LiveData<Student> = _studentLiveData
        private set

    fun updatePerson(person: Student) {
        _studentLiveData.value = person
    }
}

//一个全局的ViewModel
@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.applicationViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }
    return ViewModelLazy(VM::class, {
        application.viewModelStore
    }, factoryPromise)
}

val Application.viewModelStore: ViewModelStore by lazy {
    ViewModelStore()
}