package com.marcomadalin.olympus.presentation.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.SetEditItemBinding
import com.marcomadalin.olympus.domain.model.Set

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

        binding.setTypeEdit.text = set.type.toString()

        if (set.lastReps > 0) {
            binding.prevWeight.text = set.lastWeight.toString() + " x " + set.lastReps
            binding.repsNumber.setText(set.lastReps.toString())
            binding.weightNumber2.setText(set.lastWeight.toString())
        }
        else binding.prevWeight.text = "-"


    }

    private fun changeBackgroundColor() {
        if (absoluteAdapterPosition % 2 == 0) binding.setEditLayout.setBackgroundResource(R.color.layout)
        else binding.setEditLayout.setBackgroundResource(R.color.background)
    }
}