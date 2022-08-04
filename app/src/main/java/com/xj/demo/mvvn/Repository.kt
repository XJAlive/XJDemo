package com.xj.demo.mvvn

import java.util.*

class Repository {

    suspend fun loadDataFromRepo(): String {
        if (Random().nextBoolean()) {
            throw Exception("test内容")
        }
        return "仓库返回内容"
    }
}