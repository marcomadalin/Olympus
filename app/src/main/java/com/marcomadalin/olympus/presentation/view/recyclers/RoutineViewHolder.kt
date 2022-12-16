package com.marcomadalin.olympus.presentation.view.recyclers

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.RoutineItemBinding
import com.marcomadalin.olympus.domain.model.Routine


class RoutineViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    var binding = RoutineItemBinding.bind(view)

    fun render(routine: Routine, onRoutineClick: (Int)->Unit) {
        binding.routineTitle.text = routine.name
        binding.recyclerRoutineItem.layoutManager = LinearLayoutManager(view.context)
        binding.recyclerRoutineItem.adapter = RoutineExerciseAdapter(routine.exercises, routine.supersets)
        binding.routineItemLayout.setOnClickListener{onRoutineClick(absoluteAdapterPosition)}
    }
}