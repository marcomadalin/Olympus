package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.MeasureSelectItemBinding
import com.marcomadalin.olympus.domain.model.Measure
import com.marcomadalin.olympus.domain.model.enums.MeasurePart


class MeasureSelectViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = MeasureSelectItemBinding.bind(view)

    fun render(measures: Pair<Measure,Measure>, onMeasureClick: (Int)->Unit) {

        binding.layoutMes.setOnClickListener { onMeasureClick(absoluteAdapterPosition) }

        binding.measureType.text = measures.first.part.toString().replace("_", " ")

        if (measures.first.value == -1.0) binding.measureValue.text = "-"
        else binding.measureValue.text = measures.first.value.toString()

        when (measures.first.part) {
            MeasurePart.Weight -> binding.measureValue.text = binding.measureValue.text.toString() + " kg"
            MeasurePart.Body_Fat -> binding.measureValue.text = binding.measureValue.text.toString() + "  %"
            else -> binding.measureValue.text = binding.measureValue.text.toString() + "  cm"
        }

        var difference = 0.0
        if (measures.second.value != -1.0) difference = measures.first.value - measures.second.value

        if (difference > 0.0) binding.imageView12.setBackgroundResource(R.drawable.arrow_up)
        else if (difference < 0.0) binding.imageView12.setBackgroundResource(R.drawable.arrow_down)
        else binding.imageView12.setBackgroundResource(R.drawable.arrow_right)
    }
}