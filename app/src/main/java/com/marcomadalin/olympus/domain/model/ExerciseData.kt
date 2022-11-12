package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.data.database.entities.ExerciseDataEntity
import com.marcomadalin.olympus.domain.model.enums.Equipment
import com.marcomadalin.olympus.domain.model.enums.ExerciseType
import com.marcomadalin.olympus.domain.model.enums.Muscle
import kotlin.collections.Set

data class ExerciseData(
    var id: Long = 0,
    var userId: Long = 0,
    var name : String = "",
    var type: ExerciseType = ExerciseType.WeightReps,
    var favourite: Boolean = false,
    var equipment: Equipment = Equipment.None,
    var primaryMuscle: Muscle = Muscle.Abs,
    var secondaryMuscles: Set<Muscle> = emptySet(),
    var maxWeight: Double = 0.0,
    var orm: Double = 0.0,
    var bestSetWeight: Double = 0.0,
    var bestSetReps: Int = 0,
    var default : Boolean = true
)
fun ExerciseDataEntity.toDomain() = ExerciseData(
    id,
    userId,
    name,
    ExerciseType.valueOf(type),
    favourite,
    Equipment.valueOf(equipment),
    Muscle.valueOf(primaryMuscle),
    secondaryMuscles.map { Muscle.valueOf(it) }.toSet(),
    maxWeight,
    orm,
    bestSetWeight,
    bestSetReps,
    default
)
