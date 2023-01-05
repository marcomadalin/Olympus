package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.MeasureReviewItemBinding
import com.marcomadalin.olympus.domain.model.Measure
import com.marcomadalin.olympus.domain.model.enums.MeasurePart
import java.time.format.DateTimeFormatter


class MeasureReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = MeasureReviewItemBinding.bind(view)

    fun render(measures: Measure, onMeasureClick: (Int)->Unit) {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY")
        binding.dateMeeasureVal.text = measures.date.format(formatter).toString()
        binding.valueMesure.text = measures.value.toString()
        binding.layoutMeasureReviewItem.setOnClickListener{onMeasureClick(absoluteAdapterPosition)}
        if (absoluteAdapterPosition % 2 != 0) binding.layoutMeasureReviewItem.setBackgroundResource(R.color.layout)
        else binding.layoutMeasureReviewItem.setBackgroundResource(R.color.background)

        when (measures.part) {
            MeasurePart.Weight -> binding.valueMesure.text = binding.valueMesure.text.toString() + " kg"
            MeasurePart.Body_Fat -> binding.valueMesure.text = binding.valueMesure.text.toString() + "  %"
            else -> binding.valueMesure.text = binding.valueMesure.text.toString() + "  cm"
        }
    }
}