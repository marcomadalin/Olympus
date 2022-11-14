package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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

    private lateinit var exerciseAdapter : ExerciseDataAdapter
    private lateinit var filterAdapter: ExerciseFilterAdapter

    private lateinit var navController: NavController

    private var filterShown = false
    private var onEquipment = false

    private lateinit var startingParams : ViewGroup.LayoutParams

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

        binding.dropShadow6.visibility = View.GONE
        binding.exerciseRecyler.layoutManager = LinearLayoutManager(this.context)
        exerciseAdapter = ExerciseDataAdapter(mutableListOf(), {selectExercise(it)})
        binding.exerciseRecyler.adapter = exerciseAdapter
        exerciseDataViewModel.exercises.observe(viewLifecycleOwner) {updateExercises(it)}
        exerciseDataViewModel.getExercisesData()
        startingParams = binding.exerciseRecyler.layoutParams

        binding.filterRecycler.visibility = View.GONE
        binding.filterRecycler.layoutManager = GridLayoutManager(this.context, 3)
        filterAdapter = ExerciseFilterAdapter(emptyList(), {selectFilter(it)})
        binding.filterRecycler.adapter = filterAdapter
        exerciseDataViewModel.getFilters()

        binding.equipment.setOnClickListener{
            renderFilterRecycler(true)
            if (filterShown) {
                filterAdapter = ExerciseFilterAdapter(exerciseDataViewModel.equipmentFilters.value!!, {selectFilter(it)})
                binding.filterRecycler.adapter = filterAdapter
                filterAdapter.notifyDataSetChanged()
                onEquipment = true
            }
        }

        binding.muscle.setOnClickListener{
            renderFilterRecycler(false)
            if (filterShown) {
                filterAdapter = ExerciseFilterAdapter(exerciseDataViewModel.muscleFilters.value!!, {selectFilter(it)})
                binding.filterRecycler.adapter = filterAdapter
                filterAdapter.notifyDataSetChanged()
                onEquipment = false
            }
        }

        binding.search.clearFocus()
        binding.search.setOnClickListener { binding.search.isIconified = false }
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val filteredExercises: MutableList<ExerciseData> = mutableListOf()
                if (newText.isEmpty()) exerciseAdapter.exercises =
                    exerciseDataViewModel.exercises.value!!
                else {
                    for (exercise in exerciseDataViewModel.exercises.value!!) {
                        if (exercise.name.lowercase()
                                .contains(newText.lowercase())
                        ) filteredExercises.add(exercise)
                    }
                    exerciseAdapter.exercises = filteredExercises
                }
                updateFirstFavorite()
                exerciseAdapter.notifyDataSetChanged()
                return true
            }
        })
    }

    private fun renderFilterRecycler(equipmentClicked : Boolean) {
        if (filterShown && ((onEquipment && equipmentClicked) || (!onEquipment && !equipmentClicked)) ) {
            binding.filterRecycler.visibility = View.GONE
            binding.dropShadow6.visibility = View.GONE
            binding.exerciseRecyler.layoutParams = startingParams
            filterShown = false
        }
        else {
            binding.dropShadow6.visibility = View.VISIBLE
            binding.filterRecycler.visibility = View.VISIBLE
            val params = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.addRule(RelativeLayout.BELOW, binding.filterRecycler.id)
            params.setMargins(0,0,0,150)
            binding.exerciseRecyler.layoutParams = params
            filterShown = true
        }
    }

    private fun selectExercise(exerciseId : Long) {
        exerciseDataViewModel.selectedExercise.postValue(exerciseId)
    }

    private fun selectFilter(filter : String) {

    }

    private fun updateExercises(exercises: MutableList<ExerciseData>?) {
        if (exercises != null && first) {
            exerciseAdapter = ExerciseDataAdapter(exerciseDataViewModel.exercises.value!!, {selectExercise(it)})
            binding.exerciseRecyler.adapter = exerciseAdapter
            updateFirstFavorite()
            first = false
        }
    }

    private fun updateFirstFavorite()  {
        val index = exerciseAdapter.exercises.indexOfFirst { !it.favourite }
        if (index == 0 && !exerciseAdapter.exercises[0].favourite) exerciseAdapter.lastFavorite = -1
        else exerciseAdapter.lastFavorite = index
    }

}