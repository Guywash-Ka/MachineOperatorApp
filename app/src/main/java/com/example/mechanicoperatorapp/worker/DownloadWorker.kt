package com.example.mechanicoperatorapp.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mechanicoperatorapp.data.AppRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DownloadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val context = context
    private val rep = AppRepository.get()

    override fun doWork(): Result {
        try {
            val notification = NotificationCompat.Builder(applicationContext, "default")
                .setContentTitle("Новые данные загружены")
                .setContentText("Возможно Вы получили новые задания, скорее проверьте!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            GlobalScope.launch { rep.sync() }
        } catch (e: Exception) {
            return Result.failure()
        }
        return Result.success()
    }
}