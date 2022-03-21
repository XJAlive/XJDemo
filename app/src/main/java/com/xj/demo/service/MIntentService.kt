package com.xj.demo.service

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread

class MIntentService : IntentService(SERVICE_NAME) {

    companion object {
        const val SERVICE_NAME = "upload_service"
    }

    override fun onHandleIntent(intent: Intent?) {

        val handlerThread = HandlerThread("sample")
        Handler(handlerThread.looper).post {
            //创建串行线程任务
            Thread.sleep(300)
            println("任务执行中...")
        }
        Handler(handlerThread.looper).removeCallbacksAndMessages(null)
        handlerThread.looper.quit()


    }
}