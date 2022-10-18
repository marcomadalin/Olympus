package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.UserEntity
import kotlin.collections.Set

open class User (
    var id : Long = 0,
    var name : String = "",
    var totalWorkouts : Int = 0,
    var trackingTotalWorkouts : Boolean = false,
    var trackedExercises : Set<Int> = emptySet(),
    var trackedMeasures : Set<Int> = emptySet(),
)
fun UserEntity.toDomain() = User(
    id,
    name,
    totalWorkouts,
    trackingTotalWorkouts,
    trackedExercises,
    trackedMeasures
)