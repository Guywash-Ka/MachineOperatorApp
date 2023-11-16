package com.example.mechanicoperatorapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mechanicoperatorapp.data.dataClasses.Base


// TODO: initialize in application
@Database(entities = [Base::class], version = 1)
abstract class MechanicDatabase: RoomDatabase() {
    abstract fun baseDao(): BaseDao
}