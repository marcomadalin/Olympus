package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentRoutineBinding
import com.marcomadalin.olympus.domain.model.Routine
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.presentation.view.recyclers.RoutineAdapter
import com.marcomadalin.olympus.presentation.viewmodel.RoutineViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutineFragment : Fragment() {

    private var _binding: FragmentRoutineBinding? = null
    private val binding get() = _binding!!

    private val routineViewModel: RoutineViewModel by activityViewModels()
    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    private lateinit var adapter : RoutineAdapter

    private lateinit var navController: NavController

    private var searchText = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        searchText = routineViewModel.searchText.value!!
        navController = findNavController()

        (activity as MainActivity).showNavigationBar()

        binding.createRoutine.setOnClickListener {  }
        binding.startEmpty.setOnClickListener {  }

        binding.routineRecycler.layoutManager = LinearLayoutManager(this.context)
        adapter = RoutineAdapter(::onRoutineClick)
        binding.routineRecycler.adapter = adapter
        binding.noExercise2.visibility = View.VISIBLE

        binding.startEmpty.setOnClickListener {
            workoutViewModel.liveWorkout.value = Workout(isLive = true)
            workoutViewModel.saveLiveWorkout(workoutViewModel.liveWorkout.value!!)
            navController.navigate(R.id.liveWorkoutFragment)
        }

        routineViewModel.routines.observe(viewLifecycleOwner) {
            adapter.routines = it!!
            filterRoutines()
        }

        binding.createRoutine.setOnClickListener{
            routineViewModel.selectedRoutine.value = Routine()
            routineViewModel.selectedRoutine.value!!.name = "New routine"
            routineViewModel.newRoutine.value = true
            navController.navigate(R.id.routineEditFragment)
        }

        binding.searchRoutine.clearFocus()
        binding.searchRoutine.setOnClickListener { binding.searchRoutine.isIconified = false }
        binding.searchRoutine.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchText = newText
                filterRoutines()
                return true
            }
        })
    }

    override fun onStop() {
        super.onStop()
        routineViewModel.searchText.value = searchText
    }

    private fun onRoutineClick(routinePosition: Int) {
        routineViewModel.selectedRoutine.value = routineViewModel.routines.value!![routinePosition]
        routineViewModel.newRoutine.value = false
        navController.navigate(R.id.action_workout_to_routineReviewFragment)
        (activity as MainActivity).hideNavigationBar()
    }

    private fun filterRoutines() {
        val filteredRoutines: MutableList<Routine> = mutableListOf()
        if (searchText.isEmpty()) adapter.routines = routineViewModel.routines.value!!
        else  {
            for (routine in routineViewModel.routines.value!!) {
                if (routine.name.lowercase().contains(searchText.lowercase())) filteredRoutines.add(routine)
            }
            adapter.routines = filteredRoutines
        }
        adapter.notifyDataSetChanged()

        if (adapter.routines.isEmpty()) binding.noExercise2.visibility = View.VISIBLE
        else binding.noExercise2.visibility = View.GONE
    }
}