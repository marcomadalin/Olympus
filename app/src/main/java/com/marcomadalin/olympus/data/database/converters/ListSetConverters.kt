package com.marcomadalin.olympus.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object ListSetConverters {
    @TypeConverter
    fun fromString(value: String): List<Set<Long>> {
        val listType: Type = object : TypeToken<List<Set<Long>>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListSet(list: List<Set<Long>>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}