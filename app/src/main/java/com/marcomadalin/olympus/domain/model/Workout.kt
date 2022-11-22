package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.WorkoutEntity
import java.time.Duration
import java.time.LocalDate

data class Workout (
    override var id : Long = 0,
    override var userId : Long = 0,
    override var name : String = "",
    override var note : String = "",
    var length : Duration = Duration.ofSeconds(0),
    var date : LocalDate = LocalDate.parse(""),
    override var exercises : MutableList<Exercise> = mutableListOf(),
    override var supersets : MutableList<MutableSet<Long>> = mutableListOf(),
    var routineId : Long = -1
) : Routine()
fun WorkoutEntity.toDomain() = Workout(
    id,
    userId,
    name,
    note,
    Duration.ofSeconds(length),
    LocalDate.parse(date),
    supersets = supersets.map { it.toMutableSet() }.toMutableList(),
    routineId = routineId
)