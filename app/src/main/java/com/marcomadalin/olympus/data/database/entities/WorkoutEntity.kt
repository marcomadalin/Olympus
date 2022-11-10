package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.marcomadalin.olympus.data.database.converters.ListSetConverters
import com.marcomadalin.olympus.domain.model.Workout

@Entity(tableName = "Workouts")
data class WorkoutEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,
    var userId : Long = 0,
    var name : String = "",
    var note : String = "",
    var length : Long = 0,
    var date : String = "",
    @TypeConverters(ListSetConverters::class)
    var supersets : List<Set<Long>> = emptyList()
)
fun Workout.toData() = WorkoutEntity(
    id,
    userId,
    name,
    note,
    length.seconds,
    date.toString(),
    supersets
)