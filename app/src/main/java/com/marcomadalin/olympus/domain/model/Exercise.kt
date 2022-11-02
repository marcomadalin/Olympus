package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.ExerciseEntity
import java.time.Duration


data class Exercise(
    var id : Long = 0,
    var workoutId : Long = 0,
    var exerciseDataId : Long = 0,
    var restTime : Duration = Duration.ofSeconds(0),
    var note : String = "",
    var exerciseNumber : Int = 0,
    var sets: MutableList<Set> = mutableListOf()
)

fun ExerciseEntity.toDomain() = Exercise(
    id,
    workoutId,
    exerciseDataId,
    Duration.ofSeconds(restTime),
    note,
    exerciseNumber
)