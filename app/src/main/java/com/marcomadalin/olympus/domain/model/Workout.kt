package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.WorkoutEntity
import java.time.Duration
import java.time.LocalDate

data class Workout (
    var id : Long = 0,
    var userId : Long = 0,
    var name : String = "",
    var note : String = "",
    var length  : Duration = Duration.ofSeconds(0),
    var date : LocalDate = LocalDate.parse(""),
    var exercises: List<Exercise> = emptyList()
)
fun WorkoutEntity.toDomain() = Workout(
    id,
    userId,
    name,
    note,
    Duration.ofSeconds(length),
    LocalDate.parse(date)
)