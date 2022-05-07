package com.xj.demo

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

fun main() {
//    testInline()

}


class ProxyClass : InvocationHandler {

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        return ""
    }
}

fun testInline() {
    testClosure {
        "error"
    }
}

private inline fun testClosure(test: (String) -> String) {
    println("step 1")
    println(test("step test"))
    println("step 2")
}