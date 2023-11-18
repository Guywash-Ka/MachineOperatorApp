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

data class WorkerNetwork(
    val id: Int,
    val name: String,
    val password: String,
    val nfc: String
)

data class Worker(
    val id: Int,
    val name: String,
    val password: String,
    val nfc: String
)

fun WorkerNetwork.asEntity() = AgronomEntity(
    id = id,
    name = name,
    password = password,
    nfc = nfc
)

fun WorkerEntity.asExternalModel() = Agronom(
    id = id,
    name = name,
    password = password,
    nfc = nfc
)
