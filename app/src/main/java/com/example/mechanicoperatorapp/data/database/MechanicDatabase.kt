package com.example.mechanicoperatorapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mechanicoperatorapp.data.dataClasses.AgronomEntity
import com.example.mechanicoperatorapp.data.dataClasses.WorkerEntity

@Database(entities = [WorkerEntity::class, AgronomEntity::class], version = 1)
abstract class MechanicDatabase: RoomDatabase() {
    abstract fun loginDao(): LoginDao
}