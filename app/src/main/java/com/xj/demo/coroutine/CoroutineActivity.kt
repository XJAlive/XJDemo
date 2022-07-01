package com.xj.demo.coroutine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.xj.demo.R
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Created by xiej on 2021/3/1
 */
class CoroutineActivity : AppCompatActivity() {

    val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
    }

    fun startCoroutine(view: View?) {
//        val textView = findViewById<TextView>(R.id.tvContent)
//        lifecycleScope.launch {
//            while (true) {
//                Log.i("xj", "111")
//                delay(1000)
//            }
//        }
        val intFlow = flow {
            (1..3).forEach {
                emit(it)
                delay(100)
            }
        }

        lifecycleScope.launch {
            flow {
                withContext(Dispatchers.Main) {
                    Log.w("xj", "${Thread.currentThread().name}, 强行切换线程")
                }
                Log.i("xj", "${Thread.currentThread().name}, 发射值$1")
                this.emit(1)
            }.flowOn(Dispatchers.IO).map {
                Log.i("xj", "${Thread.currentThread().name}, 接收值1 $it")
                it
            }.flowOn(Dispatchers.Default).map {
                Log.i("xj", "${Thread.currentThread().name}, 接收值2 $it")
                it
            }.collect()
        }


//        mainScope.launch {
//            while (true){
//                Log.i("xj","111")
//                delay(1000)
//            }
//            textView.text = async(Dispatchers.IO) {
//                log(2)
//                delay(1000)
//                log(3)
//                "Hello1111"
//            }.await()
//            log(4)
//        }
    }


    fun stopCoroutine(view: View?) {
//        mainScope.cancel()
    }
}


