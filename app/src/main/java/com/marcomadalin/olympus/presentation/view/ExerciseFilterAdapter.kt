package com.marcomadalin.olympus.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R

class ExerciseFilterAdapter(var filters: List<String>,
                            private val selectFilter : (String) -> Unit)
    : RecyclerView.Adapter<ExerciseFilterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseFilterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseFilterViewHolder(layoutInflater.inflate(R.layout.exercise_filter_item, parent, false))
    }

    override fun onBindViewHolder(holder: ExerciseFilterViewHolder, position: Int) {
        val item = filters[position]
        holder.render(item, selectFilter)
    }

    override fun getItemCount(): Int = filters.size

}