package com.marcomadalin.olympus.presentation.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.SetEditItemBinding
import com.marcomadalin.olympus.domain.model.Set

class SetEditViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var binding = SetEditItemBinding.bind(view)

    fun render(set : Set) {
        binding.checkbox.setOnClickListener{
            binding.setEditLayout.setBackgroundResource(R.color.green)
        }
    }

    fun changeBackgroundColor() {
        binding.setEditLayout.setBackgroundResource(R.color.layout)
    }

}