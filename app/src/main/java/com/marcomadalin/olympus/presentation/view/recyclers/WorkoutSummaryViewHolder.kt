package com.marcomadalin.olympus.presentation.view.recyclers

import android.graphics.Color
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.WorkoutSummaryItemBinding
import com.marcomadalin.olympus.domain.model.Exercise

class WorkoutSummaryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = WorkoutSummaryItemBinding.bind(view)

    fun render(exercise: Exercise, colors: List<String>, supersets: List<Set<Long>>) {
        binding.superset4.isVisible = false
        for (i in supersets.indices) {
            if (supersets[i].contains(exercise.id)) {
                binding.superset4.setBackgroundColor(Color.parseColor(colors[i]))
                binding.superset4.isVisible = true
                binding.summarySets.left = 10
            }
        }
        binding.summarySets.text = exercise.sets.size.toString() + " x " + exercise.exerciseDataId
        var max = exercise.sets.maxBy {it.weight * it.reps }
        binding.summaryTop.text = max.weight.toString() + " kg x " + max.reps.toString()
    }
}