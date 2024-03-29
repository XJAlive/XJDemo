package com.xj.demo.rxjava

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit


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
        Observable.just(1)
            .`as`(AutoDispose.autoDisposable<Int>(AndroidLifecycleScopeProvider.from(lifecycleOwner!!)))

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
    }

    @SuppressLint("CheckResult")
    fun threadExecute() {
//        Observable.create(ObservableOnSubscribe<String> {
//            it.onNext("123")
//            it.onComplete()
//        })
//            .map {
//                println("step 0 线程" + Thread.currentThread())
//                "0"
//            }
//            .subscribeOn(Schedulers.io())
//            .map {
//                println("step 0 线程" + Thread.currentThread())
//                "0"
//            }
//            .subscribeOn(Schedulers.computation())
//            .observeOn(Schedulers.single())
//            .map {
//                println("step 1 线程" + Thread.currentThread())
//                "1"
//            }.subscribe(object : Observer<String> {
//                override fun onSubscribe(d: Disposable) {
//                    Log.i("xj", "回调onSubscribe")
//                }
//
//                override fun onNext(t: String) {
//                }
//
//                override fun onError(e: Throwable) {
//                }
//
//                override fun onComplete() {
//                }
//            })
//
        val behavior: BehaviorSubject<String>? = null
        behavior?.publish()

        var i = 0
        val connectableObservable = Observable.interval(1, TimeUnit.SECONDS).map { i++ }.publish()
        connectableObservable.connect()

        connectableObservable.subscribe {
            Log.i("xj", "收到数据:$it")
        }
    }


    fun missingStrategy() {
        Flowable.create<Int>({ emitter ->
            for (i in 0..128) {
                emitter.onNext(i)
            }
        }, BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : FlowableSubscriber<Int> {
                override fun onSubscribe(s: Subscription) {
                    s.request(Long.MAX_VALUE)
                }

                override fun onNext(integer: Int) {
                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    Log.e("xj", "onNext=$integer")
                }

                override fun onError(t: Throwable) {
                    t.printStackTrace()
                }

                override fun onComplete() {}
            })


    }

}