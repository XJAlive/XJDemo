package com.xj.demo.thread

import android.util.Log
import java.util.concurrent.locks.ReentrantLock

class XQueue {

    private val maxCapacity = 4
    private val lock = ReentrantLock()

    //移除锁标记
    private val takeCondition = lock.newCondition()

    //添加锁标记
    private val putCondition = lock.newCondition()
    private var lists = mutableListOf<String>()

    fun put(value: String) {
        lock.lock()
        try {
            //队列已满，自旋等待
            while (lists.size == maxCapacity) {
                Log.i("xj", "进入自旋，线程=${Thread.currentThread().id}")
                putCondition.await()
            }
            //入列
            lists.add(value)
            Log.i("xj", "添加成功，当前元素个数:${lists.size}，线程=${Thread.currentThread().id}")
            //完成插入之后唤醒所有移除线程，从等待队列移动到同步队列中
            takeCondition.signal()
        } finally {
            lock.unlock()
        }
    }


    fun take(): String? {
        val result: String?
        lock.lock()
        try {
            //队列已空，自旋等待
            while (lists.isEmpty()) {
                Log.w("xj", "进入自旋，线程=${Thread.currentThread().id}")
                takeCondition.await()
            }
            result = lists.removeAt(lists.lastIndex)
            Log.w("xj", "移除成功，当前元素个数:${lists.size}，线程=${Thread.currentThread().id}")
            //完成移除之后唤醒所有添加线程，可以重新插入数据
            putCondition.signal()
        } finally {
            lock.unlock()
        }
        return result
    }

}