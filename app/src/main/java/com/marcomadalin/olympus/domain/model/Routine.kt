package com.marcomadalin.olympus.domain.model

open class Routine (
    protected val id : Int = 0,
    protected val userId : Int = 0,
    open var name : String = "",
    open var note : String = "",
)