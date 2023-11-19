package com.example.mechanicoperatorapp.network

import com.example.mechanicoperatorapp.data.dataClasses.Tasks
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface API {
    @GET("/getRoleAndIdByNfc/{nfc}")
    suspend fun getUserByNfc(@Path("nfc") nfc: String): Response<ResponseBody>

    @POST("/saveWorker?name={name}&password={password}&nfc={nfc}")
    suspend fun saveWorker(@Path("name") name: String, @Path("password") password: String, @Path("nfc") nfc: String)

    @POST("/saveAgronom?name={name}&password={password}&nfc={nfc}")
    suspend fun saveAgronom(@Path("name") name: String, @Path("password") password: String, @Path("nfc") nfc: String)

    @GET("/getRoleAndIdByPassword/{password}")
    suspend fun getUserByPassword(@Path("password") password: String): Response<ResponseBody>

    @GET("/getTaskFields")
    suspend fun getFields(): Response<ResponseBody>

    @GET("/getTaskFields/{id}")
    suspend fun getFieldById(@Path("id") id: Int): Response<ResponseBody>

    @GET("/getTemplates")
    suspend fun getTemplates(): Response<ResponseBody>

    @GET("/getTemplates/{id}")
    suspend fun getTemplateById(@Path("id") id: Int): Response<ResponseBody>

    @POST("/saveTask")
    suspend fun saveTask(@Body task: Tasks): Response<ResponseBody>

    @GET("/getLastTaskId")
    suspend fun getLastTaskId(): Response<ResponseBody>

    @GET("/getTaskByTaskId/{id}")
    suspend fun getTaskById(@Path("id") id: Int): Response<ResponseBody>

    @GET("/getAllAfterId/{id}")
    suspend fun getAllAfterId(@Path("id") id: Int): Response<ResponseBody>

    @GET("/getAll/Agronoms")
    suspend fun getAllAgronoms(): Response<ResponseBody>

    @GET("/getAll/Agregats")
    suspend fun getAllAgregats(): Response<ResponseBody>

    @GET("/getAll/FarmFields")
    suspend fun getAllFarmFields(): Response<ResponseBody>

    @GET("/getAll/Operations")
    suspend fun getAllOperations(): Response<ResponseBody>

    @GET("/getAll/TaskFields")
    suspend fun getAllTaskFields(): Response<ResponseBody>

    @GET("/getAll/Tasks")
    suspend fun getAllTasks(): Response<ResponseBody>

    @GET("/getTemplatesRodya")
    suspend fun getAllTemplates(): Response<ResponseBody>

    @GET("/getAll/Transport")
    suspend fun getAllTransport(): Response<ResponseBody>

    @GET("/getAll/Waters")
    suspend fun getAllWaters(): Response<ResponseBody>

    @GET("/getAll/Workers")
    suspend fun getAllWorkers(): Response<ResponseBody>
}
