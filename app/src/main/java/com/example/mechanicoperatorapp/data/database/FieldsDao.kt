package com.example.mechanicoperatorapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mechanicoperatorapp.data.dataClasses.Fields
import com.example.mechanicoperatorapp.data.dataClasses.FieldsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addField(field: FieldsEntity)

    @Query("SELECT * FROM Fields WHERE id=:id")
    fun getFieldById(id: Int): Flow<Fields>

    @Query("SELECT * FROM Fields WHERE name=:name")
    fun getFieldByName(name: String): Flow<Fields>

    @Query("SELECT * FROM Fields")
    suspend fun getAllFields(): List<Fields>

    @Query("SELECT name FROM Fields WHERE id=:id")
    fun getFieldNameById(id: Int): Flow<String>

    @Update
    suspend fun updateField(field: FieldsEntity)
}
