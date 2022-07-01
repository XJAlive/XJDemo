package com.xj.demo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.xj.demo.binder.BinderActivity
import com.xj.demo.contentprovider.ProviderActivity
import com.xj.demo.coroutine.CoroutineActivity
import com.xj.demo.network.NetWorkActivity
import com.xj.demo.rxjava.RxUtils
import com.xj.demo.service.RunningService
import com.xj.demo.thread.ThreadActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Created by xiej on 2021/3/1
 */
class MainActivity : AppCompatActivity() {

    private val TAG = "xj"
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.w("xj", "调用onSaveInstanceState")
        count++
        outState.putInt("key", count)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e("xj", "调用onRestoreInstanceState, ${savedInstanceState.getInt("key")}")
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        val list = arrayListOf<String>()
        list.take(4)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
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


    fun startThread(view: View?) {
        startActivity(Intent(this, ThreadActivity::class.java))
    }

    fun sendEvent(view: View?) {
        EventBus.getDefault().post(MessageEvent())
    }

    fun rxJava(view: View?) {
//        RxUtils().threadExecute()
        RxUtils().missingStrategy()
    }

    fun coroutinePage(view: View){
        startActivity(Intent(this, CoroutineActivity::class.java))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent?) {
        ToastUtils.showShort("收到EventBus事件")
    }
}

class MessageEvent {

}