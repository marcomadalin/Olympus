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
import com.marcomadalin.olympus.databinding.FragmentExerciseBinding
import com.marcomadalin.olympus.domain.model.ExerciseData
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseFragment : Fragment() {

    private var _binding : FragmentExerciseBinding? = null
    private val binding get() = _binding!!

    private val exerciseDataViewModel : ExerciseDataViewModel by activityViewModels()

    private lateinit var adapter : ExerciseDataAdapter

    private lateinit var navController: NavController

    private var first = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).showNavigationBar()
        navController = findNavController()
        binding.exerciseRecyler.layoutManager = LinearLayoutManager(this.context)
        adapter = ExerciseDataAdapter(mutableListOf(), {selectExercise(it)})
        binding.exerciseRecyler.adapter = adapter
        exerciseDataViewModel.exercises.observe(viewLifecycleOwner) {updateExercises(it)}
        exerciseDataViewModel.getExercisesData()
        binding.search.clearFocus()
        binding.search.setOnClickListener { binding.search.isIconified = false }
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val filteredExercises: MutableList<ExerciseData> = mutableListOf()
                if (newText.isEmpty()) adapter.exercises =
                    exerciseDataViewModel.exercises.value!!
                else {
                    for (exercise in exerciseDataViewModel.exercises.value!!) {
                        if (exercise.name.lowercase()
                                .contains(newText.lowercase())
                        ) filteredExercises.add(exercise)
                    }
                    adapter.exercises = filteredExercises
                }
                updateFirstFavorite()
                adapter.notifyDataSetChanged()
                return true
            }
        })
    }

    private fun selectExercise(exerciseId : Long) {
        exerciseDataViewModel.selectedExercise.postValue(exerciseId)
    }

    private fun updateExercises(exercises: MutableList<ExerciseData>?) {
        if (exercises != null && first) {
            adapter = ExerciseDataAdapter(exerciseDataViewModel.exercises.value!!, {selectExercise(it)})
            binding.exerciseRecyler.adapter = adapter
            updateFirstFavorite()
            first = false
        }
    }

    private fun updateFirstFavorite()  {
        val index = adapter.exercises.indexOfFirst { !it.favourite }
        if (index == 0 && !adapter.exercises[0].favourite) adapter.lastFavorite = -1
        else adapter.lastFavorite = index
    }

}