package com.example.mechanicoperatorapp.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Worker")
data class WorkerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val password: String,
    val nfc: String,
    val salary: Float
)

data class WorkerNetwork(
    val id: Int,
    val name: String,
    val password: String,
    val nfc: String,
    val salary: Float
)

data class Worker(
    val id: Int,
    val name: String,
    val password: String,
    val nfc: String,
    val salary: Float
)

fun WorkerNetwork.asEntity() = WorkerEntity(
    id = id,
    name = name,
    password = password,
    nfc = nfc,
    salary = salary
)

fun WorkerEntity.asExternalModel() = Worker(
    id = id,
    name = name,
    password = password,
    nfc = nfc,
    salary = salary
)
