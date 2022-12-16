package com.marcomadalin.olympus.presentation.view.recyclers

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.RoutineSetReviewItemBinding
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.enums.SetType

class RoutineSetReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = RoutineSetReviewItemBinding.bind(view)

    fun render(set : Set) {
        val color : Int = Color.parseColor(getColor(set.type))
        binding.setType2.setTextColor(color)
        binding.setType2.text = (1 + set.setNumber).toString() + " - " + set.type.toString()[0].toString()
        binding.setName2.text = set.weight.toString() + " kg x " + set.reps.toString()
        binding.rir3.text = set.rir.toString()
    }

    private fun getColor(type : SetType) : String {
        return when (type) {
            SetType.Normal -> "#5E5D5D"
            SetType.Warmup -> "#D86800"
            SetType.Drop -> "#2196F3"
            else -> "#7A0098"
        }
    }
}