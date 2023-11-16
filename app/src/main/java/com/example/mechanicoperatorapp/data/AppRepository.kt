package com.example.mechanicoperatorapp.data

import android.content.Context
import androidx.room.Room
import com.example.mechanicoperatorapp.data.dataClasses.Base
import com.example.mechanicoperatorapp.data.database.MechanicDatabase
import kotlinx.coroutines.flow.Flow

private const val DATABASE_NAME = "Mechanic_db"

class AppRepository private constructor(
    context: Context
) : RepositoryInterface {

    private val database: MechanicDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            MechanicDatabase::class.java,
            DATABASE_NAME
        ).build()

    fun getData(): Flow<List<Base>> {
        return database.baseDao().get()
    }

    suspend fun addData() {
        database.baseDao().add(Base(17))
    }

    companion object {
        private var INSTANCE: AppRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = AppRepository(context)
            }
        }

        fun get(): AppRepository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized")
        }
    }
}