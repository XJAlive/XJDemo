package com.xj.demo.coroutine

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.*
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.system.measureTimeMillis

//fun main() {
//    val job = GlobalScope.launch {
//        println("方法开始执行时间：${System.currentTimeMillis()}")
//
////        println("${Thread.currentThread().name}")
//
//        val id = getIdAsync()
//        val image = getAvatarAsync()
//        id.await() + image.await()
//
//        println("方法结束执行时间：${System.currentTimeMillis()}" +"  ${id.await()} ${image.await()}")
//    }
//    println("协程方法体外部：${System.currentTimeMillis()}")
//
//    Thread.sleep(4000)
//
//}

suspend fun main() {
//    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
//        log("使用内部handler处理异常")
//    }
//    val deferred = GlobalScope.async<Int>(exceptionHandler) {
//        throw ArithmeticException()
//    }
//    try {
//        deferred.await()
//        log(1)
//    } catch (e: Exception) {
//        log("2. $e")
//    }

//    val deferred = GlobalScope.async() {
//        delay(1000)
//        log("2 + ${System.currentTimeMillis()}")
//    }
//    try {
//        deferred.await()
//        deferred.join()
//        log("1 + ${System.currentTimeMillis()}")
//    } catch (e: Exception) {
//        log("报错了 $e")
//    }

//    log(1)
//    log(returnSuspended())
//    log(2)
//    delay(1000)
//    log(3)
//    log(returnImmediately())
//    log(4)

    val flow = flowOf(1, 2, 3).onEach { delay(50) }
    val flow2 = flowOf("a", "b", "c", "d").onEach { delay(150) }
    val startTime = System.currentTimeMillis() // 记录开始的时间
    flow.zip(flow2) { i, s -> i.toString() + s }.collect {
        // Will print "1a 2b 3c"
        log("$it 耗时 ${System.currentTimeMillis() - startTime} ms")
    }

    flow {
        emit(1)
    }.shareIn(MainScope(), SharingStarted.Lazily).collect {

    }


    //launch启动, 不传入CoroutineExceptionHandler, 使用join(),抛出异常,无法try-catch,交由父协程Handler处理
    //launch启动，传入CoroutineExceptionHandler,   使用join(),不抛出异常，内部Handler处理
    //async启动， 不传入CoroutineExceptionHandler, 使用await(),抛出异常，可以try-catch
    //async启动， 传入CoroutineExceptionHandler，  使用await(),抛出异常，可以try-catch
    //async启动， 不传入CoroutineExceptionHandler  使用join(), 忽略异常，不报错
    //async启动， 传入CoroutineExceptionHandler，  使用join(), 忽略异常，不报错

}

suspend fun hello() = suspendCancellableCoroutine<Int> { continuation ->
    log(1)
    thread {
        Thread.sleep(1000)
        log(2)
        continuation.resume(1024)
    }
    log(3)
    COROUTINE_SUSPENDED
}

suspend fun returnSuspended() = suspendCancellableCoroutine<String> { continuation ->
    thread {
        Thread.sleep(1000)
        continuation.resume("Return suspended.")
    }
    COROUTINE_SUSPENDED
}

suspend fun returnImmediately() = suspendCancellableCoroutine<String> {
    it.resume("Return immediately.")
}


suspend fun getUserCoroutine() = suspendCoroutine<String> {
    it.resume("xj")
}


//fun main() = runBlocking {
//    val startTime = System.currentTimeMillis()
//    val job = launch(Dispatchers.Default) {
//        var nextPrintTime = startTime
//        var i = 0
//
//        withTimeout(1300L) {
//            while (isActive) { // computation loop, just wastes CPU
//                // print a message twice a second
//                if (System.currentTimeMillis() >= nextPrintTime) {
//                    println("job: I'm sleeping ${i++} ...")
//                    nextPrintTime += 500L
//                }
//            }
//        }
//
//    }
//
//    job.cancelAndJoin()


//    val result = getIdAsync().await() + getAvatarAsync().await()
//    print("获取多个结果合并$result ,执行时间${System.currentTimeMillis()}, thread:${Thread.currentThread().name}")

//    println(get(""))
//
//    println("回到主函数thread:${Thread.currentThread().name}")

//    val time = measureTimeMillis {
//        foo().collect {
//            println("flow done")
//        }
//    }
//    println("耗时:$time")
//
//
//}

suspend fun get(url: String): String {

    return withContext(Dispatchers.IO) {
        println(" get()所在线程,thread:${Thread.currentThread().name}")
        "123"
    }
}


suspend fun CoroutineScope.getIdAsync(): Deferred<String> {
    return async(Dispatchers.IO) {
        println("getId()执行时间：${System.currentTimeMillis()} , thread:${Thread.currentThread().name}")
        delay(1000)
        "123"
    }
}


suspend fun CoroutineScope.getAvatarAsync(): Deferred<String> {
    return async(Dispatchers.IO, start = CoroutineStart.LAZY) {
        println("getAvatar()执行时间：${System.currentTimeMillis()}, thread:${Thread.currentThread().name}")
        delay(2000)
        "image"
    }

}

suspend fun getAd() = suspendCoroutine<Boolean> {
    it.resume(false)
}

fun log(msg: String) =
    println("[${Thread.currentThread().name}, ${System.currentTimeMillis()}] $msg")

fun log(msg: Int) = println("[${Thread.currentThread().name} , ${System.currentTimeMillis()}] $msg")


//fun main() = runBlocking<Unit> {

//    val time = measureTimeMillis {
//        getUser()
//    }
//    println("Completed in $time ms")


//    launch(Dispatchers.Default) {
//        println("挂起函数前:" + Thread.currentThread().name)
//        delay(3000L)
//        println("挂起函数后:" + Thread.currentThread().name)
//    }

//}


//suspend fun foo(): List<Int> {
//    //12600-6000-1200-2000
//    delay(1000)
//    return listOf(1, 2, 3)
//}
//
//fun foo1(): Flow<Int> = flow {
//    for (i in 1..Int.MAX_VALUE) {
////        delay(100)
//        println("Emitting $i")
//        emit(i)
//    }
//}.flowOn(Dispatchers.Main)

//fun main() = runBlocking<Unit> {
//    // Timeout after 250ms
//    (1..3).asFlow().onEach { delay(100) } // a number every 100 ms
//        .flatMapConcat { requestFlow(it) }
//        .collect { value ->
//            // collect and print
//            println("$value")
//        }
//
////        foo1().collect { value -> println(value) }
//
//    println("Done")
//}
//
//fun requestFlow(request: Int): Flow<Int> = flow {
//    emit(request + 100)
//    delay(50)
//    emit(request + 200)
//}


fun foo(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(1000)
        println("Emitting $i")
        emit(i) // emit next value
    }
}

//fun main() = runBlocking<Unit> {
//    foo()
//        .onEach {
//            check(it > 2) {
//                println("上游出现报错")
//            }
//        }
//        .onCompletion {
//            print("所有流完成")
//        }.catch { e ->
//            println(e)
//        }
//        .collect { value ->
//            println(value)
//        }
//}

suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope { // scope for coroutines
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}

//sampleStart
var counter = 0

//fun main() = runBlocking {
//    withContext(Dispatchers.Default) {
//        val mutex =  Mutex()
//        massiveRun {
//            mutex.withLock {
//                counter ++
//            }
//        }
//    }
//    println("Counter = $counter")
//}


fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
    var x = start
    while (true) send(x++) // infinite stream of integers from start
}


suspend fun getUser() = coroutineScope {
    val one = async(Dispatchers.IO + CoroutineName("xj")) { doSomethingUsefulOne() }
    val two = async(Dispatchers.Unconfined) {
        doSomethingUsefulTwo()
    }
    one.await() + two.await()
}

suspend fun doSomethingUsefulOne(): Int {
    println("getId()执行时间：${System.currentTimeMillis()} , thread:${Thread.currentThread().name}")
    delay(3000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    println("getId()执行时间：${System.currentTimeMillis()} , thread:${Thread.currentThread().name}")
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}


//private suspend fun getId(): Deferred<String> {
//    return GlobalScope.async(Dispatchers.IO) {
//        delay(2000)
//        println("getId()被执行了")
//
//        "hearing"
//    }
//}
//
//private suspend fun getAvatar(id: String): String {
//    return GlobalScope.async(Dispatchers.IO) {
//        delay(1000)
//        "avatar-$id"
//    }.await()
//}
//
//fun main(args: Array<String>) {
//    GlobalScope.launch {
//        val id = getId()
//        val avatar = getAvatar(id)
//        println("${Thread.currentThread().name} - $id - $avatar")
//        println("Hello")
//
//    }
//    Thread.sleep(3000L) // block main thread for 2 seconds to keep JVM alive
//
//}


class AnalyzeLiveData : LiveData<String>() {

    override fun onActive() {
        super.onActive()
    }

    override fun onInactive() {
        super.onInactive()
    }
}


/**
 * Created by xiej on 2021/10/11.
 */

fun View.clickFlow() = callbackFlow {
    setOnClickListener { trySend(Unit).isSuccess }
    awaitClose { setOnClickListener(null) }
}


@InternalCoroutinesApi
fun <T> Flow<T>.throttleFirst(thresholdMillis: Long): Flow<T> = flow {
    var lastTime = 0L // 上次发射数据的时间
    // 收集数据
    collect { upstream ->
        // 当前时间
        val currentTime = System.currentTimeMillis()
        // 时间差超过阈值则发送数据并记录时间
        if (currentTime - lastTime > thresholdMillis) {
            lastTime = currentTime
            emit(upstream)
        }
    }
}

@InternalCoroutinesApi
fun setAntiClickListener(view: View) {
    view.clickFlow()
        .throttleFirst(3000)
        .onEach {
            // 点击事件响应
//            ToastUtil.showMsg("点击了")
            Log.i("xj", "被点击了")
        }
        .launchIn(MainScope())
}
