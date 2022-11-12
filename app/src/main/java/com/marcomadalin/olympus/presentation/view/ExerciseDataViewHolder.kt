package com.marcomadalin.olympus.presentation.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.ExerciseDataItemBinding
import com.marcomadalin.olympus.domain.model.ExerciseData


class ExerciseDataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = ExerciseDataItemBinding.bind(view)

    fun render(exerciseData: ExerciseData) {
        binding.exerciseName5.text = exerciseData.name
        binding.exerciseMuscle.text = exerciseData.primaryMuscle.toString()

    }
}