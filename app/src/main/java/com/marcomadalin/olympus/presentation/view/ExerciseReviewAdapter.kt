package com.marcomadalin.olympus.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Exercise

class ExerciseReviewAdapter(private val exercises: List<Exercise>) : RecyclerView.Adapter<ExerciseReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseReviewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseReviewViewHolder(layoutInflater.inflate(R.layout.exercise_review_item, parent, false))
    }

    override fun onBindViewHolder(holder: ExerciseReviewViewHolder, position: Int) {
        val item = exercises[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = exercises.size

}