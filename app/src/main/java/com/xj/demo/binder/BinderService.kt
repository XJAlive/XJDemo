package com.xj.demo.binder

import android.os.*
import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import com.xj.demo.binder.JService.Companion.INTERFACE_NAME
import com.xj.demo.binder.JService.Companion.METHOD_PLUS
import com.xj.demo.binder.JService.Companion.METHOD_PRINT

/**
 * 服务端进程
 */
class Stub : Binder(), JService, IInterface {

    init {
        //可以用于区分此次调用是进程内还是进程间，因为进程内调用的，Stub 子类对象也就是
        //服务端实例 的构造函数被调用过程中将 DESCRIPTOR 保存为了自己的成员变量，所以调用
        //obj.queryLocalInterface(DESCRIPTOR)得到的结果不为空（实例实现是返回Binder子类也
        // 是Stub子类对象本身），如果是代理Binder端，之前的代码可以看出BinderProxy类重新的方法
        // 直接返回就是null，这就可以区分当前调用是
        //进程内还是进程间了
        this.attachInterface(this, INTERFACE_NAME)
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

    override fun asBinder(): IBinder {
        return this
    }

}

interface JService {
    //无参方法
    fun printLog()
    //含参方法
    fun plus(a: Int, b: Int): Int

    companion object {
        internal const val INTERFACE_NAME = "com.xj.interface"
        internal const val METHOD_PRINT = Binder.FIRST_CALL_TRANSACTION + 1
        internal const val METHOD_PLUS = Binder.FIRST_CALL_TRANSACTION + 2

        //用来解决本地调用和跨进程调用
        fun asInterface(binder: IBinder?): JService? {
            binder?.run {
                //获取Binder实例
                val service = queryLocalInterface(INTERFACE_NAME) as JService?
                if (service is Stub) {
                    return service
                }
                return Proxy(this)
            }
            return null
        }
    }
}


class Proxy(var remote: IBinder) : JService, IInterface {

    override fun printLog() {
        val parcelData1 = Parcel.obtain()
        val replyData1 = Parcel.obtain()
        try {
            parcelData1.writeInterfaceToken(INTERFACE_NAME)
            remote.transact(METHOD_PRINT, parcelData1, replyData1, 0)
            replyData1.readException()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            parcelData1.recycle()
            replyData1.recycle()
        }
    }

    override fun plus(a: Int, b: Int): Int {
        val parcelData2 = Parcel.obtain()
        val replyData2 = Parcel.obtain()
        try {
            parcelData2.writeInterfaceToken(INTERFACE_NAME)
            parcelData2.writeInt(a)
            parcelData2.writeInt(b)
            remote.transact(METHOD_PLUS, parcelData2, replyData2, 0)
            val result = replyData2.readInt()
            Log.i("xj", "【客户端】 进程id = ${Process.myPid()}")
            ToastUtils.showLong("调用服务返回结果：$result")
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            parcelData2.recycle()
            replyData2.recycle()
        }
        return -1
    }

    override fun asBinder(): IBinder {
        //直接返回remote对象
        return remote
    }

}

