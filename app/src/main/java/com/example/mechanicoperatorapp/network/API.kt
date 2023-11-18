package com.example.mechanicoperatorapp.network

import okhttp3.ResponseBody
import retrofit2.Response
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
}