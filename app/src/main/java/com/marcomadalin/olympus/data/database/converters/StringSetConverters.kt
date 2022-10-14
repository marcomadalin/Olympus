package com.marcomadalin.olympus.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


object StringSetConverters {
    @TypeConverter
    fun fromString(value: String): Set<String> {
        val setType: Type = object : TypeToken<Set<String>>() {}.type
        return Gson().fromJson(value, setType)
    }

    @TypeConverter
    fun fromSet(set: Set<String>): String {
        val gson = Gson()
        return gson.toJson(set)
    }
}