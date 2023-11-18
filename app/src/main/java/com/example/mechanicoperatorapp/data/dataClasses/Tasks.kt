package com.example.mechanicoperatorapp.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "Tasks")
@TypeConverters(TypeConverter::class)
data class TasksEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val agronomId: Int,
    val workerId: Int,
    val templateId: Int,
    val json: List<Int>
)

data class TasksNetwork(
    val id: Int,
    val agronomId: Int,
    val workerId: Int,
    val templateId: Int,
    val json: List<Tasks>
)

data class Tasks(
    val id: Int,
    val agronomId: Int,
    val workerId: Int,
    val templateId: Int,
    val json: String
)
