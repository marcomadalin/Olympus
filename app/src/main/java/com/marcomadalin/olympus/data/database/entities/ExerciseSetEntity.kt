package com.marcomadalin.olympus.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation


@Entity(tableName = "WorkoutExerciseSet")
data class ExerciseSetEntity(
    @Embedded val exercise: WorkoutExerciseEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "workoutExerciseId"
    )
    val sets : ArrayList<WorkoutSetEntity>
)