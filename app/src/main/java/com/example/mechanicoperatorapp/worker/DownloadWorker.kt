package com.example.mechanicoperatorapp.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mechanicoperatorapp.data.AppRepository

class DownloadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val context = context


    override fun doWork(): Result {
        try {
            AppRepository.get().getData()
        } catch (e: Exception) {
            return Result.failure()
        }
        return Result.success()
    }
}