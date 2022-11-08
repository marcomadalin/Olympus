package com.marcomadalin.olympus.presentation.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.ExerciseSupersetItemBinding
import com.marcomadalin.olympus.domain.model.Exercise


class ExerciseSupersetViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = ExerciseSupersetItemBinding.bind(view)

    fun render(exercise: Exercise) {
        binding.exerciseName.text = exercise.exerciseDataId.toString()
    }
}