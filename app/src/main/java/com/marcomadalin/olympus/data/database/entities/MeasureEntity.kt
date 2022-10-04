package com.marcomadalin.olympus.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Measure")
data class MeasureEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val userId : Int,
    val date : Date,
    val value : Double,
    val part : Int,
)