package com.marcomadalin.olympus.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Exercise

class ExerciseSupersetAdapter(private val exercises : List<Exercise>, private val selectExercise : (Int) -> Unit
 ) : RecyclerView.Adapter<ExerciseSupersetViewHolder>() {

    val colors : List<String> = listOf("#40ce68", "#460bbc", "#e447ef", "#46dbd6", "#d13b1d", "#e28258", "9b2047", "#edc255")
    lateinit var supersets : List<Set<Long>>

    var selected : Boolean = false
    var added : Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseSupersetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseSupersetViewHolder(layoutInflater.inflate(R.layout.exercise_superset_item, parent, false))
    }

    override fun onBindViewHolder(holder: ExerciseSupersetViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item, selected, added, selectExercise, colors, supersets)
    }

    override fun getItemCount(): Int = exercises.size

}