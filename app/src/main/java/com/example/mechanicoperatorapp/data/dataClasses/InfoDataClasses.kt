package com.example.mechanicoperatorapp.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Operation")
data class OperationEntity(
    @PrimaryKey val id: Int,
    val name: String,
)

data class Operation(
    val id: Int,
    val name: String,
)

@Entity(tableName = "FarmField")
data class FarmFieldEntity(
    @PrimaryKey val id: Int,
    val name: String,
)

data class FarmField(
    val id: Int,
    val name: String,
)

@Entity(tableName = "WorkMan")
data class WorkManEntity(
    @PrimaryKey val id: Int,
    val name: String,
)

data class WorkMan(
    val id: Int,
    val name: String,
)

@Entity(tableName = "Transport")
data class TransportEntity(
    @PrimaryKey val id: Int,
    val name: String,
)

data class Transport(
    val id: Int,
    val name: String,
)

@Entity(tableName = "Agregat")
data class AgregatEntity(
    @PrimaryKey val id: Int,
    val name: String
)

data class Agregat(
    val id: Int,
    val name: String
)

@Entity(tableName = "Depth")
data class DepthEntity(
    @PrimaryKey val id: Int,
    val name: String,
)

data class Depth(
    val id: Int,
    val name: String,
)

@Entity(tableName = "Speed")
data class SpeedEntity(
    @PrimaryKey val id: Int,
    val name: String,
)

data class Speed(
    val id: Int,
    val name: String,
)

@Entity(tableName = "Water")
data class WaterEntity(
    @PrimaryKey val id: Int,
    val name: String,
)

data class Water(
    val id: Int,
    val name: String,
)
