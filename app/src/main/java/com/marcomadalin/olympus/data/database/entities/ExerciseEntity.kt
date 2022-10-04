package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Duration


@Entity(tableName = "Exercise")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val routineId : Int,
    val exerciseInfoId : Int,
    val restTime : Duration,
    val note : String,
    val exerciseNumber : Int
)