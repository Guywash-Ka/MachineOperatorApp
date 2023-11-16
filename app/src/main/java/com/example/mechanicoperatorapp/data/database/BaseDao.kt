package com.example.mechanicoperatorapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

@Dao
interface BaseDao {
    @GET
    fun get(): Flow<List<Int>>

    @Insert
    suspend fun add(a: Int)
}