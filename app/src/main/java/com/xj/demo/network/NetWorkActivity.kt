package com.xj.demo.network

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.xj.demo.R
import com.xj.demo.bean.Banner
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.*

/**
 * Created by xiej on 2021/3/1
 */
class NetWorkActivity : AppCompatActivity() {

    private val rejectedExecutionHandler =
            RejectedExecutionHandler { r, executor -> Log.w("xj", "线程池满了，执行拒绝策略任务+1") }

    //线程池
    private val executors = ThreadPoolExecutor(
            6,
            10,
            0,
            TimeUnit.SECONDS,
            LinkedBlockingQueue<Runnable>(2),
            Executors.defaultThreadFactory(),
            rejectedExecutionHandler
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
    }

    fun start(view: View) {
        Log.i("xj", "CPU核数" + Runtime.getRuntime().availableProcessors().toString())
        Thread {
            kotlin.runCatching {
                val url = URL("https://www.baidu.com")
                val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.connectTimeout = 3000
                urlConnection.connect()
                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = urlConnection.inputStream

                    val bufferReader = BufferedReader(InputStreamReader(inputStream))
                    val sb = StringBuilder()
                    var line: String?
                    while ((bufferReader.readLine().also { line = it }) != null) {
                        sb.append(line)
                    }

//                    bufferReader.close()
                    Log.i("xj", "请求结果：$sb")
                }
            }.onFailure {
                it.printStackTrace()
            }
        }.start()

    }

    var count = 0

    fun execRunnable(view: View) {
        executors.execute {
            count++
            Log.i("xj", "当前线程:{${Thread.currentThread()}}正在执行第${count}个任务")
            Thread.sleep(30000)
        }

    }


    class Result<T> {
        var data: T? = null
        var errorCode: Int = 0
        var errorMsg: String? = null
    }

    interface IRequest {
        @GET("https://www.wanandroid.com/banner/json")
        fun getResult(): Call<Result<Banner>>
    }

    fun retrofitRequest() {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val result = retrofit.create(IRequest::class.java).getResult()
    }

    fun loadImage() {
        val view: ImageView? = null
        Glide.with(this).load("").into(view!!)
    }


}


