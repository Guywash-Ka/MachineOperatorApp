package com.example.mechanicoperatorapp.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Base")
data class Base(
    @PrimaryKey val id: Int,
)
