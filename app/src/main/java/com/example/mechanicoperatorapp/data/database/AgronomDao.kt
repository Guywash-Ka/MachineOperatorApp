package com.example.mechanicoperatorapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mechanicoperatorapp.data.dataClasses.Agronom
import com.example.mechanicoperatorapp.data.dataClasses.AgronomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AgronomDao {
    @Query("SELECT name FROM Agronom WHERE id=:id")
    fun getAgronomNameById(id: Int): Flow<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAgronom(agronom: AgronomEntity)

    @Query("SELECT * FROM Agronom")
    fun getAllAgronoms(): List<AgronomEntity>
}