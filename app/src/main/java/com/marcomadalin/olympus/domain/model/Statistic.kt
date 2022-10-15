package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.StatisticEntity
import com.marcomadalin.olympus.domain.model.enums.Muscle
import com.marcomadalin.olympus.domain.model.enums.StatisticTimeframe
import java.time.Duration


data class Statistic(
    var id : Int = 0,
    var userId : Int = 0,
    var timeframe : StatisticTimeframe = StatisticTimeframe.month,
    var totalWorkouts : Int = 0,
    var totalRestDays : Int = 0,
    var totalVolume : Double = 0.0,
    var totalReps : Int = 0,
    var totalWorkoutLength : Duration = Duration.ofSeconds(0),
    var averageWorkoutVolume : Double = 0.0,
    var averageWorkoutReps : Double = 0.0,
    var averageWorkoutLength : Long = 0,
    var muscleDivision : Map<Muscle,Int> = emptyMap()
)
fun StatisticEntity.toDomain() = Statistic(
    id,
    userId,
    StatisticTimeframe.valueOf(timeframe),
    totalWorkouts,
    totalRestDays,
    totalVolume,
    totalReps,
    Duration.ofSeconds(totalWorkoutLength),
    averageWorkoutVolume,
    averageWorkoutReps,
    averageWorkoutLength,
    muscleDivision.mapKeys{ it -> Muscle.valueOf(it.key) }
)