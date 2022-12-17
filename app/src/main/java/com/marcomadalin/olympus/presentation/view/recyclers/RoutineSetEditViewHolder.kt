package com.marcomadalin.olympus.presentation.view.recyclers

import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.RoutineSetEditItemBinding
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.enums.SetType
import com.marcomadalin.olympus.presentation.view.util.InputFilterMinMax

class RoutineSetEditViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = RoutineSetEditItemBinding.bind(view)

    fun render(set: Set) {

        if (absoluteAdapterPosition % 2 == 1) {
            binding.layoutSetRou.setBackgroundResource(R.color.layout)
            binding.repsNumber2.setBackgroundResource(R.drawable.layout_marble_set)
            binding.weightNumber2.setBackgroundResource(R.drawable.layout_marble_set)
            binding.rirNumber2.setBackgroundResource(R.drawable.layout_marble_set)
        }

        binding.setTypeEdit2.text = (1 + set.setNumber).toString() + " - " + set.type.toString()[0].toString()
        val color : Int = Color.parseColor(getColor(set.type))
        binding.setTypeEdit2.setTextColor(color)
        binding.rirNumber2.filters = arrayOf(InputFilterMinMax(0, 10))
        binding.weightNumber2.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        if (set.lastReps >= 0) binding.prevWeight2.text = set.lastWeight.toString() + " kg x " + set.lastReps + "\n RIR " + set.lastRir
        else binding.prevWeight2.text = "-"

        binding.repsNumber2.setText(set.reps.toString())
        binding.weightNumber2.setText(set.weight.toString())
        binding.rirNumber2.setText(set.rir.toString())

        binding.prevWeight2.setOnClickListener {
            if (set.lastReps >= 0) {
                set.weight = set.lastWeight
                set.reps = set.lastReps
                set.rir = set.lastRir

                binding.repsNumber2.setText(set.reps.toString())
                binding.weightNumber2.setText(set.weight.toString())
                binding.rirNumber2.setText(set.rir.toString())
            }
        }

        binding.weightNumber2.doOnTextChanged { _, _, _, _ ->
            if (!binding.weightNumber2.text.isNullOrBlank()) set.weight = binding.weightNumber2.text.toString().toDouble()
        }

        binding.repsNumber2.doOnTextChanged { _, _, _, _ ->
            if (!binding.repsNumber2.text.isNullOrBlank()) set.reps = binding.repsNumber2.text.toString().toInt()
        }
        binding.rirNumber2.doOnTextChanged { _, _, _, _ ->
            if (!binding.rirNumber2.text.isNullOrBlank()) set.rir = binding.rirNumber2.text.toString().toInt()
        }

        binding.setTypeEdit2.setOnClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.setOnMenuItemClickListener{
                 when (it.itemId) {
                    R.id.normalSet -> {
                        set.type = SetType.Normal
                    }
                    R.id.warmupSet -> {
                        set.type = SetType.Warmup
                    }
                    R.id.dropSet -> {
                        set.type = SetType.Drop
                    }
                    R.id.failureSet -> {
                        set.type = SetType.Failure
                    }
                }

                binding.setTypeEdit2.text = (1 + set.setNumber).toString() + " - " + set.type.toString()[0].toString()
                val color : Int = Color.parseColor(getColor(set.type))
                binding.setTypeEdit2.setTextColor(color)
                true
            }

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