package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcomadalin.olympus.domain.model.Routine

@Entity(tableName = "Routines")
open class RoutineEntity (
    @PrimaryKey(autoGenerate = true)
    open var id : Int = 0,
    open var userId : Int = 0,
    open var name : String = "",
    open var note : String = "",
) {
    fun Routine.toData() = RoutineEntity(
        id,
        userId,
        name,
        note
    )
}