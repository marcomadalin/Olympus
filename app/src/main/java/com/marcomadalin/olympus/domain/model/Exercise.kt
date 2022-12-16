package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.ExerciseEntity
import java.time.Duration


data class Exercise(
    var id : Long = 0,
    var name : String = "",
    var workoutId : Long = 0,
    var routineId : Long = 0,
    var exerciseDataId : Long = 0,
    var restTime : Duration = Duration.ofSeconds(0),
    var note : String = "",
    var exerciseNumber : Int = 0,
    var sets: MutableList<Set> = mutableListOf()
) {
    constructor(e : Exercise) : this (0, e.name, 0, 0, e.exerciseDataId, e.restTime, e.note, e.exerciseNumber, e.sets.map{Set(it)}.toMutableList())
}

fun ExerciseEntity.toDomain() = Exercise(
    id,
    "",
    workoutId,
    routineId,
    exerciseDataId,
    Duration.ofSeconds(restTime),
    note,
    exerciseNumber
)