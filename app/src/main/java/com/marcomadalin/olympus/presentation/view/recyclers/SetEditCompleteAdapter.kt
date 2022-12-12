package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Set

class SetEditCompleteAdapter(
    private val sets: List<Set>,
    private val exercisePosition: Int,
    private val onMenuItemClick: (exercisePos: Int, setPos: Int, menuItemId: Int) -> Boolean,
    private val onItemClick: (exercisePos: Int, setPos: Int, buttonPressed : Int, value: Double) -> Unit
) : RecyclerView.Adapter<SetEditCompletedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetEditCompletedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SetEditCompletedViewHolder(layoutInflater.inflate(R.layout.set_edit_item_completed, parent, false))
    }

    override fun onBindViewHolder(holder: SetEditCompletedViewHolder, position: Int) {
        val item = sets[position]
        holder.render(item, exercisePosition, onMenuItemClick, onItemClick)
    }

    override fun getItemCount(): Int = sets.size

}