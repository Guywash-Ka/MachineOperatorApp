package com.example.mechanicoperatorapp.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "Templates")
@TypeConverters(TypeConverter::class)
data class TemplatesEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val requiredFields: List<Int>,
)

data class TemplatesNetwork(
    val id: Int,
    val title: String,
    val requiredFields: String,
)

data class Templates(
    val id: Int,
    val title: String,
    val requiredFields: List<Int>,
)

data class TemplatesModel(
    val id: Int,
    val title: String,
    val requiredFields: List<String>
)
