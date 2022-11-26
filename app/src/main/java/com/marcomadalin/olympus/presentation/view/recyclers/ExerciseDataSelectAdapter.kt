package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.ExerciseData

class ExerciseDataSelectAdapter(var exercises: MutableList<ExerciseData>,
                                private val selectExercise : (ExerciseData) -> Unit)
    : RecyclerView.Adapter<ExerciseDataSelectViewHolder>() {

    var selectedExercises : MutableSet<Long> = mutableSetOf()

    var lastFavorite: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseDataSelectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseDataSelectViewHolder(layoutInflater.inflate(R.layout.exercise_data_item, parent, false))
    }

    override fun onBindViewHolder(holder: ExerciseDataSelectViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item, lastFavorite, selectExercise, selectedExercises)
    }

    override fun getItemCount(): Int = exercises.size

}