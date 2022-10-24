package com.marcomadalin.olympus.presentation.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.ExerciseReviewItemBinding
import com.marcomadalin.olympus.domain.model.Exercise

class ExerciseReviewViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    var binding = ExerciseReviewItemBinding.bind(view)

    fun render(exercise : Exercise) {
        binding.exerciseName2.text = exercise.exerciseDataId.toString()
        binding.summarytNote2.text = exercise.note
        binding.setRecycler.layoutManager = LinearLayoutManager(view.context)
        binding.setRecycler.adapter = SetReviewAdapter(exercise.sets)
    }

}