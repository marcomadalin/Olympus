package com.marcomadalin.olympus.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Exercise

class ExerciseEditAdapter(private val exercises: List<Exercise>, private val onClickAdd : (Int) -> Unit) : RecyclerView.Adapter<ExerciseEditViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseEditViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseEditViewHolder(layoutInflater.inflate(R.layout.exercise_edit_item, parent, false))
    }

    override fun onBindViewHolder(holder: ExerciseEditViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item, onClickAdd)
    }

    override fun getItemCount(): Int = exercises.size

}