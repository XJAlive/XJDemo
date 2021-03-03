package com.xj.demo

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Created by xiej on 2021/3/2
 */
class RxJavaUtils {


}

fun main() {
    val TAG = "xj"

    val observable = Observable.create(object : ObservableOnSubscribe<String> {
        override fun subscribe(emitter: ObservableEmitter<String>) {

            for (i in 1..5) {
                if (i == 3) {
                    emitter.onError(Throwable("ERROR"))
                } else {
                    emitter.onNext(i.toString())
                }

            }
            emitter.onComplete()
        }
    })

    val subscriber = object : Observer<String> {
        override fun onComplete() {
            Log.e(TAG, "onComplete.................")
        }

        override fun onNext(t: String) {
            Log.e(TAG, "onNext.................$t")
        }

        override fun onError(t: Throwable) {
            Log.e(TAG, "onError.....................")
        }

        override fun onSubscribe(d: Disposable) {
        }

    }

    observable
        .onErrorResumeNext(Observable.just("this is an error observable", "error2"))
        .subscribe(subscriber)
}