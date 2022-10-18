package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.marcomadalin.olympus.domain.model.Measure


@Entity(tableName = "Measures")
data class MeasureEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,
    var userId : Long = 0,
    var date : String = "",
    var value : Double = 0.0,
    var part : String = "",
)
fun Measure.toData() = MeasureEntity(
    id,
    userId,
    date.toString(),
    value,
    part.toString()
)