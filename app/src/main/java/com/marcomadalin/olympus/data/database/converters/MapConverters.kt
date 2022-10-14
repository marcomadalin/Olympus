package com.marcomadalin.olympus.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object MapConverters {
    @TypeConverter
    fun fromString(value: String): Map<String,Int> {
        val mapType: Type = object : TypeToken<Map<String,Int>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromSet(map: Map<String,Int>): String {
        val gson = Gson()
        return gson.toJson(map)
    }
}