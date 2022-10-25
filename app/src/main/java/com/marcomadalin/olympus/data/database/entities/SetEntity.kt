package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcomadalin.olympus.domain.model.Set


@Entity(tableName = "Sets")
data class SetEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,
    var exerciseId : Long = 0,
    var weight : Double = 0.0,
    var reps : Int = 0,
    var rir : Int = 0,
    var lastWeight : Double = 0.0,
    var lastReps : Int = 0,
    var type : String = "",
    var setNumber : Int = 0,
)
fun Set.toData() = SetEntity(
    id,
    exerciseId,
    weight,
    reps,
    rir,
    lastWeight,
    lastReps,
    type.toString(),
    setNumber
)