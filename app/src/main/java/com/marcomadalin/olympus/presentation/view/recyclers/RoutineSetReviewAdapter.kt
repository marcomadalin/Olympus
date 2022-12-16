package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Set

class RoutineSetReviewAdapter(private val sets: List<Set>) : RecyclerView.Adapter<RoutineSetReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineSetReviewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RoutineSetReviewViewHolder(layoutInflater.inflate(R.layout.routine_set_review_item, parent, false))
    }

    override fun onBindViewHolder(holder: RoutineSetReviewViewHolder, position: Int) {
        val item = sets[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = sets.size

}