package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.RoutineEntity

open class Routine (
    open var id : Long = 0,
    open var userId : Long = 0,
    open var name : String = "",
    open var note : String = "",
    open var exercises : MutableList<Exercise> = mutableListOf(),
    open var supersets : MutableList<MutableSet<Long>> = mutableListOf()
) {
    constructor(r : Routine) : this(0, 0, r.name + " (Copy)", r.note, r.exercises.map{Exercise(it)}.toMutableList(), r.supersets.map { mutableSetOf<Long>() }.toMutableList())
}
fun RoutineEntity.toDomain() = Routine(
    id,
    userId,
    name,
    note,
    supersets = supersets.map { it.toMutableSet() }.toMutableList()
)
