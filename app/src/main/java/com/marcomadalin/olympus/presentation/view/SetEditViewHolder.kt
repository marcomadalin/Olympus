package com.marcomadalin.olympus.presentation.view

import android.graphics.Color
import android.text.InputType
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.SetEditItemBinding
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.enums.SetType

class SetEditViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = SetEditItemBinding.bind(view)

    fun render(set : Set, toggleSet: (Pair<Int, Int>) -> Unit, exercisePosition : Int) {
        if (set.completed) binding.setEditLayout.setBackgroundResource(R.color.green)
        else changeBackgroundColor()

        binding.checkbox.setOnClickListener{
            if (set.completed) changeBackgroundColor()
            else binding.setEditLayout.setBackgroundResource(R.color.green)
            toggleSet(Pair(exercisePosition, absoluteAdapterPosition))
        }

        binding.setTypeEdit.text = set.type.toString()[0].toString()
        var color : Int = Color.parseColor(getColor(set.type))
        binding.setTypeEdit.setTextColor(color)
        binding.rirNumber.filters = arrayOf(InputFilterMinMax(0, 10))
        binding.weightNumber2.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL


        if (set.lastReps > 0) {
            binding.prevWeight.text = set.lastWeight.toString() + " x " + set.lastReps
            binding.repsNumber.setText(set.lastReps.toString())
            binding.weightNumber2.setText(set.lastWeight.toString())
        }
        else binding.prevWeight.text = "-"


    }

    private fun changeBackgroundColor() {
        if (absoluteAdapterPosition % 2 == 0) binding.setEditLayout.setBackgroundResource(R.color.background)
        else binding.setEditLayout.setBackgroundResource(R.color.layout)
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