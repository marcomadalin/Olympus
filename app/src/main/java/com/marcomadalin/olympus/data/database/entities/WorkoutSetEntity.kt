package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "WorkoutSet")
data class WorkoutSetEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val workoutExerciseId : Int,
    val weight : Int,
    val reps : Int,
    val rir : Int,
    val lastWeight : Int,
    val lastReps : Int,
    val type : Int,
    val setNumber : Int,
)