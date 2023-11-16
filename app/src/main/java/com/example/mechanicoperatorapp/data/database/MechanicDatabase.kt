package com.example.mechanicoperatorapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


// TODO: initialize in application
@Database(entities = [], version = 1)
abstract class MechanicDatabase: RoomDatabase() {
    abstract fun baseDao(): BaseDao
}