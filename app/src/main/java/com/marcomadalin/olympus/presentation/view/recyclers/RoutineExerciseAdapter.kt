package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Exercise

class RoutineExerciseAdapter(private val exercises: List<Exercise>, private val supersets : List<Set<Long>>) : RecyclerView.Adapter<RoutineExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineExerciseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RoutineExerciseViewHolder(layoutInflater.inflate(R.layout.routine_exercise_item, parent, false))
    }

    override fun onBindViewHolder(holder: RoutineExerciseViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item, supersets)
    }

    override fun getItemCount(): Int = exercises.size
}