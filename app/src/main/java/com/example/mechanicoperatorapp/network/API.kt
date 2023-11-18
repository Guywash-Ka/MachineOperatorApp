package com.example.mechanicoperatorapp.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface API {
    @GET("/getByNfc/{nfc}")
    suspend fun getUserByNfc(@Path("nfc") nfc: String): Response<ResponseBody>
}