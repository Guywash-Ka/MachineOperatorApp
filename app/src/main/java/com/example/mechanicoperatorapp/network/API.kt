package com.example.mechanicoperatorapp.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface API {
    @GET("")
    suspend fun get(): Response<ResponseBody>
}