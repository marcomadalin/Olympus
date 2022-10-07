package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.domain.model.enums.SetType

data class Set(
    private val id : Int = 0,
    private val exerciseId : Int = 0,
    var weight : Int = 0,
    var reps : Int = 0,
    var rir : Int = 0,
    var lastWeight : Int = 0,
    var lastReps : Int = 0,
    var type : SetType = SetType.Warmup,
    var setNumber : Int = 0,
)