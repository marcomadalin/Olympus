package com.marcomadalin.olympus.presentation.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.ExerciseReorderItemBinding
import com.marcomadalin.olympus.domain.model.Exercise


class ExerciseReorderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    var binding = ExerciseReorderItemBinding.bind(view)

    fun render(exercise: Exercise) {
        binding.exerciseName5.text = exercise.exerciseDataId.toString()
    }
}