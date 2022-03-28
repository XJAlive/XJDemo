package com.xj.demo.binder

import android.os.Binder
import android.os.Parcel
import android.os.Process
import android.util.Log

/**
 * 服务端进程
 */
class Stub : Binder(), JService {

    companion object {
        internal const val INTERFACE_NAME = "com.xj.interface"
        internal const val METHOD_PRINT = FIRST_CALL_TRANSACTION + 1
        internal const val METHOD_PLUS = FIRST_CALL_TRANSACTION + 2
    }

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        when (code) {
            METHOD_PLUS -> {
                data.enforceInterface(INTERFACE_NAME)
                val a = data.readInt()
                val b = data.readInt()
                val result = plus(a, b)
                reply?.writeInt(result)
                return true
            }
            METHOD_PRINT -> {
                data.enforceInterface(INTERFACE_NAME)
                printLog()
                return true
            }
            else -> {
                //NOTHING
            }
        }

        return super.onTransact(code, data, reply, flags)
    }

    override fun printLog() {
        Log.i("xj", "【服务端】 调用了printLog() , 进程id = ${Process.myPid()}}")
    }

    override fun plus(a: Int, b: Int): Int {
        Log.i("xj", "【服务端】 调用了plus(a,b) ,a = $a b = $b,  进程id = ${Process.myPid()}}")
        return a + b
    }

}

interface JService {
    fun printLog()
    fun plus(a: Int, b: Int): Int
}
