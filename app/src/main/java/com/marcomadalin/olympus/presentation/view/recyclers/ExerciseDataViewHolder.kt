package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.ExerciseDataItemBinding
import com.marcomadalin.olympus.domain.model.ExerciseData


class ExerciseDataViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = ExerciseDataItemBinding.bind(view)

    fun render(exerciseData: ExerciseData, lastFavorite: Int, selectExercise: (ExerciseData) -> Unit) {
        binding.exerciseName5.text = exerciseData.name
        binding.exerciseMuscle.text = exerciseData.primaryMuscle.toString().replace("_", " ")

        if (lastFavorite == -1) binding.separatorView.visibility = View.INVISIBLE
        else if (absoluteAdapterPosition == lastFavorite) binding.separatorView.visibility = View.VISIBLE
        else binding.separatorView.visibility = View.INVISIBLE

        if (absoluteAdapterPosition % 2 == 0) binding.exerciseLayout.setBackgroundResource(R.color.background)
        else binding.exerciseLayout.setBackgroundResource(R.color.layout)

        binding.exerciseLayout.setOnClickListener{selectExercise(exerciseData)}
    }
}