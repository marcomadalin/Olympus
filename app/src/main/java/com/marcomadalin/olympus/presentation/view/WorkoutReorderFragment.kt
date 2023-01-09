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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcomadalin.olympus.databinding.FragmentWorkoutReorderBinding
import com.marcomadalin.olympus.presentation.view.recyclers.ExerciseReorderAdapter
import com.marcomadalin.olympus.presentation.viewmodel.RoutineViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WorkoutReorderFragment : Fragment() {

    private var _binding : FragmentWorkoutReorderBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private val routineViewModel : RoutineViewModel by activityViewModels()

    private lateinit var adapter : ExerciseReorderAdapter

    private lateinit var navController: NavController

    private var changesDone = false

    private val simpleCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, 0) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            changesDone = true
            val startPosition = viewHolder.absoluteAdapterPosition
            val endPosition = target.absoluteAdapterPosition

            val workout = if (workoutViewModel.editingRoutine.value!!) routineViewModel.selectedRoutine.value!!
            else {
                if (workoutViewModel.editingLive.value!!) workoutViewModel.liveWorkout.value!!
                else workoutViewModel.selectedWorkout.value!!
            }

            workout.exercises[startPosition].exerciseNumber = endPosition
            workout.exercises[endPosition].exerciseNumber = startPosition
            Collections.swap(workout.exercises, startPosition, endPosition)
            recyclerView.adapter?.notifyItemMoved(startPosition, endPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWorkoutReorderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        changesDone = false
        navController = findNavController()
        binding.reorderrecycler.layoutManager = LinearLayoutManager(this.context)

        if (workoutViewModel.editingRoutine.value!!) {
            adapter = ExerciseReorderAdapter(routineViewModel.selectedRoutine.value!!.exercises)
            binding.summaryTitle3.text = routineViewModel.selectedRoutine.value!!.name
        }
        else {
            if (workoutViewModel.editingLive.value!!) {
                adapter = ExerciseReorderAdapter(workoutViewModel.liveWorkout.value!!.exercises)
                binding.summaryTitle3.text = workoutViewModel.liveWorkout.value!!.name
            }
            else {
                adapter = ExerciseReorderAdapter(workoutViewModel.selectedWorkout.value!!.exercises)
                binding.summaryTitle3.text = workoutViewModel.selectedWorkout.value!!.name
            }
        }

        binding.reorderrecycler.adapter = adapter
        ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.reorderrecycler)
        binding.reorderrecycler.addItemDecoration(DividerItemDecoration(binding.reorderrecycler.context, DividerItemDecoration.VERTICAL))
        binding.backButtonSummary3.setOnClickListener{ navController.popBackStack() }
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

}