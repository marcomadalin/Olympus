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
import com.marcomadalin.olympus.R
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
        (activity as MainActivity).hideNavigationBar()
        workoutViewModel.workouts.observe(viewLifecycleOwner) {
            binding.textView8.text  = "This is your workout number " + workoutViewModel.workouts.value!!.size + " !"
        }

        workoutViewModel.getWorkouts()
        navController = findNavController()

        binding.workoutSummaryFinish.setOnClickListener {
            navController.navigate(R.id.workoutReview)
            (activity as MainActivity).hideNavigationBar()
        }

        binding.close2.setOnClickListener {
            val mainActivity = (activity as MainActivity)
            mainActivity.showNavigationBar()
            (mainActivity).binding.navbar.menu.findItem(R.id.history).isChecked = true
            navController.navigate(R.id.history)
        }

        binding.summaryRecyclerFinish.layoutManager = LinearLayoutManager(this.context)
        adapter = WorkoutSummaryAdapter(workoutViewModel.selectedWorkout.value!!.exercises)
        binding.summaryRecyclerFinish.adapter = adapter

        adapter.supersets = workoutViewModel.selectedWorkout.value!!.supersets
        val workout = workoutViewModel.selectedWorkout.value!!

        binding.workoutTitle2.text = workout.name
        var volume = 0.0
        workout.exercises.forEach{
                it -> it.sets.forEach{volume += it.weight * it.reps}
        }
        binding.workoutVolumeText.text = "$volume kg"
        binding.workoutTime.text = ((workout.length.seconds%3600)/60).toString() + " min"
        if (workout.length.toHours().toInt() != 0) {
            binding.workoutTime.text = workout.length.toHours().toString() + " h " + binding.workoutTime.text
        }
    }
}