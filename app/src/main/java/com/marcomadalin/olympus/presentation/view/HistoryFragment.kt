package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentHistoryBinding
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

//TODO TRANSITION

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding : FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private lateinit var adapter : WorkoutSummaryAdapter

    private lateinit var navController: NavController

    private var first = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        first = true
        navController = findNavController()
        binding.workoutSummary.setOnClickListener {
            navController.navigate(R.id.workoutReview)
            (activity as MainActivity).hideNavigationBar()
        }
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            workoutViewModel.selectedDate.postValue(LocalDate.of(year, month+1, dayOfMonth))
            workoutViewModel.getWorkout()
        }
        binding.summaryRecycler.layoutManager = LinearLayoutManager(this.context)
        adapter = WorkoutSummaryAdapter(emptyList())
        binding.summaryRecycler.adapter = adapter
        workoutViewModel.workout.observe(viewLifecycleOwner) {updateWorkoutSummary(it)}
    }

    private fun updateWorkoutSummary(workout: Workout?) {
        if (workout != null && first) {
            adapter = WorkoutSummaryAdapter(workoutViewModel.workout.value!!.exercises)
            adapter.supersets = workoutViewModel.workout.value!!.supersets
            binding.summaryRecycler.adapter = adapter
            first = false
        }

        if (workout == null) {
            binding.workoutSummary.isVisible = false;
            binding.workoutEmpty.isVisible = true;
        }
        else {
            adapter.supersets = workoutViewModel.workout.value!!.supersets
            binding.workoutEmpty.isVisible = false;
            binding.workoutTitle.text = workout.name
            var volume = 0.0
            workout.exercises.forEach{
                    it -> it.sets.forEach{volume += it.weight}
            }
            binding.workoutVolume.text = "$volume kg"
            binding.workoutTime.text = ((workout.length.seconds%3600)/60).toString() + " min"
            if (workout.length.toHours().toInt() != 0) {
                binding.workoutTime.text = workout.length.toHours().toString() + " " + binding.workoutTime.text
            }
            adapter.notifyDataSetChanged()
            binding.workoutSummary.isVisible = true;
        }
    }
}