package com.marcomadalin.olympus.presentation.view.recyclers

import android.graphics.Color
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.ExerciseReviewItemBinding
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.presentation.view.util.SupersetColors.colors


class ExerciseReviewViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    var binding = ExerciseReviewItemBinding.bind(view)

    fun render(exercise: Exercise, supersets: List<Set<Long>>) {
        binding.superset3.isVisible = false
        for (i in supersets.indices) {
            if (supersets[i].contains(exercise.id)) {
                binding.superset3.layoutParams.height = binding.layoutExerciseReview.height
                binding.superset3.setBackgroundColor(Color.parseColor(colors[i]))
                binding.superset3.isVisible = true
            }
        }
        binding.exerciseName2.text = exercise.name
        binding.summarytNote2.text = exercise.note
        binding.setRecycler.layoutManager = LinearLayoutManager(view.context)
        binding.setRecycler.adapter = SetReviewAdapter(exercise.sets)
    }

}