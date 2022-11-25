package com.xj.demo.network

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.reflect.TypeToken
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.xj.demo.BannerItem
import com.xj.demo.BizResult
import com.xj.demo.R
import kotlinx.coroutines.*
import okhttp3.*
import okio.IOException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.*
import kotlin.coroutines.resumeWithException

/**
 * Created by xiej on 2021/3/1
 */
class NetWorkActivity : AppCompatActivity() {

    private val rejectedExecutionHandler =
        RejectedExecutionHandler { r, executor -> Log.w("xj", "线程池满了，执行拒绝策略任务+1") }

    class PrintLinkedBlockingQueue : LinkedBlockingQueue<Runnable> {
        constructor() : super() {
        }

        constructor(capacity: Int) : super(capacity) {
        }

        override fun offer(e: Runnable?): Boolean {
            val result = super.offer(e)
            Log.d("xj", "offer()放到等待队列任务+1,是否成功 = $result")
            return result
        }

    }

    //线程池
    private val executors = ThreadPoolExecutor(
        6,
        10,
        0,
        TimeUnit.SECONDS,
        PrintLinkedBlockingQueue(2),
        Executors.defaultThreadFactory(),
        rejectedExecutionHandler
    )

    private val okHttpClient =
        OkHttpClient.Builder().sslSocketFactory(SSLConfig.DEFAULT_SSL_SOCKET_FACTORY,
            (SSLConfig.DEFAULT_SSL_SOCKET_FACTORY as SSLConfig.DefaultSSLSocketFactory).trustManagers)
            .build()

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

    fun requestData(view: View) {
        executors.execute {
            val request =
                Request.Builder().get().url("https://www.wanandroid.com/banner/json").build()
            val call = okHttpClient.newCall(request)
//            val response = call.execute()
//            Log.i("xj", "接口返回： ${response.body.toString()}")
            val dispatcher = CoroutineExceptionHandler { coroutineContext, throwable ->
                Log.e("xj","协程报错了")
            }

            try {
                val job1 = MainScope().launch {

                    val result = suspendCancellableCoroutine<String> { continuation ->
                        continuation.invokeOnCancellation {
                            Log.w("xj", "协程取消回调,同时取消call")
                            call.cancel()
                        }

                        call.enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                continuation.resumeWithException(e)
                            }

                            override fun onResponse(call: Call, response: Response) {
                                val turnsType =
                                    object : TypeToken<BizResult<List<BannerItem>>>() {}.type
                                val str = response.body?.string().orEmpty()
                                continuation.resumeWith(Result.success(str))
                                val turns =
                                    GsonUtils.fromJson<BizResult<List<BannerItem>>>(str,
                                        turnsType)
//                            Log.i("xj", "接口返回： $turns")
                            }
                        })
                    }
                    Log.i("xj", "接口返回： $result")

                }//launch
            }catch (e:Exception){
                Log.e("xj","协程报错了")
            }

        }
    }


    fun retrofitRequest(view: View) {
        val baseUrl = "https://www.wanandroid.com"
        val retrofit =
            Retrofit.Builder().baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
            try {
//                val deferred = retrofit.create(NetWorkServiceApi::class.java).getBannerAsync()
//                val remoteData = deferred.await()
//                val localData = getLocalData()
                val remoteData = retrofit.create(NetWorkServiceApi::class.java).getBannerSuspend()
                Log.i("xj", "请求完成,result=$remoteData")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
            }
        }
        Log.i("xj", "请求开始")
    }

    private suspend fun getLocalData(): BizResult<List<BannerItem>> {
        return BizResult(listOf(BannerItem("本地数据")), 0, "")
    }

}


