package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Exercise

class ExerciseReorderAdapter(private val exercises: List<Exercise>) : RecyclerView.Adapter<ExerciseReorderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseReorderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseReorderViewHolder(layoutInflater.inflate(R.layout.exercise_reorder_item, parent, false))
    }

    override fun onBindViewHolder(holder: ExerciseReorderViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = exercises.size

}