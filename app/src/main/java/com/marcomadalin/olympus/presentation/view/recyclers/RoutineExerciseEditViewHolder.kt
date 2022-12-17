package com.marcomadalin.olympus.presentation.view.recyclers

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.RoutineExerciseEditItemBinding
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.presentation.view.util.SupersetColors.colors
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class RoutineExerciseEditViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    var binding = RoutineExerciseEditItemBinding.bind(view)

    lateinit var deleteSet: (Int, Int) -> Unit

    lateinit var adapter: SetEditCompleteAdapter

    private val simpleCallback = object : ItemTouchHelper.SimpleCallback (0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            deleteSet(absoluteAdapterPosition, viewHolder.absoluteAdapterPosition)
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addBackgroundColor(ContextCompat.getColor(view.context, R.color.red))
                .addSwipeLeftActionIcon(R.drawable.delete_white)
                .create()
                .decorate()

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

    }

    fun render(
        exercise: Exercise,
        addSet: (Int) -> Unit,
        onItemClick: (Int, Int) -> Boolean,
        supersets: List<Set<Long>>,
    ) {
        binding.superset7.isVisible = false

        for (i in supersets.indices) {
            if (supersets[i].contains(exercise.id)) {
                binding.superset7.layoutParams.height = binding.layoutExerciseEdit.height
                binding.superset7.setBackgroundColor(Color.parseColor(colors[i]))
                binding.superset7.isVisible = true

            }
        }
        binding.nameRouEd.text = exercise.name
        binding.exerciseNoteEdit2.setText(exercise.note)

        binding.resRouEd.layoutManager = LinearLayoutManager(view.context)
        binding.resRouEd.adapter = RoutineSetEditAdapter(exercise.sets)

        ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.resRouEd)

        binding.addSetRouEd.setOnClickListener{addSet(absoluteAdapterPosition)}

        binding.exerciseNoteEdit2.doOnTextChanged { _, _, _, _ -> exercise.note = binding.exerciseNoteEdit2.text.toString() }

        binding.dropDrouEd.setOnClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)

            popupMenu.setOnMenuItemClickListener{onItemClick(it.itemId, absoluteAdapterPosition)}
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