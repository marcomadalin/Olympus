package com.marcomadalin.olympus.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object SetConverters {
    @TypeConverter
    fun fromString(value: String): Set<Int> {
        val setType: Type = object : TypeToken<Set<Int>>() {}.type
        return Gson().fromJson(value, setType)
    }

    @TypeConverter
    fun fromSet(set: Set<Int>): String {
        val gson = Gson()
        return gson.toJson(set)
    }
}