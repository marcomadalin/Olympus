package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Routine

class RoutineAdapter(private val onRoutineClick: (Int)->Unit ) : RecyclerView.Adapter<RoutineViewHolder>() {

    var routines: List<Routine> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RoutineViewHolder(layoutInflater.inflate(R.layout.routine_item, parent, false))
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val item = routines[position]
        holder.render(item, onRoutineClick)
    }

    override fun getItemCount(): Int = routines.size

}