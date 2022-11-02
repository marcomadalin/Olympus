package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentWorkoutEditBinding
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WorkoutEditFragment : Fragment() {

    private var _binding : FragmentWorkoutEditBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButtonSummary2.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.workoutReview)
        }
        workoutViewModel.workoutModel.observe(viewLifecycleOwner) {updateWorkoutReview(it)}
        workoutViewModel.getWorkout()
    }

    private fun updateWorkoutReview(workout: Workout?) {
        if (workout != null) {
            binding.summaryTitle2.text = workout.name
            binding.summaryDate2.text = workout.date.dayOfMonth.toString() + " " +
                    workout.date.month.toString().toLowerCase(Locale.ROOT) + " " + workout.date.year
            binding.summarytNote3.setText(workout.note)
            binding.editRecycler.layoutManager = LinearLayoutManager(this.context)
            binding.editRecycler.adapter = ExerciseEditAdapter(workout.exercises)
        }
    }

}