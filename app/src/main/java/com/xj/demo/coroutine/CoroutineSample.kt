package com.xj.demo.coroutine

import kotlinx.coroutines.*

class CoroutineSample {

    fun getData() {
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch(start = CoroutineStart.LAZY) {
            try {
                val data1 = getLocalData()
                val data2 = getRemoteData()

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
            }

        }
    }

    private suspend fun getLocalData(): Int {
        return 1
    }

    private suspend fun getRemoteData(): String {
        return "远程数据"
    }
}

