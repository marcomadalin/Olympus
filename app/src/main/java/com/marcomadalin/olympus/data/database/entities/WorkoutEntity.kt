package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.marcomadalin.olympus.data.database.converters.ListSetConverters
import com.marcomadalin.olympus.domain.model.Workout

@Entity(tableName = "Workouts")
data class WorkoutEntity (
    @PrimaryKey(autoGenerate = true)
    override var id : Long = 0,
    override var userId : Long = 0,
    override var name : String = "",
    override var note : String = "",
    var length : Long = 0,
    var date : String = "",
    @TypeConverters(ListSetConverters::class)
    override var supersets : List<Set<Long>> = emptyList(),
    var routineId : Long = -1,
    var isLive : Boolean = true,
) : RoutineEntity()
fun Workout.toData() = WorkoutEntity(
    id,
    userId,
    name,
    note,
    length.seconds,
    date.toString(),
    supersets,
    routineId,
    isLive
)