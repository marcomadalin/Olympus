package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import com.marcomadalin.olympus.databinding.FragmentLiveWorkoutBinding
import com.marcomadalin.olympus.presentation.view.recyclers.ExerciseEditCompletedAdapter
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel

class LiveWorkoutFragment : Fragment() {
    private var _binding: FragmentLiveWorkoutBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    private val exerciseViewModel: ExerciseViewModel by activityViewModels()

    private lateinit var adapter: ExerciseEditCompletedAdapter

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLiveWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }
}