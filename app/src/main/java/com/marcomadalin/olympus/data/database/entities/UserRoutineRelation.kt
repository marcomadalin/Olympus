package com.marcomadalin.olympus.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "UserRoutineRelation")
data class UserRoutineRelation (
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val routines : ArrayList<RoutineEntity>
)