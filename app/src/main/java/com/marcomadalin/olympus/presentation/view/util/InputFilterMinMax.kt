package com.marcomadalin.olympus.presentation.view.util

import android.text.InputFilter
import android.text.Spanned

class InputFilterMinMax constructor(min: Int, max: Int) : InputFilter {

    private var min : Int = 0
    private var max : Int = 0

    init {
        this.min = min
        this.max = max
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (dest.toString() + source.toString()).toInt()
            if (isInRange(min, max, input)) return null
        } catch (_: NumberFormatException) {
        }
        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}