package com.example.mechanicoperatorapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mechanicoperatorapp.data.dataClasses.AgronomEntity
import com.example.mechanicoperatorapp.data.dataClasses.FieldsEntity
import com.example.mechanicoperatorapp.data.dataClasses.TasksEntity
import com.example.mechanicoperatorapp.data.dataClasses.TemplatesEntity
import com.example.mechanicoperatorapp.data.dataClasses.WorkerEntity

@Database(entities = [WorkerEntity::class, AgronomEntity::class,
    FieldsEntity::class, TasksEntity::class, TemplatesEntity::class],
    version = 1)
abstract class MechanicDatabase: RoomDatabase() {
    abstract fun loginDao(): LoginDao
    abstract fun fieldsDao(): FieldsDao
    abstract fun tasksDao(): TasksDao
    abstract fun templatesDao(): TemplatesDao
}