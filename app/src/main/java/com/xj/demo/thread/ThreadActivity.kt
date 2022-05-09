package com.xj.demo.thread

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xj.demo.R
import java.util.concurrent.atomic.AtomicInteger

/**
 * 测试多线程场景
 * Created by xiej on 2021/3/1
 */
class ThreadActivity : AppCompatActivity() {

    private val queue = XQueue()
    private var count = AtomicInteger()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)
    }

    fun putElement(view: View) {
        count.getAndIncrement()
        Thread {
            queue.put(count.get().toString())
        }.start()
    }

    fun takeElement(view: View) {
        count.getAndDecrement()
        Thread {
            queue.take()
        }.start()
    }

}


