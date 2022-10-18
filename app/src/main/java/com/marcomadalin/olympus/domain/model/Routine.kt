package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.RoutineEntity

open class Routine (
    open var id : Long = 0,
    open var userId : Long = 0,
    open var name : String = "",
    open var note : String = "",
)
fun RoutineEntity.toDomain() = Routine(
    id,
    userId,
    name,
    note
)
