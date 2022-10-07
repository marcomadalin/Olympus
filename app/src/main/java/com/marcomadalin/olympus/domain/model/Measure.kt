package com.marcomadalin.olympus.domain.model

import com.marcomadalin.olympus.domain.model.enums.MeasurePart

data class Measure (
    private val id : Int = 0,
    private val userId : Int = 0,
    var date : String = "",
    var value : Double = 0.0,
    var part : MeasurePart = MeasurePart.Weight,
)