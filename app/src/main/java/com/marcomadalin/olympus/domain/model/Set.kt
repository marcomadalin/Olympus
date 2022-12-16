package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.SetEntity
import com.marcomadalin.olympus.domain.model.enums.SetType

data class Set(
    var id : Long = 0,
    var exerciseId : Long = 0,
    var weight : Double = 0.0,
    var reps : Int = 0,
    var rir : Int = 0,
    var lastWeight : Double = 0.0,
    var lastReps : Int = 0,
    var lastRir : Int = 0,
    var type : SetType = SetType.Warmup,
    var setNumber : Int = 0,
    var completed : Boolean = false
) {
    constructor(s : Set) : this (0, 0, s.weight, s.reps, s.rir, s.lastWeight, s.lastReps, s.lastRir, s.type, s.setNumber, s.completed)
}
fun SetEntity.toDomain() = Set(
    id,
    exerciseId,
    weight,
    reps,
    rir,
    lastWeight,
    lastReps,
    lastRir,
    SetType.valueOf(type),
    setNumber,
    true
)
