package com.marcomadalin.olympus.presentation.view.recyclers

import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.SetEditItemCompletedBinding
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.enums.SetType
import com.marcomadalin.olympus.presentation.view.util.InputFilterMinMax

class SetEditCompletedViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = SetEditItemCompletedBinding.bind(view)

    fun render(
        set: Set,
        exercisePosition: Int,
        onItemClick: (exercisePos: Int, setPos: Int, menuItemId: Int) -> Boolean
    ) {

        binding.setTypeEdit.text = (1 + set.setNumber).toString() + " - " + set.type.toString()[0].toString()
        val color : Int = Color.parseColor(getColor(set.type))
        binding.setTypeEdit.setTextColor(color)
        binding.rirNumber.filters = arrayOf(InputFilterMinMax(0, 10))
        binding.weightNumber.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL


        if (set.lastReps > 0) {
            binding.prevWeight.text = set.lastWeight.toString() + " x " + set.lastReps
            binding.repsNumber.setText(set.lastReps.toString())
            binding.weightNumber.setText(set.lastWeight.toString())
        }
        else binding.prevWeight.text = "-"

        binding.prevWeight.setOnClickListener{
            if (set.lastReps > 0) {
                binding.repsNumber.setText(set.lastReps.toString())
                binding.weightNumber.setText(set.lastWeight.toString())
            }
        }

        binding.setTypeEdit.setOnClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.setOnMenuItemClickListener{onItemClick(exercisePosition, absoluteAdapterPosition, it.itemId)}
            popupMenu.inflate(R.menu.set_type_dropdown)

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

    private fun getColor(type : SetType) : String {
        return when (type) {
            SetType.Normal -> "#5E5D5D"
            SetType.Warmup -> "#D86800"
            SetType.Drop -> "#2196F3"
            else -> "#7A0098"
        }
    }
}