package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.marcomadalin.olympus.data.database.converters.StringSetConverters
import com.marcomadalin.olympus.domain.model.ExerciseData


@Entity(tableName = "ExercisesData")
data class ExerciseDataEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var userId: Long = 0,
    var name : String = "",
    var type: String = "",
    var favourite: Boolean = false,
    var equipment: String = "",
    var primaryMuscle: String = "",
    @TypeConverters(StringSetConverters::class)
    var secondaryMuscles: Set<String> = emptySet(),
    var maxWeight: Double = 0.0,
    var orm: Double = 0.0,
    var bestSetWeight: Double = 0.0,
    var bestSetReps: Int = 0,
    var default : Boolean = true
)
fun ExerciseData.toData() = ExerciseDataEntity(
    id,
    userId,
    name,
    type.toString(),
    favourite,
    equipment.toString(),
    primaryMuscle.toString(),
    secondaryMuscles.map{ it.toString()}.toSet(),
    maxWeight,
    orm,
    bestSetWeight,
    bestSetReps,
    default
)