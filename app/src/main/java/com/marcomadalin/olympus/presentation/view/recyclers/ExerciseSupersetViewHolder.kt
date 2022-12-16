package com.marcomadalin.olympus.presentation.view.recyclers

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.ExerciseSupersetItemBinding
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.presentation.view.util.SupersetColors.colors


class ExerciseSupersetViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = ExerciseSupersetItemBinding.bind(view)

    fun render(
        exercise: Exercise,
        selected: Boolean,
        added: Boolean,
        selectExercise: (Int) -> Unit,
        supersets: List<Set<Long>>
    ) {
        binding.view2.background = null
        for (i in supersets.indices) {
            if (supersets[i].contains(exercise.id)) binding.view2.setBackgroundColor(Color.parseColor(colors[i]))
        }
        binding.supersetLayout.bringToFront()
        changeBackground()
        binding.exerciseName.text = exercise.name
        binding.supersetLayout.setOnClickListener{selectExercise(absoluteAdapterPosition)}
        if (selected) {
            if (added) binding.supersetLayout.setBackgroundResource(R.color.buttons)
            else changeBackground()
        }
    }

    private fun changeBackground() {
        if (absoluteAdapterPosition % 2 == 0) binding.supersetLayout.setBackgroundResource(R.color.background)
        else binding.supersetLayout.setBackgroundResource(R.color.layout)
    }
}