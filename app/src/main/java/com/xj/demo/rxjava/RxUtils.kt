package com.xj.demo.rxjava

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers

fun main() {
    RxUtils().threadExecute()
}

class RxUtils {

    companion object {
        const val TAG = "xj"
    }

    @SuppressLint("CheckResult")
    fun bind() {

        val lifecycleOwner: LifecycleOwner? = null
        Observable.just(1).`as`(AutoDispose.autoDisposable<Int>(AndroidLifecycleScopeProvider.from(lifecycleOwner!!)))

        Observable.create<Int> { emitter -> // 1. 发送5个事件
            emitter.onNext(1)
            emitter.onNext(2)
            emitter.onNext(3)
            emitter.onNext(4)
            emitter.onNext(5)
        }.filter(@SuppressLint("NewApi")
        object : Predicate<Int> {
            // 根据test()的返回值 对被观察者发送的事件进行过滤 & 筛选
            // a. 返回true，则继续发送
            // b. 返回false，则不发送（即过滤）
            @Throws(Exception::class)
            override fun test(integer: Int): Boolean {
                return integer > 3
                // 本例子 = 过滤了整数≤3的事件
            }
        }).subscribe(object : Observer<Int> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "开始采用subscribe连接")
            }

            override fun onNext(value: Int) {
                Log.d(TAG, "过滤后得到的事件是：$value")
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "对Error事件作出响应")
            }

            override fun onComplete() {
                Log.d(TAG, "对Complete事件作出响应")
            }
        })



    }

    @SuppressLint("CheckResult")
    fun createObservable() {
        val observable = object : Observable<String>() {
            override fun subscribeActual(observer: Observer<in String>?) {
                observer?.onNext("123")
                observer?.onComplete()
            }
        }

        Observable.create(ObservableOnSubscribe<String> {
            it.onNext("123")
            it.onComplete()
        })
    }

    @SuppressLint("CheckResult")
    fun threadExecute(){
        Observable.just("1")
            .map {
                println("step 0 线程" + Thread.currentThread())
            }
            .subscribeOn(Schedulers.computation())
            .map {
                println("step 1 线程" + Thread.currentThread())
            }.subscribe {
                println("step 2 线程" + Thread.currentThread())
            }
    }


}