package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Statistic")
data class StatisticEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val userId : Int,
    val timeframe : Int,
    val totalWorkouts : Int,
    val totalRestDays : Int,
    val totalVolume : Double,
    val totalReps : Int,
    val totalWorkoutLength : Long,
    val averageWorkoutVolume : Double,
    val averageWorkoutReps : Double,
    val averageWorkoutLength : Long,
    val muscleDivision : Map<Int,Int>
)