package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import com.marcomadalin.olympus.databinding.FragmentExerciseReviewBinding
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseDataViewModel

class ExerciseReviewFragment : Fragment() {

    private var _binding : FragmentExerciseReviewBinding? = null
    private val binding get() = _binding!!

    private val exerciseDataViewModel : ExerciseDataViewModel by activityViewModels()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

    }
}