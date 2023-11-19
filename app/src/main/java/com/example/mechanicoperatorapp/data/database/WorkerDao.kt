package com.example.mechanicoperatorapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.work.Worker
import com.example.mechanicoperatorapp.data.dataClasses.WorkerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkerDao {
    @Query("SELECT name FROM Worker WHERE id=:id")
    fun getWorkerNameById(id: Int): Flow<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorker(worker: WorkerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWorker(worker: WorkerEntity)
}