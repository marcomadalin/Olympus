package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
open class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String,
    val totalWorkouts : Int,
    val trackingTotalWorkouts : Boolean,
    val trackedExercises : Set<Int>,
    val trackedMeasures : Set<Int>,
)