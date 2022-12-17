package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Exercise

class RoutineExerciseEditAdapter(private val addSet: (Int) -> Unit,
                                 private val deleteSet: (Int, Int) -> Unit,
                                 private val onItemClick: (Int, Int) -> Boolean,
) : RecyclerView.Adapter<RoutineExerciseEditViewHolder>() {
    var exercises: List<Exercise> = listOf()
    lateinit var supersets : List<Set<Long>>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineExerciseEditViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = RoutineExerciseEditViewHolder(layoutInflater.inflate(R.layout.routine_exercise_edit_item, parent, false))
        viewHolder.deleteSet = deleteSet
        return viewHolder
    }

    override fun onBindViewHolder(holder: RoutineExerciseEditViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item, addSet, onItemClick, supersets)
    }

    override fun getItemCount(): Int = exercises.size

}