package com.example.mechanicoperatorapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mechanicoperatorapp.data.dataClasses.Agregat
import com.example.mechanicoperatorapp.data.dataClasses.AgregatEntity
import com.example.mechanicoperatorapp.data.dataClasses.DepthEntity
import com.example.mechanicoperatorapp.data.dataClasses.FarmFieldEntity
import com.example.mechanicoperatorapp.data.dataClasses.OperationEntity
import com.example.mechanicoperatorapp.data.dataClasses.SpeedEntity
import com.example.mechanicoperatorapp.data.dataClasses.TransportEntity
import com.example.mechanicoperatorapp.data.dataClasses.WaterEntity
import com.example.mechanicoperatorapp.data.dataClasses.WorkManEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InfoClassesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOperation(operation: OperationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFarmField(farmField: FarmFieldEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkMan(workMan: WorkManEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTransport(transport: TransportEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAgregat(agregat: AgregatEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDepth(depth: DepthEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSpeed(speed: SpeedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWater(water: WaterEntity)

    @Query("SELECT * FROM Operation")
    fun getOperations(): Flow<List<OperationEntity>>

    @Query("SELECT * FROM Water")
    fun getWaters(): Flow<List<WaterEntity>>

    @Query("SELECT * FROM FarmField")
    fun getFarmFields(): Flow<List<FarmFieldEntity>>

    @Query("SELECT * FROM WorkMan")
    fun getWorkMans(): Flow<List<WorkManEntity>>

    @Query("SELECT * FROM Transport")
    fun getTransports(): Flow<List<TransportEntity>>

    @Query("SELECT * FROM Agregat")
    fun getAgregats(): Flow<List<AgregatEntity>>

    @Query("SELECT * FROM Depth")
    fun getDepths(): Flow<List<DepthEntity>>

    @Query("SELECT * FROM Speed")
    fun getSpeeds(): Flow<List<SpeedEntity>>
}