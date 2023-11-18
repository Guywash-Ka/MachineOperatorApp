package com.example.mechanicoperatorapp.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.mechanicoperatorapp.data.dataClasses.AgregatEntity
import com.example.mechanicoperatorapp.data.dataClasses.DepthEntity
import com.example.mechanicoperatorapp.data.dataClasses.FarmFieldEntity
import com.example.mechanicoperatorapp.data.dataClasses.Fields
import com.example.mechanicoperatorapp.data.dataClasses.FieldsEntity
import com.example.mechanicoperatorapp.data.dataClasses.OperationEntity
import com.example.mechanicoperatorapp.data.dataClasses.RoleAndId
import com.example.mechanicoperatorapp.data.dataClasses.SpeedEntity
import com.example.mechanicoperatorapp.data.dataClasses.Tasks
import com.example.mechanicoperatorapp.data.dataClasses.TasksEntity
import com.example.mechanicoperatorapp.data.dataClasses.Templates
import com.example.mechanicoperatorapp.data.dataClasses.TemplatesEntity
import com.example.mechanicoperatorapp.data.dataClasses.TransportEntity
import com.example.mechanicoperatorapp.data.dataClasses.WaterEntity
import com.example.mechanicoperatorapp.data.dataClasses.WorkManEntity
import com.example.mechanicoperatorapp.data.dataClasses.WorkerEntity
import com.example.mechanicoperatorapp.data.database.MechanicDatabase
import com.example.mechanicoperatorapp.network.RetrofitInstance.API
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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

    suspend fun getProfileByNfc(nfc: String): RoleAndId {
        val gson = GsonBuilder().create()
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

    suspend fun addFieldWithName(id: Int = 1, name: String) {
        database.fieldsDao().addField(FieldsEntity(id, name))
    }

    suspend fun addTaskWithJson(id: Int = 1, json: String) {
        database.tasksDao().addTask(TasksEntity(id, json))
    }

    suspend fun addTemplateWithTitleAndFields(id: Int = 1, title: String, requiredFields: IntArray) {
        database.templatesDao().addTemplate(TemplatesEntity(id, title, requiredFields.joinToString("_")))
    }

    fun getFieldById(id: Int) = database.fieldsDao().getFieldById(id)

    fun getFieldByName(name: String) = database.fieldsDao().getFieldByName(name)

    fun getTemplateById(id: Int) = database.templatesDao().getTemplateById(id)

    fun getTemplateByTitle(title: String) = database.templatesDao().getTemplateByTitle(title)

    fun getTaskById(id: Int) = database.tasksDao().getTaskById(id)

    fun getAllTasks() = flow {
        emit(database.tasksDao().getAllTasks())
        if (isOnline(context)) {
            emit(API.getTasks())
        }
    }

    fun getAllTemplates() = flow {
        emit(database.tasksDao().getAllTasks())
        if (isOnline(context)) {
            emit(API.getTasks())
        }
    }

    fun getAllFields() = flow {
        emit(database.tasksDao().getAllTasks())
        if (isOnline(context)) {
            emit(API.getTasks())
        }
    }

    suspend fun addOperation(id: Int, name: String) = database.infoClassesDao().addOperation(OperationEntity(id, name))

    suspend fun addFarmField(id: Int, name: String) = database.infoClassesDao().addFarmField(FarmFieldEntity(id, name))

    suspend fun addWorkMan(id: Int, name: String) = database.infoClassesDao().addWorkMan(WorkManEntity(id, name))

    suspend fun addTransport(id: Int, name: String) = database.infoClassesDao().addTransport(TransportEntity(id, name))

    suspend fun addAgregat(id: Int, name: String) = database.infoClassesDao().addAgregat(AgregatEntity(id, name))

    suspend fun addDepth(id: Int, name: String) = database.infoClassesDao().addDepth(DepthEntity(id, name))

    suspend fun addSpeed(id: Int, name: String) = database.infoClassesDao().addSpeed(SpeedEntity(id, name))

    suspend fun addWater(id: Int, name: String) = database.infoClassesDao().addWater(WaterEntity(id, name))

    fun getOperations() = database.infoClassesDao().getOperations()
    fun getFarmFields() = database.infoClassesDao().getFarmFields()
    fun getWorkMans() = database.infoClassesDao().getWorkMans()
    fun getTransports() = database.infoClassesDao().getTransports()
    fun getAgregats() = database.infoClassesDao().getAgregats()
    fun getDepths() = database.infoClassesDao().getDepths()
    fun getSpeeds() = database.infoClassesDao().getSpeeds()
    fun getWaters() = database.infoClassesDao().getWaters()

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

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
        }
    }
    return false
}