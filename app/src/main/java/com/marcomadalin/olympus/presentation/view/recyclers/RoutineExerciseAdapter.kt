package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Exercise

class RoutineExerciseAdapter(private val exercises: List<Exercise>, private val supersets : List<Set<Long>>) : RecyclerView.Adapter<RoutineExerciseViewHolder>() {

    private val colors : List<String> = listOf("#40ce68", "#460bbc", "#e447ef", "#46dbd6", "#d13b1d", "#e28258", "9b2047", "#edc255")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineExerciseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RoutineExerciseViewHolder(layoutInflater.inflate(R.layout.routine_exercise_item, parent, false))
    }

    override fun onBindViewHolder(holder: RoutineExerciseViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item, colors, supersets)
    }

    override fun getItemCount(): Int = exercises.size
}