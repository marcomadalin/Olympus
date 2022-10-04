package com.marcomadalin.olympus.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "UserExerciseInfoRelation")
data class UserExerciseInfoRelation (
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val exercises : ArrayList<ExerciseInfoEntity>
)