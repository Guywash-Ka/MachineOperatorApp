package com.example.mechanicoperatorapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mechanicoperatorapp.data.dataClasses.Tasks
import com.example.mechanicoperatorapp.data.dataClasses.TasksEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(tasks: TasksEntity)

    @Query("SELECT * FROM Tasks WHERE id=:id")
    fun getTaskById(id: Int): Flow<Tasks>

    @Query("SELECT * FROM Tasks")
    fun getAllTasks(): Flow<List<Tasks>>

    @Query("SELECT id FROM Tasks")
    fun getAllIds(): Flow<Int>
}
