package com.marcomadalin.olympus.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Set

class SetReviewAdapter(private val sets: List<Set>) : RecyclerView.Adapter<SetReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetReviewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SetReviewViewHolder(layoutInflater.inflate(R.layout.set_review_item, parent, false))
    }

    override fun onBindViewHolder(holder: SetReviewViewHolder, position: Int) {
        val item = sets[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = sets.size

}