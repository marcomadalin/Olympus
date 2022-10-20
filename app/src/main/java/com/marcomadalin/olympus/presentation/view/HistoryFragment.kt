package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marcomadalin.olympus.databinding.FragmentHistoryBinding
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate


@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding : FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private var selectedDate : LocalDate = LocalDate.now()
    private val workoutViewModel : WorkoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = LocalDate.of(year, month+1, dayOfMonth)
            workoutViewModel.getWorkout(selectedDate)
        }
        workoutViewModel.workoutModel.observe(viewLifecycleOwner) {updateWorkoutSummary(it)}
        workoutViewModel.getWorkout(selectedDate)
    }

    private fun updateWorkoutSummary(workout: Workout?) {
        if (workout == null) {
            binding.workoutSummary.isVisible = false;
            binding.workoutEmpty.isVisible = true;
        }
        else {
            binding.workoutEmpty.isVisible = false;
            binding.workoutTitle.text = workout.name
            binding.workoutTime.text = (workout.length.seconds%60).toString() + " min"
            if (workout.length.toHours().toInt() != 0) {
                binding.workoutTime.text = workout.length.toHours().toString() + " " + binding.workoutTime.text
            }
            binding.workoutSummary.isVisible = true;
        }
    }
}