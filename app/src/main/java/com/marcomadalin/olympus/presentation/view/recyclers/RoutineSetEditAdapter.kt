package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Set

class RoutineSetEditAdapter(private val sets: List<Set>) : RecyclerView.Adapter<RoutineSetEditViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineSetEditViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RoutineSetEditViewHolder(layoutInflater.inflate(R.layout.routine_set_edit_item, parent, false))
    }

    override fun onBindViewHolder(holder: RoutineSetEditViewHolder, position: Int) {
        val item = sets[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = sets.size

}