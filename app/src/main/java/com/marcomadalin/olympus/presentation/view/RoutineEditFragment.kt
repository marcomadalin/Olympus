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
import com.marcomadalin.olympus.databinding.FragmentRoutineEditBinding
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.enums.SetType
import com.marcomadalin.olympus.presentation.view.recyclers.RoutineExerciseEditAdapter
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseViewModel
import com.marcomadalin.olympus.presentation.viewmodel.RoutineViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration

@AndroidEntryPoint
class RoutineEditFragment : Fragment() {

    private var _binding : FragmentRoutineEditBinding? = null
    private val binding get() = _binding!!

    private val routineViewModel : RoutineViewModel by activityViewModels()

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private val exerciseViewModel : ExerciseViewModel by activityViewModels()

    private lateinit var adapter : RoutineExerciseEditAdapter

    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoutineEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        navController = findNavController()

        if (exerciseViewModel.selectedExercises.value!!.isNotEmpty()) {
            val routine = routineViewModel.selectedRoutine.value!!
            exerciseViewModel.selectedExercises.value!!.forEach { selectedExercise ->
                val exercise = Exercise(
                    0,
                    selectedExercise.value,
                    0,
                    routine.id,
                    selectedExercise.key,
                    Duration.ofSeconds(0),
                    "",
                    routine.exercises.size,
                    mutableListOf(),
                )
                val set = Set(0, exercise.id, 0.0, 0, 0, 0.0, 0, 0, SetType.Normal, exercise.sets.size, false)
                exercise.sets.add(set)

                if (exerciseViewModel.selectOne.value!!) {
                    val position = exerciseViewModel.swappedExercisePosition.value!!
                    for ((index, superset) in routine.supersets.withIndex()) {
                        if (superset.contains(exerciseViewModel.oldExerciseId.value!!)) {
                            superset.remove(exerciseViewModel.oldExerciseId.value!!)
                            if (superset.size == 1) routine.supersets.removeAt(index)
                            break
                        }
                    }
                    exerciseViewModel.deleteExercise(routine.exercises[position])
                    exercise.exerciseNumber = position
                    routine.exercises[position] = exercise
                }
                else routine.exercises.add(exercise)
            }
            exerciseViewModel.selectedExercises.value = mutableMapOf()
        }

        binding.backRouEd.setOnClickListener {
            if (routineViewModel.newRoutine.value!!) routineViewModel.deleteRoutine(routineViewModel.selectedRoutine.value!!)
            navController.popBackStack()
        }
        binding.addExRouEd.setOnClickListener { addExercise() }

        binding.recyclerRouEd.layoutManager = LinearLayoutManager(this.context)
        adapter = RoutineExerciseEditAdapter(::addSet, ::deleteSet, ::onItemClick)

        adapter.exercises = routineViewModel.selectedRoutine.value!!.exercises
        adapter.supersets = routineViewModel.selectedRoutine.value!!.supersets

        binding.recyclerRouEd.adapter = adapter
        binding.recyclerRouEd.isNestedScrollingEnabled = false

        val routine = routineViewModel.selectedRoutine.value

        if (routine != null) {
            adapter.supersets = routineViewModel.selectedRoutine.value!!.supersets

            binding.routineTitleEdit.setText(routine.name)
            binding.routineTitleEdit.doOnTextChanged { _, _, _, _ -> routine.name = binding.routineTitleEdit.text.toString() }
            binding.noteRouEd.setText(routine.note)
            binding.noteRouEd.doOnTextChanged { _, _, _, _ -> routine.note = binding.noteRouEd.text.toString() }

            if (routineViewModel.newRoutine.value!!) {
                binding.done2.visibility = View.VISIBLE
                binding.done2.setOnClickListener{
                    routineViewModel.saveRoutine(routineViewModel.selectedRoutine.value!!)
                    navController.popBackStack()
                }
                routineViewModel.selectedRoutine.observe(viewLifecycleOwner) {
                    binding.done2.isEnabled  = routineViewModel.selectedRoutine.value!!.exercises.size > 0
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (routineViewModel.newRoutine.value!!) routineViewModel.deleteRoutine(routineViewModel.selectedRoutine.value!!)
        else routineViewModel.saveRoutine(routineViewModel.selectedRoutine.value!!)
    }

    private fun onItemClick(itemId: Int, exercisePosition : Int) : Boolean {
        return when (itemId) {
            R.id.order -> {
                workoutViewModel.editingRoutine.value = true
                navController.navigate(R.id.workoutReorderFragment)
                true
            }
            R.id.superset -> {
                workoutViewModel.editingRoutine.value = true
                navController.navigate(R.id.workoutSupersetFragment)
                true
            }
            R.id.swap -> {
                exerciseViewModel.selectOne.value = true
                exerciseViewModel.oldExerciseId.value = routineViewModel.selectedRoutine.value!!.exercises[exercisePosition].id
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
        val routine = routineViewModel.selectedRoutine.value!!
        exerciseViewModel.deleteExercise( routine.exercises[exercisePosition])
        for (i in exercisePosition until routine.exercises.size) --routine.exercises[i].exerciseNumber

        for ((index, superset) in routine.supersets.withIndex()) {
            if (superset.contains(routine.exercises[exercisePosition].id)) {
                superset.remove(routine.exercises[exercisePosition].id)
                if (superset.size == 1) routine.supersets.removeAt(index)
                break
            }
        }
        adapter.supersets = routine.supersets
        routine.exercises.removeAt(exercisePosition)
        adapter.exercises = routine.exercises
        adapter.notifyDataSetChanged()
    }

    private fun addSet(exercisePosition : Int) {
        val routine = routineViewModel.selectedRoutine.value!!
        val exercise = routine.exercises[exercisePosition]
        val set = Set(exercise.sets.last())
        set.setNumber = exercise.sets.size
        set.type = SetType.Normal
        exercise.sets.add(set)
        adapter.exercises = routine.exercises
        adapter.notifyItemChanged(exercisePosition)
    }

    private fun deleteSet(exercisePosition: Int, setPosition : Int) {
        val routine = routineViewModel.selectedRoutine.value!!
        val exercise = routine.exercises[exercisePosition]
        exerciseViewModel.deleteSet(exercise.sets[setPosition])
        exercise.sets.removeAt(setPosition)

        if (exercise.sets.isEmpty()) deleteExercise(exercisePosition)
        else {
            adapter.exercises = routine.exercises
            adapter.notifyItemChanged(exercisePosition)
        }
    }
}