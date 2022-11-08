package com.marcomadalin.olympus.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object ListSetConverters {
    @TypeConverter
    fun fromString(value: String): List<Set<Int>> {
        val listType: Type = object : TypeToken<List<Set<Int>>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListSet(list: List<Set<Int>>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}