package com.example.mechanicoperatorapp.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.work.Worker
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkerDao {
    @Query("SELECT name FROM Worker WHERE id=:id")
    fun getWorkerNameById(id: Int): Flow<String>
}