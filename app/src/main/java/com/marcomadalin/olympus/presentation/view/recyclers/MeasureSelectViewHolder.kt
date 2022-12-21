package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.MeasureSelectItemBinding


class MeasureSelectViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    var binding = MeasureSelectItemBinding.bind(view)

    fun render(measure: Pair<String,Double>, onMeasureClick: (Int)->Unit) {
    }
}