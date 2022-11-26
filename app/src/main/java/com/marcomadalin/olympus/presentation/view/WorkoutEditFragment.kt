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
import com.marcomadalin.olympus.databinding.FragmentWorkoutEditBinding
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.domain.model.enums.SetType
import com.marcomadalin.olympus.presentation.view.recyclers.ExerciseEditAdapter
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

    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.backButtonSummary2.setOnClickListener {navController.popBackStack()}
        binding.button2.setOnClickListener { addExercise() }
        binding.editRecycler.layoutManager = LinearLayoutManager(this.context)
        adapter = ExerciseEditAdapter(workoutViewModel.selectedWorkout.value!!.exercises,
            {updateNote(it)}, {addSet(it)}, {deleteSet(it)}, {toggleSet(it)}, {onItemClick(it)})
        adapter.supersets = workoutViewModel.selectedWorkout.value!!.supersets
        binding.editRecycler.adapter = adapter
        binding.editRecycler.isNestedScrollingEnabled = false
        updateWorkoutReview( workoutViewModel.selectedWorkout.value)
    }

    private fun onItemClick(data : Pair<Int, Int>) : Boolean {
        return when (data.first) {
            R.id.order -> {
                navController.navigate(R.id.workoutReorderFragment)
                true
            }
            R.id.superset -> {
                navController.navigate(R.id.workoutSupersetFragment)
                true
            }
            R.id.swap -> {
                navController.navigate(R.id.selectExerciseFragment)
                true
            }
            R.id.deleteExercise -> {
                deleteExercise(data.second)
                true
            }
            else -> false
        }
    }

    private fun updateWorkoutReview(workout: Workout?) {
        if (workout != null) {
            adapter.supersets = workoutViewModel.selectedWorkout.value!!.supersets
            binding.summaryTitle2.text = workout.name
            binding.summaryDate2.text = workout.date.dayOfMonth.toString() + " " +
                    workout.date.month.toString().lowercase(Locale.ROOT) + " " + workout.date.year
            binding.summarytNote3.setText(workout.note)
            binding.summarytNote3.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        workout.note = binding.summarytNote3.text.toString()
                        workoutViewModel.selectedWorkout.postValue(workout)
                    }
                }

        }
    }

    private fun addExercise() {
        val workout = workoutViewModel.selectedWorkout.value!!
        val exercise = Exercise(0, workout.id, 5, 1, Duration.ofSeconds(0), "", workout.exercises.size, mutableListOf())
        val set = Set(0, exercise.id, 0.0, 0, 0, 0.0, 0, SetType.Normal, exercise.sets.size)
        exercise.sets.add(set)
        workout.exercises.add(exercise)
        workoutViewModel.selectedWorkout.postValue(workout)
        adapter.notifyItemChanged(workout.exercises.size-1)
    }

    private fun deleteExercise(exercisePosition : Int) {
        val workout = workoutViewModel.selectedWorkout.value!!
        workout.exercises.removeAt(exercisePosition)
        workoutViewModel.selectedWorkout.postValue(workout)
        adapter.notifyItemChanged(exercisePosition)
    }

    private fun addSet(exercisePosition : Int) {
        val workout = workoutViewModel.selectedWorkout.value!!
        val exercise = workout.exercises[exercisePosition]
        val set = Set(0, exercise.id, 0.0, 0, 0, 0.0, 0, SetType.Normal, exercise.sets.size)
        exercise.sets.add(set)
        workoutViewModel.selectedWorkout.postValue(workout)
        adapter.notifyItemChanged(exercisePosition)
    }

    private fun deleteSet(data : Pair<Int, Int>) {
        val workout = workoutViewModel.selectedWorkout.value!!
        val exercise = workout.exercises[data.first]
        exercise.sets.removeAt(data.second)
        workoutViewModel.selectedWorkout.postValue(workout)
        adapter.notifyItemChanged(data.first)
    }

    private fun toggleSet(data : Pair<Int, Int>) {
        val workout = workoutViewModel.selectedWorkout.value!!
        val exercise = workout.exercises[data.first]
        exercise.sets[data.second].completed = !exercise.sets[data.second].completed
        workoutViewModel.selectedWorkout.postValue(workout)
        adapter.notifyItemChanged(data.first)
    }

    private fun updateNote(data : Pair<Int, String>) {
        val workout = workoutViewModel.selectedWorkout.value!!
        workout.exercises[data.first].note = data.second
        workoutViewModel.selectedWorkout.postValue(workout)
        adapter.notifyItemChanged(data.first)
    }

}