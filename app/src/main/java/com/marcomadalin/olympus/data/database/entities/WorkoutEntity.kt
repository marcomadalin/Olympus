package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import java.util.*

@Entity(tableName = "Workout")
data class WorkoutEntity (
    override val name : String,
    override val note : String,
    override val userId : Int,
    val length : String,
    val date : Date,
) : RoutineEntity (name = name, note = note, userId = userId)