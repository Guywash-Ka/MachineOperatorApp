package com.example.mechanicoperatorapp.data.dataClasses

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun tasksToJson(tasks: List<Int>) = gson.toJson(tasks)

    @TypeConverter
    fun jsonToTasks(json: String): List<Int> {
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(json, type)
    }
}