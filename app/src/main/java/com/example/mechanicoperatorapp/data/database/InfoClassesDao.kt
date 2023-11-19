package com.example.mechanicoperatorapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
    suspend fun getOperations(): List<OperationEntity>

    @Query("SELECT * FROM Water")
    suspend fun getWaters(): List<WaterEntity>

    @Query("SELECT * FROM FarmField")
    suspend fun getFarmFields(): List<FarmFieldEntity>

    @Query("SELECT * FROM WorkMan")
    suspend fun getWorkMans(): List<WorkManEntity>

    @Query("SELECT * FROM Transport")
    suspend fun getTransports(): List<TransportEntity>

    @Query("SELECT * FROM Agregat")
    suspend fun getAgregats(): List<AgregatEntity>

    @Query("SELECT * FROM Depth")
    suspend fun getDepths(): List<DepthEntity>

    @Query("SELECT * FROM Speed")
    suspend fun getSpeeds(): List<SpeedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOperations(operation: OperationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWaters(water: WaterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFarmFields(farmField: FarmFieldEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWorkMans(workMan: WorkManEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransports(transport: TransportEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAgregats(agregat: AgregatEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDepths(depth: DepthEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSpeeds(speed: SpeedEntity)

}