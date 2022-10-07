package com.marcomadalin.olympus.domain.model

import kotlin.collections.Set

open class User (
    private val id : Int = 0,
    private val name : String = "",
    var totalWorkouts : Int = 0,
    var trackingTotalWorkouts : Boolean = false,
    var trackedExercises : Set<Int> = emptySet(),
    var trackedMeasures : Set<Int> = emptySet(),
)