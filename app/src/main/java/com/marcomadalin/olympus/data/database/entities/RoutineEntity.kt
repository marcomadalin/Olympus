package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Routine")
open class RoutineEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    open val userId : Int,
    open val name : String,
    open val note : String,
)