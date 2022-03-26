package com.xj.demo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xj.demo.binder.BinderActivity
import com.xj.demo.contentprovider.ProviderActivity
import com.xj.demo.network.NetWorkActivity
import com.xj.demo.service.RunningService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins

/**
 * Created by xiej on 2021/3/1
 */
class MainActivity : AppCompatActivity() {

    val TAG = "xj"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun navigation(view: View) {
        startActivity(Intent(this, SecondActivity::class.java))
    }

    fun navigation2Provider(view: View) {
        startActivity(Intent(this, ProviderActivity::class.java))
    }

    fun click2(view: View) {
        test2()
    }

    @SuppressLint("CheckResult")
    fun test2() {
        val list = mutableListOf<Observable<String>>()
        for (i in 1..5) {
            val observable = Observable.create<String> { emitter ->
                if (i == 3) {
                    emitter.onError(Throwable("报错了"))
                } else {
                    emitter.onNext(i.toString())
                    emitter.onComplete()
                }
            }.onErrorResumeNext(Observable.just("报错了"))
            list.add(observable)
        }

        val mySubscriber: Observer<String> =
            object : Observer<String> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(s: String) {
                    Log.e(TAG, "onNext.................$s")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError.....................")
                    RxJavaPlugins.onError(e)
                }

                override fun onComplete() {
                    Log.e(TAG, "onCompleted.................")
                }
            }
//        Observable.concat(list).subscribe(mySubscriber)


        list.reduce { t1, t2 ->
            Observable.just(t1.blockingFirst() + t2.blockingFirst())
        }.subscribe(Consumer {
            Log.e(TAG, "onNext.................$it")
        }, Consumer {
            Log.e(TAG, "onError.....................")
        })

    }

    private fun concatObserver(): Observable<Int> {
        val obser1 = Observable.just(1, 2)
        val obser2 = Observable.just(4)
        val obser3 = Observable.just(7)
        val obser4 = Observable.just(9)
        val obser5 = Observable.just(11)

        val list = mutableListOf<Observable<Int>>(obser1, obser2, obser3, obser4, obser5)
        return Observable.concat(list)
    }

    fun start(view: View) {
        startService(Intent(this@MainActivity, RunningService::class.java))
    }

    fun stop(view: View) {
        stopService(Intent(this@MainActivity, RunningService::class.java))
    }

    fun filterAction(view: View) {
        val intent = Intent().apply {
            action = Intent.ACTION_EDIT
        }
        startActivity(intent)
    }

    fun networkAction(view: android.view.View) {
        startActivity(Intent(this, NetWorkActivity::class.java))
    }

    fun startBinder(view: android.view.View) {
        startActivity(Intent(this, BinderActivity::class.java))
    }

}