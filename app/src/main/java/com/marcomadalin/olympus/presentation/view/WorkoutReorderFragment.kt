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
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*




@AndroidEntryPoint
class WorkoutReorderFragment : Fragment() {

    private var _binding : FragmentWorkoutReorderBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private lateinit var adapter : ExerciseReorderAdapter

    private lateinit var navController: NavController

    private val simpleCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, 0) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            val startPosition = viewHolder.absoluteAdapterPosition
            val endPosition = target.absoluteAdapterPosition
            val workout = workoutViewModel.workout.value!!
            Collections.swap(workout.exercises, startPosition, endPosition)
            recyclerView.adapter?.notifyItemMoved(startPosition, endPosition)
            workoutViewModel.workout.postValue(workout)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWorkoutReorderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.reorderrecycler.layoutManager = LinearLayoutManager(this.context)
        adapter = ExerciseReorderAdapter(workoutViewModel.workout.value!!.exercises)
        binding.reorderrecycler.adapter = adapter
        ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.reorderrecycler)
        binding.reorderrecycler.addItemDecoration(DividerItemDecoration(binding.reorderrecycler.context, DividerItemDecoration.VERTICAL))
        binding.summaryTitle3.text = workoutViewModel.workout.value!!.name
        binding.backButtonSummary3.setOnClickListener{ navController.popBackStack() }
    }

}