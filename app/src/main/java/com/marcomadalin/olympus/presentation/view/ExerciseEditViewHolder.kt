package com.marcomadalin.olympus.presentation.view

import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.ExerciseEditItemBinding
import com.marcomadalin.olympus.domain.model.Exercise

class ExerciseEditViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    var binding = ExerciseEditItemBinding.bind(view)

    fun render(exercise: Exercise, onClickAdd: (Int) -> Unit, onItemClick: (Pair<Int, Int>) -> Boolean) {
        binding.exerciseName4.text = exercise.exerciseDataId.toString()
        binding.exerciseNoteEdit.setText(exercise.note)
        binding.setRecycler2.layoutManager = LinearLayoutManager(view.context)
        binding.setRecycler2.adapter = SetEditAdapter(exercise.sets)
        binding.addSet.setOnClickListener{onClickAdd(adapterPosition)}
        binding.dropdownEdit.setOnClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)

            popupMenu.setOnMenuItemClickListener{onItemClick(Pair(it.itemId, adapterPosition))}
            popupMenu.inflate(R.menu.exercise_edit_dropdown)
            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java).invoke(mPopup, true)
            } catch (e: Exception){
                Log.e("Main" ,"Error showing menu icons.", e)
            } finally {
                popupMenu.show()
            }
        }

    }
}