package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Exercise

class ExerciseEditCompletedAdapter(private val updateNote: (Pair<Int, String>) -> Unit,
                                   private val addSet: (Int) -> Unit,
                                   private val deleteSet: (Pair<Int, Int>) -> Unit,
                                   private val onItemClick: (Pair<Int, Int>) -> Boolean,
                                   private val onSetItemClick: (exercisePos : Int, setPos : Int, menuItemId : Int) -> Boolean,
) : RecyclerView.Adapter<ExerciseEditCompletedViewHolder>() {

    private val colors : List<String> = listOf("#40ce68", "#460bbc", "#e447ef", "#46dbd6", "#d13b1d", "#e28258", "9b2047", "#edc255")
    var exercises: List<Exercise> = listOf()
    lateinit var supersets : List<Set<Long>>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseEditCompletedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewHolder = ExerciseEditCompletedViewHolder(layoutInflater.inflate(R.layout.exercise_edit_item_completed, parent, false))
        viewHolder.deleteSet = deleteSet
        return viewHolder
    }

    override fun onBindViewHolder(holder: ExerciseEditCompletedViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item, updateNote, addSet, onItemClick, onSetItemClick, colors, supersets)
    }

    override fun getItemCount(): Int = exercises.size

}