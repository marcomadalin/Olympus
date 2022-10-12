package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.ExerciseEntity
import java.time.Duration


data class Exercise(
    val id : Int = 0,
    val routineId : Int = 0,
    val exerciseDataId : Int = 0,
    var restTime : Duration = Duration.ofSeconds(0),
    var note : String = "",
    var exerciseNumber : Int = 0
)

fun ExerciseEntity.toDomain() = Exercise(
    id,
    routineId,
    exerciseDataId,
    Duration.ofSeconds(restTime),
    note,
    exerciseNumber
)