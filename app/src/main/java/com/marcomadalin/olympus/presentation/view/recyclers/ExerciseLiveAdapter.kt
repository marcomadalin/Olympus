package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Exercise

class ExerciseLiveAdapter(private val addSet: (Int) -> Unit,
                          private val deleteSet: (Int, Int) -> Unit,
                          private val onItemClick: (Int, Int) -> Boolean,
) : RecyclerView.Adapter<ExerciseLiveViewHolder>() {
    var exercises: List<Exercise> = listOf()
    lateinit var supersets : List<Set<Long>>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseLiveViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = ExerciseLiveViewHolder(layoutInflater.inflate(R.layout.exercise_live_item, parent, false))
        viewHolder.deleteSet = deleteSet
        return viewHolder
    }

    override fun onBindViewHolder(holder: ExerciseLiveViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item, addSet, onItemClick, supersets)
    }

    override fun getItemCount(): Int = exercises.size

}