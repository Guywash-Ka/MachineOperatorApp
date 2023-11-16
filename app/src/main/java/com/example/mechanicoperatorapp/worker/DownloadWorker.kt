package com.example.mechanicoperatorapp.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class DownloadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        try {

        } catch (e: Exception) {
            return Result.failure()
        }
        return Result.success()
    }
}