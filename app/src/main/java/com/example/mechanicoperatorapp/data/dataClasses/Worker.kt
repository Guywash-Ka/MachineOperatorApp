package com.example.mechanicoperatorapp.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Worker")
data class WorkerEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val password: String,
    val nfc: String
)
