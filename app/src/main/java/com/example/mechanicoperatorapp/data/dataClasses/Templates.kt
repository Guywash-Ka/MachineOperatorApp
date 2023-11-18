package com.example.mechanicoperatorapp.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Templates")
data class TemplatesEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val requiredFields: String,
)

data class TemplatesNetwork(
    val id: Int,
    val title: String,
    val requiredFields: String,
)

data class Templates(
    val id: Int,
    val title: String,
    val requiredFields: String,
)
