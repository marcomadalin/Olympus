package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Measure

class MeasureReviewAdapter(private val onMeasureClick: (Int)->Unit ) : RecyclerView.Adapter<MeasureReviewViewHolder>() {

    var measures: List<Measure> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasureReviewViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MeasureReviewViewHolder(layoutInflater.inflate(R.layout.measure_review_item, parent, false))
    }

    override fun onBindViewHolder(holder: MeasureReviewViewHolder, position: Int) {
        holder.render(measures[position], onMeasureClick)
    }

    override fun getItemCount(): Int = measures.size

}