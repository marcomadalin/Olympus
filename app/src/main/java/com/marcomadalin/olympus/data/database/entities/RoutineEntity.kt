package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Routines")
open class RoutineEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    open var userId : Int = 0,
    open var name : String = "",
    open var note : String = "",
)