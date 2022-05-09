package com.xj.demo.designmodel

class SingleInstanceKt private constructor() {

    var filed: String? = null

    companion object {
        private var instance: SingleInstanceKt? = null

        //懒汉式
        val instance1 by lazy { SingleInstanceKt() }

        //线程安全
        @Synchronized
        fun getInstance(): SingleInstanceKt? {
            if (instance == null) {
                instance = SingleInstanceKt()
            }
            return instance
        }

        //DCL
        val instance2 by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { SingleInstanceKt() }

        //静态内部类
        fun getInternalInstance() = InstanceHolder.instance
    }

    private object InstanceHolder {
        val instance = SingleInstanceKt()
    }

}

//饿汉式
object SingleInstance1 {
    var field: String? = null
}

//枚举
enum class SingleInstance2 {
    INSTANCE;

    private var value = 3

    open fun log() {
        println(value)
    }
}