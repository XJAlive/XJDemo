package com.xj.demo.binder

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.xj.demo.R

/**
 * Created by xiej on 2021/3/1
 */
class BinderClientActivity : AppCompatActivity() {

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val jService = JService.asInterface(service)
            jService?.printLog()
            jService?.plus(1, 2)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            ToastUtils.showLong("取消服务绑定")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binder)
        //AMS跨进程
//        Toast.makeText(this, intent.getStringExtra("name"), Toast.LENGTH_SHORT).show()
    }

    fun start(view: View) {
        val intent = Intent()
        intent.component = ComponentName("com.xj.demo", "com.xj.demo.binder.ServerService")
        val b = bindService(intent, connection, BIND_AUTO_CREATE)
        Log.e("Client", "      $b")
    }


    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

}


