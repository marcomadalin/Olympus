package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.MeasureEntity
import com.marcomadalin.olympus.domain.model.enums.MeasurePart
import java.time.LocalDate

data class Measure(
    var id: Long = 0,
    var userId: Long = 0,
    var date: LocalDate = LocalDate.now(),
    var value: Double = 0.0,
    var part: MeasurePart = MeasurePart.Weight,
)
fun MeasureEntity.toDomain() = Measure(
    id,
    userId,
    LocalDate.parse(date),
    value,
    MeasurePart.valueOf(part)
)

