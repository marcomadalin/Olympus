package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcomadalin.olympus.domain.model.Exercise


@Entity(tableName = "Exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,
    var workoutId : Long = 0,
    var routineId : Long = 0,
    var exerciseDataId : Long = 0,
    var restTime : Long = 0,
    var note : String = "",
    var exerciseNumber : Int = 0
)
fun Exercise.toData() = ExerciseEntity(
    id,
    workoutId,
    routineId,
    exerciseDataId,
    restTime.seconds,
    note,
    exerciseNumber
)
