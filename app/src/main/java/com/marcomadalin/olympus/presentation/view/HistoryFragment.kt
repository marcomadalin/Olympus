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
import com.marcomadalin.olympus.presentation.view.recyclers.WorkoutSummaryAdapter
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*

//TODO TRANSITION

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding : FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private lateinit var adapter : WorkoutSummaryAdapter

    private lateinit var navController: NavController

    private var initAdapter = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).showNavigationBar()
        initAdapter = true
        navController = findNavController()
        binding.workoutSummary.setOnClickListener {
            navController.navigate(R.id.workoutReview)
            (activity as MainActivity).hideNavigationBar()
        }
        val date = workoutViewModel.selectedDate.value!!.toString()
        val parts = date.split("-".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()

        val day = parts[2].toInt()
        val month = parts[1].toInt()-1
        val year = parts[0].toInt()

        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        val milliTime: Long = calendar.timeInMillis
        binding.calendarView.setDate (milliTime, true, true);

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            workoutViewModel.selectedDate.value = LocalDate.of(year, month+1, dayOfMonth)
            workoutViewModel.getWorkout()
        }
        binding.summaryRecycler.layoutManager = LinearLayoutManager(this.context)
        adapter = WorkoutSummaryAdapter(emptyList())
        binding.summaryRecycler.adapter = adapter
        workoutViewModel.selectedWorkout.observe(viewLifecycleOwner) {updateWorkoutSummary(it)}
        workoutViewModel.getWorkout()
    }

    private fun updateWorkoutSummary(workout: Workout?) {
        if (workout == null) {
            adapter = WorkoutSummaryAdapter(emptyList())
            adapter.supersets = emptyList()
            binding.summaryRecycler.adapter = adapter
            initAdapter = true
            adapter.notifyDataSetChanged()

            binding.workoutSummary.isVisible = false;
            binding.workoutEmpty.isVisible = true;
        }
        else {
            adapter = WorkoutSummaryAdapter(workoutViewModel.selectedWorkout.value!!.exercises)
            adapter.supersets = workoutViewModel.selectedWorkout.value!!.supersets
            binding.summaryRecycler.adapter = adapter
            binding.workoutEmpty.isVisible = false;
            binding.workoutTitle.text = workout.name
            var volume = 0.0
            workout.exercises.forEach{
                    it -> it.sets.forEach{volume += it.weight * it.reps}
            }
            binding.workoutVolumeText.text = "$volume kg"
            binding.workoutTime.text = ((workout.length.seconds%3600)/60).toString() + " min"
            if (workout.length.toHours().toInt() != 0) {
                binding.workoutTime.text = workout.length.toHours().toString() + " h " + binding.workoutTime.text
            }
            adapter.notifyDataSetChanged()
            binding.workoutSummary.isVisible = true;
        }
    }
}