package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.WorkoutEntity
import java.time.Duration
import java.time.LocalDate

data class Workout (
    var length  : Duration = Duration.ofSeconds(0),
    var date : LocalDate = LocalDate.parse(""),
) : Routine() {
    fun WorkoutEntity.toDomain() = Workout(
        Duration.ofSeconds(length),
        LocalDate.parse(date)
    )
}