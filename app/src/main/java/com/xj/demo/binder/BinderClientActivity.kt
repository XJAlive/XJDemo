package com.xj.demo.binder

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import com.xj.demo.R

/**
 * Created by xiej on 2021/3/1
 */
class BinderClientActivity : AppCompatActivity() {

    companion object {
        internal const val INTERFACE_NAME = "com.xj.interface"
        internal const val METHOD_PRINT = Binder.FIRST_CALL_TRANSACTION + 1
        internal const val METHOD_PLUS = Binder.FIRST_CALL_TRANSACTION + 2
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            service?.run {
                val parcelData1 = Parcel.obtain()
                val replyData1 = Parcel.obtain()
                val parcelData2 = Parcel.obtain()
                val replyData2 = Parcel.obtain()
                try {
                    parcelData1.writeInterfaceToken(INTERFACE_NAME)
                    service.transact(METHOD_PRINT, parcelData1, replyData1, 0)
                    replyData1.readException()

                    parcelData2.writeInterfaceToken(INTERFACE_NAME)
                    parcelData2.writeInt(1)
                    parcelData2.writeInt(2)
                    service.transact(METHOD_PLUS, parcelData2, replyData2, 0)
                    val result = replyData2.readInt()
                    Log.i("xj", "【客户端】 进程id = ${Process.myPid()}")
                    ToastUtils.showLong("调用服务返回结果：$result")
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    parcelData1.recycle()
                    replyData1.recycle()
                    parcelData2.recycle()
                    replyData2.recycle()
                }
            }
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


