package com.marcomadalin.olympus.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "RoutineExercise")
data class RoutineExerciseRelation(
    @Embedded val routine: RoutineEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "routineId"
    )
    val exercises : ArrayList<ExerciseEntity>
)