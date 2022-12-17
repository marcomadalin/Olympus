package com.marcomadalin.olympus.presentation.view.recyclers

import android.graphics.Color
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.RoutineExerciseReviewItemBinding
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.presentation.view.util.SupersetColors.colors

class RoutineExerciseReviewViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    var binding = RoutineExerciseReviewItemBinding.bind(view)

    fun render(exercise: Exercise, supersets: List<Set<Long>>) {
        binding.superset6.isVisible = false
        for (i in supersets.indices) {
            if (supersets[i].contains(exercise.id)) {
                binding.superset6.layoutParams.height = binding.layoutExerciseReview.height
                binding.superset6.setBackgroundColor(Color.parseColor(colors[i]))
                binding.superset6.isVisible = true
            }
        }
        binding.exerciseName3.text = exercise.name
        binding.noteExerciseRoutine.text = exercise.note
        binding.setRecyclerRoutineRev.layoutManager = LinearLayoutManager(view.context)
        binding.setRecyclerRoutineRev.adapter = RoutineSetReviewAdapter(exercise.sets)
    }
}