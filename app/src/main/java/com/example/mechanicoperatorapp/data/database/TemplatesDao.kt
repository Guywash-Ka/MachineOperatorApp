package com.example.mechanicoperatorapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mechanicoperatorapp.data.dataClasses.Templates
import com.example.mechanicoperatorapp.data.dataClasses.TemplatesEntity

@Dao
interface TemplatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTemplate(template: TemplatesEntity)

    @Query("SELECT * FROM Templates WHERE id=:id")
    fun getTemplateById(id: Int): Templates

    @Query("SELECT * FROM Templates WHERE title=:title")
    fun getTemplateByTitle(title: String): Templates
}