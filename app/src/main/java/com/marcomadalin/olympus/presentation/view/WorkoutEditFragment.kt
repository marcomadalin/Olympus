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
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.domain.model.enums.SetType
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.util.*

@AndroidEntryPoint
class WorkoutEditFragment : Fragment() {

    private var _binding : FragmentWorkoutEditBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private lateinit var adapter : ExerciseEditAdapter

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
        binding.button2.setOnClickListener { addExercise() }
        binding.editRecycler.layoutManager = LinearLayoutManager(this.context)
        adapter = ExerciseEditAdapter(workoutViewModel.workoutModel.value!!.exercises, {addSet(it)})
        binding.editRecycler.adapter = adapter
        updateWorkoutReview( workoutViewModel.workoutModel.value)
    }

    private fun updateWorkoutReview(workout: Workout?) {
        if (workout != null) {
            binding.summaryTitle2.text = workout.name
            binding.summaryDate2.text = workout.date.dayOfMonth.toString() + " " +
                    workout.date.month.toString().toLowerCase(Locale.ROOT) + " " + workout.date.year
            binding.summarytNote3.setText(workout.note)
        }
    }

    private fun addExercise() {
        var workout = workoutViewModel.workoutModel.value!!
        var exercise = Exercise(0, workout.id, 5, Duration.ofSeconds(0), "", workout.exercises.size, mutableListOf())
        var set = Set(0, exercise.id, 0.0, 0, 0, 0.0, 0, SetType.Normal, exercise.sets.size)
        exercise.sets.add(set)
        workout.exercises.add(exercise)
        workoutViewModel.workoutModel.postValue(workout)
        adapter.notifyItemChanged(workout.exercises.size-1)
    }

    private fun deleteExercise(exercisePosition : Int) {
        var workout = workoutViewModel.workoutModel.value!!
        workout.exercises.removeAt(exercisePosition)
        workoutViewModel.workoutModel.postValue(workout)
        adapter.notifyItemChanged(exercisePosition)
    }

    private fun addSet(exercisePosition : Int) {
        var workout = workoutViewModel.workoutModel.value!!
        var exercise = workout.exercises[exercisePosition]
        var set = Set(0, exercise.id, 0.0, 0, 0, 0.0, 0, SetType.Normal, exercise.sets.size)
        exercise.sets.add(set)
        workoutViewModel.workoutModel.postValue(workout)
        adapter.notifyItemChanged(exercisePosition)
    }

    private fun deleteSet(exercisePosition : Int, setPosition : Int) {
        var workout = workoutViewModel.workoutModel.value!!
        var exercise = workout.exercises[exercisePosition]
        exercise.sets.removeAt(setPosition)
        workoutViewModel.workoutModel.postValue(workout)
        adapter.notifyItemChanged(exercisePosition)
    }

}