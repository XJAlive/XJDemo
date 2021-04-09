package com.xj.demo

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

/**
 * Created by xiej on 2021/3/4
 */
class RunningService : Service() {

    var count = 0
    var flag = true

    inner class LocalBinder : Binder() {
        fun getService(): RunningService {
            return this@RunningService
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.i("xj", "---->onBind,hashcode=${hashCode()}")

        return null
    }

    override fun onRebind(intent: Intent?) {
        Log.i("xj", "---->onRebind,hashcode=${hashCode()}")

        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i("xj", "---->onUnbind,hashcode=${hashCode()}")

        return true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("xj", "---->onStartCommand,flags= $flags,startId=$startId,hashcode=${hashCode()}")
        return START_STICKY
//        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        Log.i("xj", "---->onCreate,hashcode=${hashCode()}")
        super.onCreate()

        Thread {
            while (flag) {
                startCount()
                try {
                    Thread.sleep(1000)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.start()
    }


    override fun onDestroy() {
        Log.i("xj", "---->onDestroy,hashcode=${hashCode()}")
        super.onDestroy()
    }


    fun startCount() {
        count++
        Log.i("xj", "count = $count")

        if (count == 10) {
            flag = false
            stopSelf()
        }
    }
}