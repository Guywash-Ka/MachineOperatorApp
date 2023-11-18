package com.example.mechanicoperatorapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mechanicoperatorapp.data.dataClasses.Templates
import com.example.mechanicoperatorapp.data.dataClasses.TemplatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTemplate(template: TemplatesEntity)

    @Query("SELECT * FROM Templates WHERE id=:id")
    fun getTemplateById(id: Int): Flow<Templates>

    @Query("SELECT * FROM Templates WHERE title=:title")
    fun getTemplateByTitle(title: String): Flow<Templates>

    @Query("SELECT * FROM Templates")
    fun getAllTemplates(): Flow<List<Templates>>

    @Query("SELECT id FROM Templates")
    fun getAllIds(): Flow<List<Int>>
}
