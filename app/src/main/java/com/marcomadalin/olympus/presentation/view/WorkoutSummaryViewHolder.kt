package com.marcomadalin.olympus.presentation.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.WorkoutSummaryItemBinding
import com.marcomadalin.olympus.domain.model.Exercise

class WorkoutSummaryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = WorkoutSummaryItemBinding.bind(view)

    fun render(exercise : Exercise) {
        binding.summarySets.text = exercise.sets.size.toString() + " x " + exercise.exerciseDataId
        var max = exercise.sets.maxBy {it.weight * it.reps }
        binding.summaryTop.text = max.weight.toString() + " kg x " + max.reps.toString()
    }
}