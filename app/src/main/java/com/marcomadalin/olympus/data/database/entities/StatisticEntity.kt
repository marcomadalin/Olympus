package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.marcomadalin.olympus.data.database.converters.MapConverters
import com.marcomadalin.olympus.domain.model.Statistic


@Entity(tableName = "Statistics")
data class StatisticEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,
    var userId : Long = 0,
    var timeframe : String = "",
    var totalWorkouts : Int = 0,
    var totalRestDays : Int = 0,
    var totalVolume : Double = 0.0,
    var totalReps : Int = 0,
    var totalWorkoutLength : Long = 0,
    var averageWorkoutVolume : Double = 0.0,
    var averageWorkoutReps : Double = 0.0,
    var averageWorkoutLength : Long = 0,
    @TypeConverters(MapConverters::class)
    var muscleDivision : Map<String,Int> = emptyMap()
)
fun Statistic.toData() = StatisticEntity(
    id,
    userId,
    timeframe.toString(),
    totalWorkouts,
    totalRestDays,
    totalVolume,
    totalReps,
    totalWorkoutLength.seconds,
    averageWorkoutVolume,
    averageWorkoutReps,
    averageWorkoutLength,
    muscleDivision.mapKeys{ it -> it.key.toString() }
)