package com.marcomadalin.olympus.presentation.view.recyclers

import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.SetLiveItemBinding
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.enums.SetType
import com.marcomadalin.olympus.presentation.view.util.InputFilterMinMax

class SetLiveViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = SetLiveItemBinding.bind(view)

    fun render(set: Set) {

        binding.setTypeEdit3.text = (1 + set.setNumber).toString() + " - " + set.type.toString()[0].toString()
        val color : Int = Color.parseColor(getColor(set.type))
        binding.setTypeEdit3.setTextColor(color)
        binding.rirNumber3.filters = arrayOf(InputFilterMinMax(0, 10))
        binding.weightNumber3.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        if (set.lastReps >= 0) binding.prevWeight3.text = set.lastWeight.toString() + " kg x " + set.lastReps + " RIR " + set.lastRir
        else binding.prevWeight3.text = "-"

        setBackgroundColor(set)

        binding.repsNumber3.setText(set.reps.toString())
        binding.weightNumber3.setText(set.weight.toString())
        binding.rirNumber3.setText(set.rir.toString())
        binding.completeButton.setOnClickListener {
            set.completed = !set.completed
            setBackgroundColor(set)
        }

        binding.prevWeight3.setOnClickListener {
            if (set.lastReps >= 0) {
                set.weight = set.lastWeight
                set.reps = set.lastReps
                set.rir = set.lastRir

                binding.repsNumber3.setText(set.reps.toString())
                binding.weightNumber3.setText(set.weight.toString())
                binding.rirNumber3.setText(set.rir.toString())
            }
        }

        binding.weightNumber3.doOnTextChanged { _, _, _, _ ->
            if (!binding.weightNumber3.text.isNullOrBlank()) set.weight = binding.weightNumber3.text.toString().toDouble()
        }

        binding.repsNumber3.doOnTextChanged { _, _, _, _ ->
            if (!binding.repsNumber3.text.isNullOrBlank()) set.reps = binding.repsNumber3.text.toString().toInt()
        }
        binding.rirNumber3.doOnTextChanged { _, _, _, _ ->
            if (!binding.rirNumber3.text.isNullOrBlank()) set.rir = binding.rirNumber3.text.toString().toInt()
        }

        binding.setTypeEdit3.setOnClickListener { view ->
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

                binding.setTypeEdit3.text = (1 + set.setNumber).toString() + " - " + set.type.toString()[0].toString()
                val color : Int = Color.parseColor(getColor(set.type))
                binding.setTypeEdit3.setTextColor(color)
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

    private fun setBackgroundColor(set: Set) {
        if (set.completed) {
            binding.setEditLayoutLive.setBackgroundResource(R.color.green)
            binding.rirNumber3.setBackgroundResource(R.drawable.layout_green_set)
            binding.weightNumber3.setBackgroundResource(R.drawable.layout_green_set)
            binding.repsNumber3.setBackgroundResource(R.drawable.layout_green_set)
        }
        else {
            if (absoluteAdapterPosition % 2 == 0) {
                binding.setEditLayoutLive.setBackgroundResource(R.color.background)
                binding.rirNumber3.setBackgroundResource(R.drawable.layout_gray_set)
                binding.weightNumber3.setBackgroundResource(R.drawable.layout_gray_set)
                binding.repsNumber3.setBackgroundResource(R.drawable.layout_gray_set)
            }
            else {
                binding.setEditLayoutLive.setBackgroundResource(R.color.layout)
                binding.rirNumber3.setBackgroundResource(R.drawable.layout_marble_set)
                binding.weightNumber3.setBackgroundResource(R.drawable.layout_marble_set)
                binding.repsNumber3.setBackgroundResource(R.drawable.layout_marble_set)
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