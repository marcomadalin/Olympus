package com.marcomadalin.olympus.domain.model

import java.time.Duration


data class Exercise(
    private val id : Int = 0,
    private val routineId : Int = 0,
    private val exerciseDataId : Int = 0,
    var restTime : Duration = Duration.ofSeconds(0),
    var note : String = "",
    var exerciseNumber : Int = 0
)