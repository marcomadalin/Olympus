package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentWorkoutSupersetBinding
import com.marcomadalin.olympus.domain.model.Routine
import com.marcomadalin.olympus.presentation.view.recyclers.ExerciseSupersetAdapter
import com.marcomadalin.olympus.presentation.viewmodel.RoutineViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutSupersetFragment : Fragment() {

    private var _binding : FragmentWorkoutSupersetBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private val routineViewModel : RoutineViewModel by activityViewModels()

    private lateinit var adapter : ExerciseSupersetAdapter

    private lateinit var navController: NavController

    private var superset : MutableSet<Long> = mutableSetOf()
    private var removedExercises : MutableSet<Long> = mutableSetOf()

    private var changesDone = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWorkoutSupersetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        changesDone = false

        binding.imageButton3.isEnabled = false
        navController = findNavController()
        binding.supersetRecycler.layoutManager = LinearLayoutManager(this.context)

        if (workoutViewModel.editingRoutine.value!!) {
            adapter = ExerciseSupersetAdapter(routineViewModel.selectedRoutine.value!!.exercises, ::selectSet)
            adapter.supersets = routineViewModel.selectedRoutine.value!!.supersets
            binding.summaryTitle4.text = routineViewModel.selectedRoutine.value!!.name
        }
        else {
            if (workoutViewModel.editingLive.value!!) {
                adapter = ExerciseSupersetAdapter(workoutViewModel.liveWorkout.value!!.exercises, ::selectSet)
                adapter.supersets = workoutViewModel.liveWorkout.value!!.supersets
                binding.summaryTitle4.text = workoutViewModel.liveWorkout.value!!.name
            }
            else {
                adapter = ExerciseSupersetAdapter(workoutViewModel.selectedWorkout.value!!.exercises, ::selectSet)
                adapter.supersets = workoutViewModel.selectedWorkout.value!!.supersets
                binding.summaryTitle4.text = workoutViewModel.selectedWorkout.value!!.name
            }
        }
        binding.supersetRecycler.adapter = adapter
        binding.supersetRecycler.addItemDecoration(DividerItemDecoration(binding.supersetRecycler.context, DividerItemDecoration.VERTICAL))
        binding.backButtonSummary4.setOnClickListener{ navController.popBackStack() }

        binding.imageButton3.setOnClickListener{
            if (binding.imageButton3.isEnabled) {
                changesDone = true

                val workout = if (workoutViewModel.editingRoutine.value!!) routineViewModel.selectedRoutine.value!!
                else {
                    if (workoutViewModel.editingLive.value!!) workoutViewModel.liveWorkout.value!!
                    else workoutViewModel.selectedWorkout.value!!
                }

                if (superset.isNotEmpty()) {
                    val newSupersets : MutableList<MutableSet<Long>> = removeSingleSupersets(workout, superset)
                    newSupersets.add(superset)
                    superset = mutableSetOf()
                    workout.supersets = newSupersets
                    binding.imageButton3.isEnabled = false
                }
                else {
                    val newSupersets : MutableList<MutableSet<Long>> = removeSingleSupersets(workout, removedExercises)
                    removedExercises = mutableSetOf()
                    workout.supersets = newSupersets
                }
                binding.imageButton3.setBackgroundResource(R.drawable.lock_disable)
                binding.imageButton3.isEnabled = false

                adapter.supersets = if (workoutViewModel.editingRoutine.value!!) routineViewModel.selectedRoutine.value!!.supersets
                else {
                    if (workoutViewModel.editingLive.value!!) workoutViewModel.liveWorkout.value!!.supersets
                    else workoutViewModel.selectedWorkout.value!!.supersets
                }

                adapter.selected = false
                adapter.added = false
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (changesDone) {
            if (workoutViewModel.editingRoutine.value!!) routineViewModel.saveRoutine(routineViewModel.selectedRoutine.value!!)
            else {
                if (workoutViewModel.editingLive.value!!) workoutViewModel.saveLiveWorkout(workoutViewModel.liveWorkout.value!!)
                else workoutViewModel.saveWorkout(workoutViewModel.selectedWorkout.value!!)
            }
        }
    }

    private fun removeSingleSupersets(workout : Routine, exercises : MutableSet<Long> ) : MutableList<MutableSet<Long>> {
        val newSupersets : MutableList<MutableSet<Long>> = mutableListOf()
        for (set in workout.supersets) {
            set.removeAll(exercises)
            if (set.size > 1) newSupersets.add(set)
        }
        return newSupersets
    }

    private fun checkSameSuperset(exerciseId : Long, superset : MutableSet<Long>,
                                  supersets : MutableList<MutableSet<Long>>, skip : Boolean) : Boolean {
        for (set in supersets) {
            if ((set.contains(exerciseId) || skip) and set.containsAll(superset)) return true
        }
        return false
    }

    private fun selectSet(exercisePos : Int) {
        val supersets = if (workoutViewModel.editingRoutine.value!!) routineViewModel.selectedRoutine.value!!.supersets
        else  {
            if (workoutViewModel.editingLive.value!!) workoutViewModel.liveWorkout.value!!.supersets
            else workoutViewModel.selectedWorkout.value!!.supersets
        }

        val exerciseId = if (workoutViewModel.editingRoutine.value!!) routineViewModel.selectedRoutine.value!!.exercises[exercisePos].id
        else {
            if (workoutViewModel.editingLive.value!!) workoutViewModel.liveWorkout.value!!.exercises[exercisePos].id
            else workoutViewModel.selectedWorkout.value!!.exercises[exercisePos].id
        }

        if (superset.isEmpty() && checkSameSuperset(exerciseId, removedExercises, supersets, false)) {
            if (removedExercises.contains(exerciseId)) {
                adapter.selected = true
                adapter.added = false
                removedExercises.remove(exerciseId)
                if (removedExercises.size < 1 ) {
                    binding.imageButton3.setBackgroundResource(R.drawable.lock_disable)
                    binding.imageButton3.isEnabled = false
                }
            }
            else {
                adapter.selected = true
                adapter.added = true
                removedExercises.add(exerciseId)
                binding.imageButton3.setBackgroundResource(R.drawable.unlock)
                binding.imageButton3.isEnabled = true
            }
        }
        else {
            superset.addAll(removedExercises)
            removedExercises = mutableSetOf()

            if (superset.contains(exerciseId)) {
                adapter.selected = true
                adapter.added = false
                superset.remove(exerciseId)
                if (superset.isNotEmpty() && checkSameSuperset(superset.first(), superset, supersets, true)) {
                    removedExercises.addAll(superset)
                    superset = mutableSetOf()
                    binding.imageButton3.setBackgroundResource(R.drawable.unlock)
                    binding.imageButton3.isEnabled = true
                }
                else if (superset.size <= 1 ) {
                    binding.imageButton3.isEnabled = false
                    binding.imageButton3.setBackgroundResource(R.drawable.lock_disable)
                }
                else {
                    binding.imageButton3.isEnabled = true
                    binding.imageButton3.setBackgroundResource(R.drawable.lock)
                }
            }
            else {
                adapter.selected = true
                adapter.added = true
                superset.add(exerciseId)
                if (superset.size > 1 ) {
                    binding.imageButton3.isEnabled = true
                    binding.imageButton3.setBackgroundResource(R.drawable.lock)
                }
            }

        }
        adapter.notifyItemChanged(exercisePos)
    }

}