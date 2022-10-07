package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Measures")
data class MeasureEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var userId : Int = 0,
    var date : String = "",
    var value : Double = 0.0,
    var part : Int = 0,
)