package com.example.mechanicoperatorapp.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Agronom")
data class AgronomEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val password: String,
    val nfc: String,
    val salary: Float
)

data class AgronomNetwork(
    val id: Int,
    val name: String,
    val password: String,
    val nfc: String,
    val salary: Float
)

data class Agronom(
    val id: Int,
    val name: String,
    val password: String,
    val nfc: String,
    val salary: Float
)

fun AgronomNetwork.asEntity() = AgronomEntity(
    id = id,
    name = name,
    password = password,
    nfc = nfc,
    salary = salary
)

fun AgronomEntity.asExternalModel() = Agronom(
    id = id,
    name = name,
    password = password,
    nfc = nfc,
    salary = salary
)
