package com.xj.demo.designmodel

interface Subject {
    fun request(): String
}

class RealSubject : Subject {
    override fun request(): String {
        return "this is RealSubject request"
    }
}

class Proxy(var subject: Subject) : Subject {
    override fun request(): String {
        return subject.request()
    }
}

fun main() {
    val realSubject = RealSubject()
    Proxy(realSubject).request()
}

