package com.example.mechanicoperatorapp.data.database

import androidx.room.Dao
import androidx.room.Query
import com.example.mechanicoperatorapp.data.dataClasses.Agronom
import kotlinx.coroutines.flow.Flow

@Dao
interface AgronomDao {
    @Query("SELECT name FROM Agronom WHERE id=:id")
    fun getAgronomNameById(id: Int): Flow<String>
}