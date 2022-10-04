package com.marcomadalin.olympus.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "UserMeasureRelation")
data class UserMeasureRelation (
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val measures : ArrayList<MeasureEntity>
)