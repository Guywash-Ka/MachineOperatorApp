package com.example.mechanicoperatorapp

import android.app.Application
import com.example.mechanicoperatorapp.data.AppRepository

class MechanicOperatorApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppRepository.initialize(this)
    }
}