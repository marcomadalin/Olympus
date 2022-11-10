package com.marcomadalin.olympus.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Exercise

class WorkoutSummaryAdapter(private val exercises: List<Exercise>) : RecyclerView.Adapter<WorkoutSummaryViewHolder>() {

    private val colors : List<String> = listOf("#40ce68", "#460bbc", "#e447ef", "#46dbd6", "#d13b1d", "#e28258", "9b2047", "#edc255")
    lateinit var supersets : List<Set<Long>>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutSummaryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return WorkoutSummaryViewHolder(layoutInflater.inflate(R.layout.workout_summary_item, parent, false))
    }

    override fun onBindViewHolder(holder: WorkoutSummaryViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item, colors, supersets)
    }

    override fun getItemCount(): Int = exercises.size
}