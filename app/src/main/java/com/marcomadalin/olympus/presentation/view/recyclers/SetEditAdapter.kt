package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Set

class SetEditAdapter(private val sets: List<Set>,
                     private val toggleSet: (Pair<Int, Int>) -> Unit,
                     private val exercisePosition : Int
) : RecyclerView.Adapter<SetEditViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetEditViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SetEditViewHolder(layoutInflater.inflate(R.layout.set_edit_item, parent, false))
    }

    override fun onBindViewHolder(holder: SetEditViewHolder, position: Int) {
        val item = sets[position]
        holder.render(item, toggleSet, exercisePosition)
    }

    override fun getItemCount(): Int = sets.size

}