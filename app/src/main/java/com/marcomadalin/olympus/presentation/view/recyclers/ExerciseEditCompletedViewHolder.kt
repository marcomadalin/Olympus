package com.marcomadalin.olympus.presentation.view.recyclers

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.ExerciseEditItemCompletedBinding
import com.marcomadalin.olympus.domain.model.Exercise
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class ExerciseEditCompletedViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    var binding = ExerciseEditItemCompletedBinding.bind(view)

    lateinit var deleteSet: (Pair<Int, Int>) -> Unit

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
            deleteSet(Pair(absoluteAdapterPosition, viewHolder.absoluteAdapterPosition))
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
        updateNote: (Pair<Int, String>) -> Unit,
        addSet: (Int) -> Unit,
        onItemClick: (Pair<Int, Int>) -> Boolean,
        onSetItemClick: (exercisePos: Int, setPos: Int, menuItemId: Int) -> Boolean,
        onSetMenuItemClick: (exercisePos: Int, setPos: Int, buttonPressed: Int, value: Double) -> Unit,
        colors: List<String>,
        supersets: List<Set<Long>>,
    ) {
        binding.superset.isVisible = false
        for (i in supersets.indices) {
            if (supersets[i].contains(exercise.id)) {
                binding.superset.setBackgroundColor(Color.parseColor(colors[i]))
                binding.superset.isVisible = true
            }
        }
        binding.exerciseName4.text = exercise.name
        binding.exerciseNoteEdit.setText(exercise.note)
        binding.setRecycler2.layoutManager = LinearLayoutManager(view.context)
        binding.setRecycler2.adapter = SetEditCompleteAdapter(exercise.sets, absoluteAdapterPosition, onSetItemClick, onSetMenuItemClick)
        ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.setRecycler2)
        binding.addSet.setOnClickListener{addSet(absoluteAdapterPosition)}
        binding.exerciseNoteEdit.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                updateNote(Pair(absoluteAdapterPosition, binding.exerciseNoteEdit.text.toString()))
            }
        }
        binding.dropdownEdit.setOnClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)

            popupMenu.setOnMenuItemClickListener{onItemClick(Pair(it.itemId, absoluteAdapterPosition))}
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