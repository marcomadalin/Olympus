package com.marcomadalin.olympus.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object MapConverters {
    @TypeConverter
    fun fromString(value: String): Map<Int,Int> {
        val mapType: Type = object : TypeToken<Map<Int,Int>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromSet(map: Map<Int,Int>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}