package com.example.mechanicoperatorapp.data.dataClasses

import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey

@Entity(tableName = "Fields")
data class FieldsEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
)

data class FieldsNetwork(
    val id: Int,
    val name: String,
)

data class Fields(
    val id: Int,
    val name: String,
)
