package com.marcomadalin.olympus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marcomadalin.olympus.databinding.FragmentWorkoutSummaryBinding

class WorkoutSummaryFragment : Fragment() {

    private var _binding : FragmentWorkoutSummaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

}