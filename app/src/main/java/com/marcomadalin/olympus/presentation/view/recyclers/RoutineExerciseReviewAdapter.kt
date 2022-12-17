package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Exercise

class RoutineExerciseReviewAdapter() : RecyclerView.Adapter<RoutineExerciseReviewViewHolder>() {
    var exercises: List<Exercise> = mutableListOf()
    var supersets : List<Set<Long>> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineExerciseReviewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RoutineExerciseReviewViewHolder(layoutInflater.inflate(R.layout.routine_exercise_review_item, parent, false))
    }

    override fun onBindViewHolder(holder: RoutineExerciseReviewViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item, supersets)
    }

    override fun getItemCount(): Int = exercises.size
}