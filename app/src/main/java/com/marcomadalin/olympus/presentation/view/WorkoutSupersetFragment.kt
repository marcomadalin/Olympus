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
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkoutSupersetFragment : Fragment() {

    private var _binding : FragmentWorkoutSupersetBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private lateinit var adapter : ExerciseSupersetAdapter

    private lateinit var navController: NavController

    private var superset : MutableSet<Long> = mutableSetOf()
    private var removedExercises : MutableSet<Long> = mutableSetOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWorkoutSupersetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageButton3.isEnabled = false
        navController = findNavController()
        binding.supersetRecycler.layoutManager = LinearLayoutManager(this.context)
        adapter = ExerciseSupersetAdapter(workoutViewModel.workout.value!!.exercises, {selectSet(it)})
        adapter.supersets = workoutViewModel.workout.value!!.supersets
        binding.supersetRecycler.adapter = adapter
        binding.supersetRecycler.addItemDecoration(DividerItemDecoration(binding.supersetRecycler.context, DividerItemDecoration.VERTICAL))
        binding.summaryTitle4.text = workoutViewModel.workout.value!!.name
        binding.backButtonSummary4.setOnClickListener{ navController.popBackStack() }
        binding.imageButton3.setOnClickListener{
            if (binding.imageButton3.isEnabled) {
                val workout = workoutViewModel.workout.value!!
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
                workoutViewModel.workout.postValue(workout)
                adapter.supersets = workoutViewModel.workout.value!!.supersets
                adapter.selected = false
                adapter.added = false
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun removeSingleSupersets(workout : Workout, exercises : MutableSet<Long> ) : MutableList<MutableSet<Long>> {
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
        val supersets = workoutViewModel.workout.value!!.supersets
        val exerciseId = workoutViewModel.workout.value!!.exercises[exercisePos].id

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