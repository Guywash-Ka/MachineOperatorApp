package com.example.mechanicoperatorapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mechanicoperatorapp.data.dataClasses.Agregat
import com.example.mechanicoperatorapp.data.dataClasses.AgregatEntity
import com.example.mechanicoperatorapp.data.dataClasses.AgronomEntity
import com.example.mechanicoperatorapp.data.dataClasses.DepthEntity
import com.example.mechanicoperatorapp.data.dataClasses.FarmField
import com.example.mechanicoperatorapp.data.dataClasses.FarmFieldEntity
import com.example.mechanicoperatorapp.data.dataClasses.FieldsEntity
import com.example.mechanicoperatorapp.data.dataClasses.OperationEntity
import com.example.mechanicoperatorapp.data.dataClasses.SpeedEntity
import com.example.mechanicoperatorapp.data.dataClasses.TasksEntity
import com.example.mechanicoperatorapp.data.dataClasses.TemplatesEntity
import com.example.mechanicoperatorapp.data.dataClasses.TransportEntity
import com.example.mechanicoperatorapp.data.dataClasses.TypeConverter
import com.example.mechanicoperatorapp.data.dataClasses.Water
import com.example.mechanicoperatorapp.data.dataClasses.WaterEntity
import com.example.mechanicoperatorapp.data.dataClasses.WorkMan
import com.example.mechanicoperatorapp.data.dataClasses.WorkManEntity
import com.example.mechanicoperatorapp.data.dataClasses.WorkerEntity

@Database(entities = [WorkerEntity::class, AgronomEntity::class,
    FieldsEntity::class, TasksEntity::class, TemplatesEntity::class,
                     OperationEntity::class, FarmFieldEntity::class,
                     WorkManEntity::class, TransportEntity::class,
                     AgregatEntity::class, DepthEntity::class, SpeedEntity::class,
                     WaterEntity::class],
    version = 1)
@TypeConverters(TypeConverter::class)
abstract class MechanicDatabase: RoomDatabase() {
    abstract fun loginDao(): LoginDao
    abstract fun fieldsDao(): FieldsDao
    abstract fun tasksDao(): TasksDao
    abstract fun templatesDao(): TemplatesDao
    abstract fun infoClassesDao(): InfoClassesDao
}