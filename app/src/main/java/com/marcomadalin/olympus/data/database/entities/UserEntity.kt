package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.marcomadalin.olympus.data.database.converters.SetConverters

@Entity(tableName = "Users")
open class UserEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var name : String = "",
    var totalWorkouts : Int = 0,
    var trackingTotalWorkouts : Boolean = false,
    @TypeConverters(SetConverters::class)
    var trackedExercises : Set<Int> = emptySet(),
    @TypeConverters(SetConverters::class)
    var trackedMeasures : Set<Int> = emptySet(),
)