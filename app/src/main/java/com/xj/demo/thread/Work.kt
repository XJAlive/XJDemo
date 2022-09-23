package com.xj.demo.thread

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters


class UploadImageWorker(var context: Context, var workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        Thread.sleep(5000)
        return Result.success()
    }

}

class UploadImageManager(var context: Context) {
    fun start() {
        val request = OneTimeWorkRequestBuilder<UploadImageWorker>().build()
        WorkManager.getInstance(context).enqueue(request)
    }
}
