package com.xj.demo.jvm

import java.lang.Exception
import java.util.concurrent.Callable
import java.util.concurrent.FutureTask
import java.util.concurrent.locks.ReentrantLock



internal class MThread : Thread() {
    override fun run() {
        super.run()
        println("thread start1, state = $state")
        Thread.sleep(5000)
//        join()
        println("thread start2, state = $state")

//        println("thread running, current thread is " + Thread.currentThread())
    }
}

class Task {
    @Synchronized
    fun fetchData() {
        Thread.sleep(5000)
    }
}

fun main() {
//    val thread = MThread()
//    thread.start()
//    Thread.sleep(1000)
//    //相当于调用thread.wait(),直到thread terminated之后thread.notifyAll()
//    thread.join()
//    println("after 1000, thread's state = ${thread.state}")

//    blockThread()
//    futureTask()
//    interruptThread()
    deadLock()
}

fun deadLock() {
    val a = Any()
    val b = Any()
//    val thread1 = Thread({
//        synchronized(a) {
//            println("thread1 get lock of a")
//            Thread.sleep(500)
//            println("thread1 after 500ms")
//
//            synchronized(b) {
//                println("thread1 get lock of b")
//            }
//            println("thread1 finish")
//        }
//    }, "thread1")
//    val thread2 = Thread({
//        synchronized(b) {
//            println("thread2 get lock of b")
//            Thread.sleep(500)
//            println("thread2 after 500ms")
//
//            synchronized(a) {
//                println("thread2 get lock of a")
//            }
//            println("thread2 finish")
//        }
//    }, "thread2")
//    thread1.start()
//    thread2.start()


}

fun interruptThread() {
    val thread = Thread {
        repeat(50000) {
            println("正在执行线程:$it")
        }
    }
    thread.start()
    Thread.sleep(20)
    println("=============interrupt==================")
}


fun blockThread() {
    val task = Task()
    val thread1 = Thread {
        task.fetchData()
    }

    val thread2 = Thread {
        task.fetchData()
    }

    thread1.start()
    thread2.start()
    //过1000ms获取两个线程的状态
    Thread.sleep(1000)
    println("thread1 state = ${thread1.state}")
    println("thread2 state = ${thread2.state}")
}

fun futureTask() {
    val callable = Callable {
        println("thread = ${Thread.currentThread()}")
        1 + 2
    }
    val future: FutureTask<Int> = FutureTask<Int>(callable)
    Thread(future).start()
    println("thread is finish , result = ${future.get()}")
}

