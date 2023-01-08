package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.marcomadalin.olympus.databinding.FragmentWorkoutLiveFinishedBinding
import com.marcomadalin.olympus.presentation.view.recyclers.WorkoutSummaryAdapter
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel

class WorkoutLiveFinished : Fragment() {

    private var _binding : FragmentWorkoutLiveFinishedBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private lateinit var adapter : WorkoutSummaryAdapter

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWorkoutLiveFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        navController = findNavController()
    }
}