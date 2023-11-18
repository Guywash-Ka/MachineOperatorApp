package com.example.mechanicoperatorapp.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class TasksEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val json: String,
)

data class TasksNetwork(
    val id: Int,
    val json: String
)

data class Tasks(
    val id: Int,
    val json: String
)
