package com.example.mechanicoperatorapp.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mechanicoperatorapp.data.AppRepository

class DownloadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val context = context


    override fun doWork(): Result {
        try {
            AppRepository.get().getData()
            val notification = NotificationCompat.Builder(applicationContext, "default")
                .setContentTitle("Новые данные загружены")
                .setContentText("Возможно Вы получили новые задания, скорее проверьте!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
        } catch (e: Exception) {
            return Result.failure()
        }
        return Result.success()
    }
}