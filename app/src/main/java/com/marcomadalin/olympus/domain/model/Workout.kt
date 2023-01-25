package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.WorkoutEntity
import java.time.Duration
import java.time.LocalDate

data class Workout (
    override var id : Long = 0,
    override var userId : Long = 0,
    override var name : String = "New workout",
    override var note : String = "",
    var length : Duration = Duration.ofSeconds(0),
    var date : LocalDate = LocalDate.now(),
    override var exercises : MutableList<Exercise> = mutableListOf(),
    override var supersets : MutableList<MutableSet<Long>> = mutableListOf(),
    var routineId : Long = -1,
    var isLive : Boolean = false,
) : Routine() {
    constructor(r : Routine) : this(0, 0, r.name + " (Copy)", r.note, Duration.ofSeconds(0), LocalDate.now(), r.exercises.map{Exercise(it)}.toMutableList(), r.supersets.map { mutableSetOf<Long>() }.toMutableList(), r.id, true)
    constructor(w : Workout) : this(0, 0, w.name + " (Copy)", w.note, Duration.ofSeconds(0), LocalDate.now(), w.exercises.map{Exercise(it)}.toMutableList(), w.supersets.map { mutableSetOf<Long>() }.toMutableList(), w.id, true)


}
fun WorkoutEntity.toDomain() = Workout(
    id,
    userId,
    name,
    note,
    Duration.ofSeconds(length),
    LocalDate.parse(date),
    supersets = supersets.map { it.toMutableSet() }.toMutableList(),
    routineId = routineId,
    isLive = isLive,
)