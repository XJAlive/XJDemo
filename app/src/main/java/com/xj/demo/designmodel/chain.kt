package com.xj.demo.designmodel

abstract class Interceptor {

    var next: Interceptor? = null

    fun chain(interceptor: Interceptor) {
        this.next = interceptor
    }

    abstract fun intercept(): String
}

class ConnectionInterceptor : Interceptor() {
    override fun intercept(): String {
        TODO("Not yet implemented")
    }

}

class CacheInterceptor : Interceptor() {
    override fun intercept(): String {
        TODO("Not yet implemented")
    }

}

fun main() {
    val interceptor1 = ConnectionInterceptor()
    val interceptor2 = CacheInterceptor()
    interceptor1.chain(interceptor2)
    interceptor1.intercept()
}