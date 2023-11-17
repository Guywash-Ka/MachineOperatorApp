package com.example.mechanicoperatorapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mechanicoperatorapp.data.dataClasses.AgronomistEntity
import com.example.mechanicoperatorapp.data.dataClasses.WorkerEntity


// TODO: initialize in application
@Database(entities = [WorkerEntity::class, AgronomistEntity::class], version = 1)
abstract class MechanicDatabase: RoomDatabase() {
    abstract fun loginDao(): LoginDao
}