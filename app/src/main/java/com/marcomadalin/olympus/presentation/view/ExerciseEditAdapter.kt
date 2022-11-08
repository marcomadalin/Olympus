package com.marcomadalin.olympus.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Exercise

class ExerciseEditAdapter(private val exercises: List<Exercise>,
                          private val updateNote: (Pair<Int, String>) -> Unit,
                          private val addSet: (Int) -> Unit,
                          private val deleteSet: (Pair<Int, Int>) -> Unit,
                          private val toggleSet: (Pair<Int, Int>) -> Unit,
                          private val onItemClick: (Pair<Int, Int>) -> Boolean
) : RecyclerView.Adapter<ExerciseEditViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseEditViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = ExerciseEditViewHolder(layoutInflater.inflate(R.layout.exercise_edit_item, parent, false))
        viewHolder.deleteSet = deleteSet
        return viewHolder
    }

    override fun onBindViewHolder(holder: ExerciseEditViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item, updateNote, addSet, toggleSet, onItemClick)
    }

    override fun getItemCount(): Int = exercises.size

}