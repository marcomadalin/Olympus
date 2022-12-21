package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Routine

class MeasureSelectAdapter(private val onMeasureClick: (Int)->Unit ) : RecyclerView.Adapter<MeasureSelectViewHolder>() {

    var measures: List<Pair<String, Double>> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasureSelectViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MeasureSelectViewHolder(layoutInflater.inflate(R.layout.measure_select_item, parent, false))
    }

    override fun onBindViewHolder(holder: MeasureSelectViewHolder, position: Int) {
        holder.render(measures[position], onMeasureClick)
    }

    override fun getItemCount(): Int = measures.size

}