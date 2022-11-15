package com.marcomadalin.olympus.presentation.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.ExerciseFilterItemBinding


class ExerciseFilterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = ExerciseFilterItemBinding.bind(view)

    fun render(
        filter: String,
        selectFilter: (Pair<String, Int>) -> Unit,
        selectedFilters: MutableSet<String>
    ) {
        binding.textView4.text = filter
        binding.itemFilter.setOnClickListener{selectFilter(Pair(filter, absoluteAdapterPosition))}
        if (selectedFilters.contains(filter)) binding.textView4.setBackgroundResource(R.drawable.layout3)
        else binding.textView4.setBackgroundResource(R.drawable.layout2)
    }
}