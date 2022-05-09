package com.xj.demo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.AsyncTask
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xj.demo.service.RunningService

/**
 * Created by xiej on 2021/3/1
 */
class SecondActivity : AppCompatActivity() {

    var serviceConnection: ServiceConnection? = null
    var runningService: RunningService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    fun start(view: View) {
        serviceConnection = object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                Log.i("xj", "---->onServiceDisconnected,hashcode=${this.hashCode()}")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.i("xj", "---->onServiceConnected,hashcode=${this.hashCode()}")

                (service as? RunningService.Stub)?.run {
                    runningService = this.getService()
                }
            }
        }

        bindService(Intent(this, RunningService::class.java), serviceConnection!!, BIND_AUTO_CREATE)
    }

    fun stop(view: View) {
        unbindService(serviceConnection!!)
    }

    fun startAsyncTask(view: View) {
        JobAsyncTask().execute("1", "2", "3")
    }

}


class JobAsyncTask : AsyncTask<String, Int, String>() {
    override fun doInBackground(vararg params: String): String {
        Thread.sleep(1000)
        publishProgress(30)
        Thread.sleep(1000)
        publishProgress(60)
        Thread.sleep(1000)
        publishProgress(100)
        return params[0] + params[1] + params[2]
    }

    /**
     * 在execute()被调用后立即执行，一般用于UI更新
     */
    override fun onPreExecute() {
        super.onPreExecute()
        Log.i("xj", "onPreExecute")
    }

    /**
     * 后台任务执行结束后调用，返回结果
     */
    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        Log.i("xj", "onPostExecute,结果=$result")
    }

    /**
     * 更新任务进度
     */
    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        Log.i("xj", "收到进度更新:" + values[0])
    }

    override fun onCancelled() {
        super.onCancelled()
        Log.i("xj", "onCancelled")
    }


}