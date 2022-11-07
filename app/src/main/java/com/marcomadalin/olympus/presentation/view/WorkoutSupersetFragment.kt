package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.marcomadalin.olympus.databinding.FragmentWorkoutSupersetBinding
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutSupersetFragment : Fragment() {

    private var _binding : FragmentWorkoutSupersetBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private lateinit var adapter : WorkoutSummaryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWorkoutSupersetBinding.inflate(inflater, container, false)
        return binding.root
    }

}