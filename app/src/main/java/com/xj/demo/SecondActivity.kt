package com.xj.demo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

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

                (service as? RunningService.LocalBinder)?.run {
                    runningService = this.getService()
                }
            }
        }

        bindService(Intent(this, RunningService::class.java), serviceConnection!!, BIND_AUTO_CREATE)
    }

    fun stop(view: View) {
        unbindService(serviceConnection!!)
    }

}