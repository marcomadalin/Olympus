package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.marcomadalin.olympus.data.database.MapConverters


@Entity(tableName = "Statistics")
data class StatisticEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var userId : Int = 0,
    var timeframe : Int = 0,
    var totalWorkouts : Int = 0,
    var totalRestDays : Int = 0,
    var totalVolume : Double = 0.0,
    var totalReps : Int = 0,
    var totalWorkoutLength : Long = 0,
    var averageWorkoutVolume : Double = 0.0,
    var averageWorkoutReps : Double = 0.0,
    var averageWorkoutLength : Long = 0,
    @TypeConverters(MapConverters::class)
    var muscleDivision : Map<Int,Int> = emptyMap()
)