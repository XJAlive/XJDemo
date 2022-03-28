package com.xj.demo.binder

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * 对外提供服务
 */
class ServerService : Service() {
    override fun onBind(intent: Intent?): IBinder {
        return Stub()
    }
}