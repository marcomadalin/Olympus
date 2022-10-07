package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.marcomadalin.olympus.data.database.converters.SetConverters


@Entity(tableName = "ExercisesData")
data class ExerciseDataEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var userId: Int = 0,
    var type: Int = 0,
    var favourite: Boolean = false,
    var equipment: Int = 0,
    var primaryMuscle: Int = 0,
    @TypeConverters(SetConverters::class)
    var secondaryMuscles: Set<Int> = emptySet(),
    var maxWeight: Double = 0.0,
    var orm: Double = 0.0,
    var bestSetWeight: Double = 0.0,
    var bestSetReps: Int = 0,
)