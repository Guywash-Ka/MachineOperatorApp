package com.example.mechanicoperatorapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mechanicoperatorapp.data.dataClasses.Tasks
import com.example.mechanicoperatorapp.data.dataClasses.TasksEntity
import com.example.mechanicoperatorapp.data.dataClasses.TasksModel
import com.example.mechanicoperatorapp.data.dataClasses.TasksSQLModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(tasks: TasksEntity)

    @Query("SELECT * FROM Tasks WHERE id=:id")
    fun getTaskById(id: Int): Flow<Tasks>

    @Query("SELECT * FROM Tasks")
    suspend fun getAllTasks(): List<Tasks>

    @Query("SELECT id FROM Tasks")
    fun getAllIds(): Flow<List<Int>>

    @Query(
            "SELECT Tasks.id, Agronom.name as agronomName, Worker.name as workerName, " +
                    "Templates.title as templateName, Tasks.json as valueList  FROM Tasks " +
                    "JOIN Templates ON Tasks.templateId = Templates.id " +
                    "JOIN Agronom ON Tasks.agronomId = Agronom.id " +
                    "JOIN Worker ON Tasks.workerId = Worker.id"
    )
    fun getAllTasksModel(): Flow<List<TasksSQLModel>>
}
