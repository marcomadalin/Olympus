package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.domain.model.Set

class SetLiveAdapter(private val sets: List<Set>) : RecyclerView.Adapter<SetLiveViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetLiveViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SetLiveViewHolder(layoutInflater.inflate(R.layout.set_live_item, parent, false))
    }

    override fun onBindViewHolder(holder: SetLiveViewHolder, position: Int) {
        val item = sets[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = sets.size

}