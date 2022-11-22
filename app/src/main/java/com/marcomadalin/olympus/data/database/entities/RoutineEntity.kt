package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.marcomadalin.olympus.data.database.converters.ListSetConverters
import com.marcomadalin.olympus.domain.model.Routine

@Entity(tableName = "Routines")
open class RoutineEntity (
    @PrimaryKey(autoGenerate = true)
    open var id : Long = 0,
    open var userId : Long = 0,
    open var name : String = "",
    open var note : String = "",
    @TypeConverters(ListSetConverters::class)
    open var supersets : List<Set<Long>> = emptyList()
)
fun Routine.toData() = RoutineEntity(
    id,
    userId,
    name,
    note,
    supersets
)