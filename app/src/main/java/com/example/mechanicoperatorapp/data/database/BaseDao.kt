package com.example.mechanicoperatorapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mechanicoperatorapp.data.dataClasses.Base
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

@Dao
interface BaseDao {

    @Query("SELECT * FROM Base")
    fun get(): Flow<List<Base>>

    @Insert
    suspend fun add(a: Base)
}