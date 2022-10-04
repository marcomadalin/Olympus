package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Set")
data class SetEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val exerciseId : Int,
    val weight : Int,
    val reps : Int,
    val rir : Int,
    val lastWeight : Int,
    val lastReps : Int,
    val type : Int,
    val setNumber : Int,
)