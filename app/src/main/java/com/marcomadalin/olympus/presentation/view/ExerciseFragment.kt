package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import com.marcomadalin.olympus.databinding.FragmentExerciseBinding
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseFragment : Fragment() {

    private var _binding : FragmentExerciseBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : ExerciseDataViewModel by activityViewModels()

   // private lateinit var adapter : ExercisesAdapter

    private lateinit var navController: NavController

    private var first = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }


}