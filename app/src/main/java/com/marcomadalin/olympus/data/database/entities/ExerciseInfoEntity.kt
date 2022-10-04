package com.marcomadalin.olympus.data.database.entities

import android.media.Image
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ExerciseInfo")
data class ExerciseInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val userId : Int,
    val type : Int,
    val favourite : Boolean,
    val equipment : Int,
    val primaryMuscle : Int,
    val secondaryMuscles : Set<Int>,
    val maxWeight : Double,
    val orm : Double,
    val bestSetWeight : Double,
    val bestSetReps : Int,
    val image : Image
)