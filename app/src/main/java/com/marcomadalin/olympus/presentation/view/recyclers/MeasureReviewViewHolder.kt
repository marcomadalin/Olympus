package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.MeasureReviewItemBinding
import com.marcomadalin.olympus.domain.model.Measure


class MeasureReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = MeasureReviewItemBinding.bind(view)

    fun render(measures: Measure, onMeasureClick: (Int)->Unit) {

    }
}