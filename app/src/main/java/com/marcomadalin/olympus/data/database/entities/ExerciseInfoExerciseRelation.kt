package com.marcomadalin.olympus.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "ExerciseInfoExerciseRelation")
data class ExerciseInfoExerciseRelation(
    @Embedded
    val exercise: ExerciseInfoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseInfoId"
    )
    val exercises : ArrayList<ExerciseEntity>
)