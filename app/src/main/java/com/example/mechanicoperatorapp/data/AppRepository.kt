package com.example.mechanicoperatorapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.mechanicoperatorapp.data.dataClasses.Base
import com.example.mechanicoperatorapp.data.database.MechanicDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")
private const val DATABASE_NAME = "Mechanic_db"

class AppRepository private constructor(
    private val context: Context
) : RepositoryInterface {

    private val database: MechanicDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            MechanicDatabase::class.java,
            DATABASE_NAME
        ).build()

    fun getId() = context.dataStore.data.map { preferences ->
        preferences[idKey] ?: -1
    }

    suspend fun setId(id: Int) {
        context.dataStore.edit{ it[idKey] = id }
    }

    fun getRole() = context.dataStore.data.map { preferences ->
        preferences[roleKey] ?: ""
    }

    suspend fun setRole(role: String) {
        context.dataStore.edit { it[roleKey] = role }
    }

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