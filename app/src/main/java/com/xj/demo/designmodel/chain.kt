package com.xj.demo.designmodel

/**
 * 责任链模式
 */
abstract class Interceptor {

    var next: Interceptor? = null

    fun chain(interceptor: Interceptor) {
        this.next = interceptor
    }

    abstract fun intercept(param: String): String
}

class ConnectionInterceptor : Interceptor() {
    override fun intercept(param: String): String {
        next?.run {
            return intercept("$param -> connectionInterceptor handle ")
        }
        return "$param -> connectionInterceptor handle "
    }
}

class CacheInterceptor : Interceptor() {
    override fun intercept(param: String): String {
        next?.run {
            return intercept("$param -> cacheInterceptor handle ")
        }
        return "$param -> cacheInterceptor handle "
    }
}

class BridgeInterceptor : Interceptor() {
    override fun intercept(param: String): String {
        next?.run {
            return intercept("$param -> BridgeInterceptor handle ")
        }
        return "$param -> BridgeInterceptor handle "
    }
}

fun main() {
    val interceptor1 = ConnectionInterceptor()
    val interceptor2 = CacheInterceptor()
    val interceptor3 = BridgeInterceptor()
    interceptor1.chain(interceptor2)
    interceptor2.chain(interceptor3)
    println(interceptor1.intercept("start"))
}