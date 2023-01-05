package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.UserEntity
import com.marcomadalin.olympus.domain.model.enums.Muscle
import java.time.Duration

open class User (
    var id : Long = 0,
    var name : String = "",
    var totalRestDays : Int = 0,
    var totalVolume : Double = 0.0,
    var totalReps : Int = 0,
    var totalWorkoutLength : Duration = Duration.ofSeconds(0),
    var muscleDivision : MutableMap<Muscle,Int> = mutableMapOf(),
)
fun UserEntity.toDomain() = User(
    id,
    name,
    totalRestDays,
    totalVolume,
    totalReps,
    Duration.ofSeconds(totalWorkoutLength),
    muscleDivision.mapKeys{ it -> Muscle.valueOf(it.key) }.toMutableMap(),
)