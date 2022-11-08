package com.marcomadalin.olympus.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Exercise

class ExerciseSupersetAdapter(private val exercises: List<Exercise>) : RecyclerView.Adapter<ExerciseSupersetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseSupersetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseSupersetViewHolder(layoutInflater.inflate(R.layout.exercise_superset_item, parent, false))
    }

    override fun onBindViewHolder(holder: ExerciseSupersetViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = exercises.size

}