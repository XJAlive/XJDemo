package com.xj.demo

class Data {
    val name: String? = null
    val age: String? = null
}

data class  BizResult<T>(var data: T ?= null, val errorCode:Int, val errorMsg:String)

data class BannerItem(
    val desc: String? = null,
    val id: Int? = null,
    val imagePath: String? = null,
    val isVisible: Int? = null,
    val order: Int? = null,
    val title: String? = null,
    val type: Int? = null,
    val url: String? = null
)