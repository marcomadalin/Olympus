package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcomadalin.olympus.databinding.FragmentWorkoutSupersetBinding
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutSupersetFragment : Fragment() {

    private var _binding : FragmentWorkoutSupersetBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private lateinit var adapter : ExerciseSupersetAdapter

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWorkoutSupersetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.supersetRecycler.layoutManager = LinearLayoutManager(this.context)
        adapter = ExerciseSupersetAdapter(workoutViewModel.workoutModel.value!!.exercises)
        binding.supersetRecycler.adapter = adapter
        binding.summaryTitle4.text = workoutViewModel.workoutModel.value!!.name
        binding.backButtonSummary4.setOnClickListener{ navController.popBackStack() }
    }

}