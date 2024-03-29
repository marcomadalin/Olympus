package com.marcomadalin.olympus.presentation.view.recyclers

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.RoutineExerciseItemBinding
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.presentation.view.util.SupersetColors.colors

class RoutineExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = RoutineExerciseItemBinding.bind(view)

    fun render(exercise: Exercise, supersets: List<Set<Long>>) {
        binding.superset2.visibility = View.GONE

        for (i in supersets.indices) {
            if (supersets[i].contains(exercise.id)) {
                binding.superset2.setBackgroundColor(Color.parseColor(colors[i]))
                binding.superset2.visibility = View.VISIBLE
                binding.superset2.left = 10
            }
        }
        binding.textExerciseRoutine.text = exercise.sets.size.toString() + " x " + exercise.name
    }
}