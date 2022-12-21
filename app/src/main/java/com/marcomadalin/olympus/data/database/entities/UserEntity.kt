package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.marcomadalin.olympus.data.database.converters.MapConverters
import com.marcomadalin.olympus.domain.model.User

@Entity(tableName = "Users")
open class UserEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,
    var name : String = "",
    var totalRestDays : Int = 0,
    var totalVolume : Double = 0.0,
    var totalReps : Int = 0,
    var totalWorkoutLength : Long = 0,
    @TypeConverters(MapConverters::class)
    var muscleDivision : Map<String,Int> = emptyMap()
)
fun User.toData() = UserEntity(
    id,
    name,
    totalRestDays,
    totalVolume,
    totalReps,
    totalWorkoutLength.seconds,
    muscleDivision.mapKeys{ it -> it.key.toString() },
)