package com.xj.demo

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField

//data class Student(
//    var name: String? = null,
//    var age: String? = null
//) {
//    @SuppressLint("ShowToast")
//    fun fetchFromRepo(view: View) {
//        Toast.makeText(view.context, "更改name属性值", Toast.LENGTH_SHORT).show()
//        name = "xiaoming"
//    }
//}


class Student() : BaseObservable() {
    constructor(name: String, age: String) : this() {
        this.name = name
        this.age = age
    }

    var name: String? = null
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }
//    var name : ObservableField<String> ?= null
    var age: String? = null

    @SuppressLint("ShowToast")
    fun fetchFromRepo(view: View) {
        Toast.makeText(view.context, "更改name属性值", Toast.LENGTH_SHORT).show()
        name = "xiaoming"
    }
}

data class BizResult<T>(var data: T? = null, val errorCode: Int, val errorMsg: String)

data class BannerItem(
    val desc: String? = null,
    val id: Int? = null,
    val imagePath: String? = null,
    val isVisible: Int? = null,
    val order: Int? = null,
    val title: String? = null,
    val type: Int? = null,
    val url: String? = null,
)