package com.marcomadalin.olympus.domain.model

import java.time.Duration
import java.util.*

data class Workout (
    override var name : String = "",
    override var note : String = "",
    var length : Duration = Duration.ofSeconds(0),
    var date : Date = Date(),
) : Routine (name = name, note = note)