package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import com.marcomadalin.olympus.domain.model.Workout

@Entity(tableName = "Workouts")
data class WorkoutEntity (
    var length : Long = 0,
    var date : String = "",
) : RoutineEntity ()
fun Workout.toData() = WorkoutEntity(
    length.seconds,
    date.toString()
)