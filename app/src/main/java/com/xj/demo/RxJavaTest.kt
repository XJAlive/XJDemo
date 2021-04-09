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

    val list = mutableListOf<String>()
    list.add(12,"12")
    System.out.println(list.size)
}