package com.xj.demo.mvvn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xj.demo.Student

class DataViewModel : ViewModel() {

    private val _studentLiveData = MutableLiveData<Student>()
    var studentLiveData: LiveData<Student> = _studentLiveData
        private set

    fun updatePerson(person: Student) {
        _studentLiveData.value = person
    }
}