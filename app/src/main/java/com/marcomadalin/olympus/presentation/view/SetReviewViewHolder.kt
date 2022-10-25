package com.marcomadalin.olympus.presentation.view

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.SetReviewItemBinding
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.enums.SetType
import kotlin.math.roundToInt

class SetReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = SetReviewItemBinding.bind(view)

    fun render(set : Set) {
        val setType = set.type.toString()[0].toString()
        binding.setType.text = if (setType == "N") (set.setNumber+1).toString() else setType
        binding.setName.text = set.weight.toString() + " kg x " + set.reps.toString()
        binding.rir.text = set.rir.toString()
        binding.urm.text = (((set.weight / ( 1.0278 - 0.0278 * set.reps)) * 100.0).roundToInt() / 100.0).toString() + " kg"
        var color : Int = Color.parseColor(getColor(set.type))
        binding.setType.setTextColor(color)

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