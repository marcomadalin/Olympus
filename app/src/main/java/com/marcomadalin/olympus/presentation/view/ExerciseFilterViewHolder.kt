package com.marcomadalin.olympus.presentation.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.ExerciseFilterItemBinding


class ExerciseFilterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = ExerciseFilterItemBinding.bind(view)

    fun render(filter: String, selectFilter: (String) -> Unit) {
        binding.textView4.text = filter
        binding.itemFilter.setOnClickListener{selectFilter(filter)}
    }
}