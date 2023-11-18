package com.example.mechanicoperatorapp.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.mechanicoperatorapp.data.dataClasses.RoleAndId
import com.example.mechanicoperatorapp.data.dataClasses.WorkerEntity
import com.example.mechanicoperatorapp.data.database.MechanicDatabase
import com.example.mechanicoperatorapp.network.RetrofitInstance.API
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

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

    suspend fun getProfileByNfc(nfcRaw: String): RoleAndId {
        val gson = GsonBuilder().create()
        val nfc = nfcRaw.lowercase()
        val response = API.getUserByNfc(nfc)
        return try {
            val res = gson.fromJson(
                response.body()?.string(),
                RoleAndId::class.java
            )
            setRole(res.role)
            setId(res.id)
            res
        } catch (e: Exception) {
            Log.e("REPO", "${response.code()}")
            RoleAndId("", -1)
        }
    }

    suspend fun getProfileByPassword(password: String): RoleAndId {
        val gson = GsonBuilder().create()
        val response = API.getUserByPassword(password)
        return try {
            val res = gson.fromJson(
                response.body()?.string(),
                RoleAndId::class.java
            )
            setRole(res.role)
            setId(res.id)
            res
        } catch (e: Exception) {
            Log.e("REPO", "${response.code()}")
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