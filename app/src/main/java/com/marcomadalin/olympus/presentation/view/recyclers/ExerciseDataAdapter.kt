package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.ExerciseData

class ExerciseDataAdapter(var exercises: MutableList<ExerciseData>,
                          private val selectExercise : (ExerciseData) -> Unit)
    : RecyclerView.Adapter<ExerciseDataViewHolder>() {

    var lastFavorite: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseDataViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseDataViewHolder(layoutInflater.inflate(R.layout.exercise_data_item, parent, false))
    }

    override fun onBindViewHolder(holder: ExerciseDataViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item, lastFavorite, selectExercise)
    }

    override fun getItemCount(): Int = exercises.size

}