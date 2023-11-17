package com.example.mechanicoperatorapp.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Agronomist")
data class AgronomistEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val password: String,
    val nfc: String
)
