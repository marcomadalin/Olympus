package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentWorkoutEditBinding
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.enums.SetType
import com.marcomadalin.olympus.presentation.view.recyclers.ExerciseEditCompletedAdapter
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.util.*

@AndroidEntryPoint
class WorkoutEditFragment : Fragment() {

    private var _binding : FragmentWorkoutEditBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private val exerciseViewModel : ExerciseViewModel by activityViewModels()

    private lateinit var adapter : ExerciseEditCompletedAdapter

    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        navController = findNavController()

        if (exerciseViewModel.selectedExercises.value!!.isNotEmpty()) {
            val workout = workoutViewModel.selectedWorkout.value!!
            exerciseViewModel.selectedExercises.value!!.forEach { selectedExercise ->
                val exercise = Exercise(
                    0,
                    selectedExercise.value,
                    workout.id,
                    0,
                    selectedExercise.key,
                    Duration.ofSeconds(0),
                    "",
                    workout.exercises.size,
                    mutableListOf()
                )
                val set = Set(0, exercise.id, 0.0, 0, 0, 0.0, 0, 0, SetType.Normal, exercise.sets.size, true)
                exercise.sets.add(set)

                if (exerciseViewModel.selectOne.value!!) {
                    val position = exerciseViewModel.swappedExercisePosition.value!!
                    for ((index, superset) in workout.supersets.withIndex()) {
                        if (superset.contains(exerciseViewModel.oldExerciseId.value!!)) {
                            superset.remove(exerciseViewModel.oldExerciseId.value!!)
                            if (superset.size == 1) workout.supersets.removeAt(index)
                            break
                        }
                    }
                    exerciseViewModel.deleteExercise(workout.exercises[position])
                    exercise.exerciseNumber = position
                    workout.exercises[position] = exercise
                }
                else workout.exercises.add(exercise)
            }
            exerciseViewModel.selectedExercises.value = mutableMapOf()
        }

        binding.backButtonSummary2.setOnClickListener {navController.popBackStack()}
        binding.button2.setOnClickListener { addExercise() }

        binding.editRecycler.layoutManager = LinearLayoutManager(this.context)
        adapter = ExerciseEditCompletedAdapter(::addSet, ::deleteSet, ::onItemClick)

        adapter.exercises = workoutViewModel.selectedWorkout.value!!.exercises
        adapter.supersets = workoutViewModel.selectedWorkout.value!!.supersets

        binding.editRecycler.adapter = adapter
        binding.editRecycler.isNestedScrollingEnabled = false

        val workout = workoutViewModel.selectedWorkout.value

        if (workout != null) {
            adapter.supersets = workoutViewModel.selectedWorkout.value!!.supersets
            binding.summaryTitle2.text = workout.name
            binding.summaryDate2.text = workout.date.dayOfMonth.toString() + " " +
                    workout.date.month.toString().lowercase(Locale.ROOT) + " " + workout.date.year
            binding.summarytNote3.setText(workout.note)
            binding.summarytNote3.doOnTextChanged { _, _, _, _ -> workout.note = binding.summarytNote3.text.toString() }
        }
    }

    override fun onStop() {
        super.onStop()
        workoutViewModel.saveWorkout(workoutViewModel.selectedWorkout.value!!)
    }

    private fun onItemClick(itemId: Int, exercisePosition : Int) : Boolean {
        return when (itemId) {
            R.id.order -> {
                navController.navigate(R.id.workoutReorderFragment)
                true
            }
            R.id.superset -> {
                navController.navigate(R.id.workoutSupersetFragment)
                true
            }
            R.id.swap -> {
                exerciseViewModel.selectOne.value = true
                exerciseViewModel.oldExerciseId.value = workoutViewModel.selectedWorkout.value!!.exercises[exercisePosition].id
                exerciseViewModel.swappedExercisePosition.value = exercisePosition
                navController.navigate(R.id.selectExerciseFragment)
                true
            }
            R.id.deleteExercise -> {
                deleteExercise(exercisePosition)
                true
            }
            else -> false
        }
    }

    private fun addExercise() {
        exerciseViewModel.selectOne.value = false
        navController.navigate(R.id.selectExerciseFragment)
    }

    private fun deleteExercise(exercisePosition : Int) {
        val workout = workoutViewModel.selectedWorkout.value!!
        exerciseViewModel.deleteExercise( workout.exercises[exercisePosition])
        for (i in exercisePosition until workout.exercises.size) --workout.exercises[i].exerciseNumber

        for ((index, superset) in workout.supersets.withIndex()) {
            if (superset.contains(workout.exercises[exercisePosition].id)) {
                superset.remove(workout.exercises[exercisePosition].id)
                if (superset.size == 1) workout.supersets.removeAt(index)
                break
            }
        }
        adapter.supersets = workout.supersets
        workout.exercises.removeAt(exercisePosition)
        adapter.exercises = workout.exercises
        adapter.notifyDataSetChanged()
    }

    private fun addSet(exercisePosition : Int) {
        val workout = workoutViewModel.selectedWorkout.value!!
        val exercise = workout.exercises[exercisePosition]
        val set = Set(0, exercise.id, 0.0, 0, 0, 0.0, 0, 0, SetType.Normal, exercise.sets.size, true)
        exercise.sets.add(set)
        adapter.exercises = workout.exercises
        adapter.notifyItemChanged(exercisePosition)
    }

    private fun deleteSet(exercisePosition: Int, setPosition : Int) {
        val workout = workoutViewModel.selectedWorkout.value!!
        val exercise = workout.exercises[exercisePosition]
        exerciseViewModel.deleteSet(exercise.sets[setPosition])
        exercise.sets.removeAt(setPosition)

        if (exercise.sets.isEmpty()) deleteExercise(exercisePosition)
        else {
            adapter.exercises = workout.exercises
            adapter.notifyItemChanged(exercisePosition)
        }
    }
}