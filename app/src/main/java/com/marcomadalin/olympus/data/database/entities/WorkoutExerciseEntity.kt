package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Duration


@Entity(tableName = "WorkoutExercise")
data class WorkoutExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val restTime : Duration,
    val note : String,
    val exerciseNumber : Int
)