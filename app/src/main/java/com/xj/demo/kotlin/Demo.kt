package com.xj.demo.kotlin

import android.util.Log
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * 委托
 */
class User {

    private val password: String by lazy(fun(): String {
        return "admin"
    })

    /**
     * 定义 Base 接口
     *
     */
    interface Base {
        fun say()
    }

    /**
     * 定义 Base 接口的实现类，并实现 say() 方法
     */
    class BaseImpl : Base {
        override fun say() {
            println("BaseImpl say()")
        }
    }

    /**
     * 定义 BaseProxy 类，并实现了 Base 接口，
     * 关键字 by 将接口 Base 中所有的方法都委托给 base 对象，这样 BaseProxy 类就不需要去实现接口 Base 中的方法了，
     * 简化了实现接口时要实现其中的方法。
     */
    class BaseProxy(base: Base) : Base by base

    var name: String by Delegates.observable("hello") { kProperty: KProperty<*>, oldName: String, newName: String ->
        Log.e("xj", "${kProperty.name}---${oldName}--${newName}")
    }
}
