package com.example.mechanicoperatorapp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.mechanicoperatorapp.data.dataClasses.RoleAndId
import com.example.mechanicoperatorapp.data.dataClasses.WorkerEntity
import com.example.mechanicoperatorapp.data.database.MechanicDatabase
import com.example.mechanicoperatorapp.network.RetrofitInstance.API
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

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
        runBlocking { context.dataStore.edit{ it[idKey] = id } }
    }

    fun getRole() = context.dataStore.data.map { preferences ->
        preferences[roleKey] ?: ""
    }

    suspend fun setRole(role: String) {
        runBlocking  { context.dataStore.edit { it[roleKey] = role } }
    }

    suspend fun getProfileByNfc(nfc: String): RoleAndId {
        return try {
            val res = Gson().fromJson(
                API.getWorkerByNfc(nfc).body().toString(),
                RoleAndId::class.java
            )
            setRole(res.role)
            setId(res.id)
            res
        } catch (e: Exception) {
            RoleAndId("", -1)
        }
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