package com.marcomadalin.olympus.presentation.view

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.ExerciseEditItemBinding
import com.marcomadalin.olympus.domain.model.Exercise

class ExerciseEditViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    var binding = ExerciseEditItemBinding.bind(view)

    fun render(exercise : Exercise) {
        binding.exerciseName4.text = exercise.exerciseDataId.toString()
        binding.exerciseNoteEdit.setText(exercise.note)
        binding.setRecycler2.layoutManager = LinearLayoutManager(view.context)
        binding.setRecycler2.adapter = SetEditAdapter(exercise.sets)
    }

}