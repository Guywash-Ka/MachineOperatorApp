package com.example.mechanicoperatorapp.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Agronom")
data class AgronomEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val password: String,
    val nfc: String
)

data class AgronomNetwork(
    val id: Int,
    val name: String,
    val password: String,
    val nfc: String
)

data class Agronom(
    val id: Int,
    val name: String,
    val password: String,
    val nfc: String
)

fun AgronomNetwork.asEntity() = AgronomEntity(
    id = id,
    name = name,
    password = password,
    nfc = nfc
)

fun AgronomEntity.asExternalModel() = Agronom(
    id = id,
    name = name,
    password = password,
    nfc = nfc
)
