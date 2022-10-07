package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity

@Entity(tableName = "Workouts")
data class WorkoutEntity (
    override var name : String = "",
    override var note : String = "",
    var length : String = "",
    var date : String = "",
) : RoutineEntity (name = name, note = note)