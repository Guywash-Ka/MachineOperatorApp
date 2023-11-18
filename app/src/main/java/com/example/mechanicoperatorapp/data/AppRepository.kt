package com.example.mechanicoperatorapp.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.mechanicoperatorapp.data.dataClasses.AgregatEntity
import com.example.mechanicoperatorapp.data.dataClasses.Agronom
import com.example.mechanicoperatorapp.data.dataClasses.AgronomEntity
import com.example.mechanicoperatorapp.data.dataClasses.DepthEntity
import com.example.mechanicoperatorapp.data.dataClasses.FarmFieldEntity
import com.example.mechanicoperatorapp.data.dataClasses.Fields
import com.example.mechanicoperatorapp.data.dataClasses.FieldsEntity
import com.example.mechanicoperatorapp.data.dataClasses.Operation
import com.example.mechanicoperatorapp.data.dataClasses.OperationEntity
import com.example.mechanicoperatorapp.data.dataClasses.RoleAndId
import com.example.mechanicoperatorapp.data.dataClasses.SpeedEntity
import com.example.mechanicoperatorapp.data.dataClasses.Tasks
import com.example.mechanicoperatorapp.data.dataClasses.TasksEntity
import com.example.mechanicoperatorapp.data.dataClasses.TasksModel
import com.example.mechanicoperatorapp.data.dataClasses.Templates
import com.example.mechanicoperatorapp.data.dataClasses.TemplatesEntity
import com.example.mechanicoperatorapp.data.dataClasses.TemplatesModel
import com.example.mechanicoperatorapp.data.dataClasses.TransportEntity
import com.example.mechanicoperatorapp.data.dataClasses.WaterEntity
import com.example.mechanicoperatorapp.data.dataClasses.WorkManEntity
import com.example.mechanicoperatorapp.data.dataClasses.WorkerEntity
import com.example.mechanicoperatorapp.data.database.MechanicDatabase
import com.example.mechanicoperatorapp.network.RetrofitInstance.API
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
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

    suspend fun addTask(id: Int, agronomId: Int, workerId: Int, templateId: Int, tasks: List<Int>) {
        database.tasksDao().addTask(TasksEntity(id, agronomId, workerId, templateId, tasks))
    }

    suspend fun addTemplateWithTitleAndFields(id: Int = 1, title: String, requiredFields: List<Int>) {
        database.templatesDao().addTemplate(TemplatesEntity(id, title, requiredFields))
    }

    fun getFieldById(id: Int) = database.fieldsDao().getFieldById(id)

    fun getFieldByName(name: String) = database.fieldsDao().getFieldByName(name)

    fun getFieldNameById(id: Int) = database.fieldsDao().getFieldNameById(id)

    fun getTemplateById(id: Int) = database.templatesDao().getTemplateById(id)

    fun getTemplateByTitle(title: String) = database.templatesDao().getTemplateByTitle(title)

    private fun parseRequiredFieldsIntoFieldsList(requiredFields: List<Int>): List<String> {
        val resList = MutableList(requiredFields.size){""}
        requiredFields.forEach { getFieldNameById(it).map { res -> resList[0] = res } }
        return resList
    }

    fun getTaskModelById(id: Int): Flow<TasksModel> {
        return database.tasksDao().getTaskById(id).map { task ->
            return@map combine(
                getAgronomNameById(task.agronomId),
                getWorkerNameById(task.workerId),
                getTemplateById(task.templateId)
            ) { agroName, workName, template ->
                val fieldsList = parseRequiredFieldsIntoFieldsList(template.requiredFields)
                return@combine TasksModel(id, agroName, workName, template.title, fieldsList)
            }.first()
        }
    }

    fun getAllTasksModel(): Flow<List<TasksModel>> {
        val resList = mutableListOf<TasksModel>()
        database.tasksDao().getAllIds().map { listId ->
            listId.forEach {
                getTaskModelById(it).map { task ->
                    resList.add(task)
                }
            }
        }
        Log.i("MYTAG", "hello ${resList.joinToString("|")}")
        return flowOf(resList)
    }

//    fun getAllTemplates() = flow {
//        emit(database.tasksDao().getAllTasks())
//        if (isOnline(context)) {
//            emit(API.getTasks())
//        }
//    }

//    fun getAllTemplatesModel(): Flow<List<TemplatesModel>> {
//        val resList = mutableListOf<TemplatesModel>()
//        database.templatesDao().getAllIds().map { id ->
//            getTemplateModelById(id).map { template ->
//                resList.add(template)
//            }
//        }
//        return flowOf(resList)
//    }

//    fun getAllFields() = flow {
//        emit(database.tasksDao().getAllTasks())
//        if (isOnline(context)) {
//            emit(API.getTasks())
//        }
//    }

    suspend fun addOperation(id: Int, name: String) = database.infoClassesDao().addOperation(OperationEntity(id, name))

    suspend fun addFarmField(id: Int, name: String) = database.infoClassesDao().addFarmField(FarmFieldEntity(id, name))

    suspend fun addWorkMan(id: Int, name: String) = database.infoClassesDao().addWorkMan(WorkManEntity(id, name))

    suspend fun addTransport(id: Int, name: String) = database.infoClassesDao().addTransport(TransportEntity(id, name))

    suspend fun addAgregat(id: Int, name: String) = database.infoClassesDao().addAgregat(AgregatEntity(id, name))

    suspend fun addDepth(id: Int, name: String) = database.infoClassesDao().addDepth(DepthEntity(id, name))

    suspend fun addSpeed(id: Int, name: String) = database.infoClassesDao().addSpeed(SpeedEntity(id, name))

    suspend fun addWater(id: Int, name: String) = database.infoClassesDao().addWater(WaterEntity(id, name))

    suspend fun addAgronom(id: Int, name: String, password: String, nfc: String) {
        database.agronomDao().addAgronom(AgronomEntity(id, name, password, nfc))
        if (isOnline(context)) {
            try {
                API.saveAgronom(name, password, nfc)
            } catch (e: Exception) {
                e.message?.let { Log.i("Internet connection", it) }
            }
        }
    }

    suspend fun addWorker(id: Int, name: String, password: String, nfc: String) {
        database.workerDao().addWorker(WorkerEntity(id, name, password, nfc))
        if (isOnline(context)) {
            try {
                API.saveWorker(name, password, nfc)
            } catch (e: Exception) {
                e.message?.let { Log.i("No internet", it) }
            }
        }
    }

    fun getOperations() = flow {
        val localData = database.infoClassesDao().getOperations()
        emit(localData)
        if (isOnline(context)) {
            val remoteData = Gson().fromJson(API.getAllOperations().body()!!.string(), Array<OperationEntity>::class.java).toList()
            emit(remoteData)
        }
    }

    fun getFarmFields() = flow {
        val localData = database.infoClassesDao().getFarmFields()
        emit(localData)
        if (isOnline(context)) {
            val remoteData = Gson().fromJson(API.getAllFarmFields().body()!!.string(), Array<FarmFieldEntity>::class.java).toList()
            emit(remoteData)
        }
    }

    fun getWorkMans() = flow {
        val localData = database.infoClassesDao().getWorkMans()
        emit(localData)
        if (isOnline(context)) {
            val remoteData = Gson().fromJson(API.getAllWorkers().body()!!.string(), Array<WorkManEntity>::class.java).toList()
            emit(remoteData)
        }
    }

    fun getTransports() = flow {
        val localData = database.infoClassesDao().getTransports()
        emit(localData)
        if (isOnline(context)) {
            val remoteData = Gson().fromJson(API.getAllTransport().body()!!.string(), Array<TransportEntity>::class.java).toList()
            emit(remoteData)
        }
    }

    fun getAgregats() = flow {
        val localData = database.infoClassesDao().getAgregats()
        emit(localData)
        if (isOnline(context)) {
            val remoteData = Gson().fromJson(API.getAllAgregats().body()!!.string(), Array<AgregatEntity>::class.java).toList()
            emit(remoteData)
        }
    }

    fun getWaters() = flow {
        val localData = database.infoClassesDao().getWaters()
        emit(localData)
        if (isOnline(context)) {
            val remoteData = Gson().fromJson(API.getAllWaters().body()!!.string(), Array<WaterEntity>::class.java).toList()
            emit(remoteData)
        }
    }

    fun getTasks() = flow {
        val localData = database.tasksDao().getAllTasks().toList()
        emit(localData)
        if (isOnline(context)) {
            val remoteData = Gson().fromJson(API.getAllTasks().body()!!.string(), Array<Tasks>::class.java).toList()
            emit(remoteData)
        }
    }

    fun getAgronoms() = flow {
        val localData = database.agronomDao().getAllAgronoms()
        emit(localData)
        if (isOnline(context)) {
            val remoteData = Gson().fromJson(API.getAllAgronoms().body()!!.string(), Array<AgronomEntity>::class.java).toList()
            emit(remoteData)
        }
    }

    fun getTemplates() = flow {
        val resLocalData = mutableListOf<TemplatesModel>()
        val localData = database.templatesDao().getAllTemplates()
        localData.forEach { temp ->
            resLocalData.add(TemplatesModel(temp.id, temp.title, parseRequiredFieldsIntoFieldsList(temp.requiredFields)))
        }
        emit(resLocalData)
        if (isOnline(context)) {
            val remoteData = Gson().fromJson(API.getAllTemplates().body()!!.string(), Array<Templates>::class.java).toList()
            val resRemoteData = mutableListOf<TemplatesModel>()
            remoteData.forEach { temp ->
                resRemoteData.add(TemplatesModel(temp.id, temp.title, parseRequiredFieldsIntoFieldsList(temp.requiredFields)))
            }
            emit(resRemoteData)
        }
    }

    fun getFields() = flow {
        val localData = database.fieldsDao().getAllFields()
        emit(localData)
        if (isOnline(context)) {
            val remoteData = Gson().fromJson(API.getAllTaskFields().body()!!.string(), Array<Fields>::class.java).toList()
            emit(remoteData)
        }
    }

    suspend fun getLastTaskId(): Int {
        return try { API.getLastTaskId().body()!!.string().toInt() } catch (e: Exception) { -1 }
    }

    fun getAgronomNameById(id: Int) = database.agronomDao().getAgronomNameById(id)
    fun getWorkerNameById(id: Int) = database.workerDao().getWorkerNameById(id)

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