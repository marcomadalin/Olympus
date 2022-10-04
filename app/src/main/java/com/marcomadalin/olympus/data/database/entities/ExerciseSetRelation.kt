package com.marcomadalin.olympus.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation


@Entity(tableName = "ExerciseSet")
data class ExerciseSetRelation(
    @Embedded val
    exercise: ExerciseEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseId"
    )
    val sets : ArrayList<SetEntity>
)