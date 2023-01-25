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
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentExerciseBinding
import com.marcomadalin.olympus.domain.model.ExerciseData
import com.marcomadalin.olympus.presentation.view.recyclers.ExerciseDataAdapter
import com.marcomadalin.olympus.presentation.view.recyclers.ExerciseFilterAdapter
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExerciseFragment : Fragment() {

    private var _binding : FragmentExerciseBinding? = null
    private val binding get() = _binding!!

    private val exerciseDataViewModel : ExerciseViewModel by activityViewModels()

    private lateinit var exerciseAdapter : ExerciseDataAdapter
    private lateinit var filterAdapter: ExerciseFilterAdapter

    private lateinit var navController: NavController

    private var filterShown = false
    private var onEquipment = false

    private lateinit var startingParams : ViewGroup.LayoutParams

    private var searchText = ""
    private var textFilteredList : List<ExerciseData> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        searchText = exerciseDataViewModel.searchFilter.value!!
        (activity as MainActivity).showNavigationBar()
        navController = findNavController()

        binding.create.setOnClickListener {
            navController.navigate(R.id.createExericeFragment)
            (activity as MainActivity).hideNavigationBar()
        }

        binding.filterRecycler.visibility = View.GONE
        binding.filterRecycler.layoutManager = GridLayoutManager(this.context, 3)
        filterAdapter = ExerciseFilterAdapter(emptyList(), {selectFilter(it)})
        filterAdapter.selectedFilters = exerciseDataViewModel.selectedFilters.value!!.toMutableSet()
        binding.filterRecycler.adapter = filterAdapter
        exerciseDataViewModel.getFilters()

        binding.equipment.setOnClickListener{
            renderFilterRecycler(true)
            if (filterShown) {
                filterAdapter = ExerciseFilterAdapter(exerciseDataViewModel.equipmentFilters.value!!, {selectFilter(it)})
                updateFilterRecycler()
                onEquipment = true
            }
        }

        binding.muscle.setOnClickListener{
            renderFilterRecycler(false)
            if (filterShown) {
                filterAdapter = ExerciseFilterAdapter(exerciseDataViewModel.muscleFilters.value!!, {selectFilter(it)})
                updateFilterRecycler()
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
                searchText = newText
                filterListWithText()
                filterListWithButtons()
                updateFirstFavorite()
                exerciseAdapter.notifyDataSetChanged()
                updateTextView()
                return true
            }
        })

        binding.dropShadow6.visibility = View.GONE
        binding.exerciseRecyler.layoutManager = LinearLayoutManager(this.context)
        exerciseAdapter = ExerciseDataAdapter(mutableListOf(), {selectExercise(it)})
        binding.exerciseRecyler.adapter = exerciseAdapter
        exerciseDataViewModel.exercises.observe(viewLifecycleOwner) {updateExercises(it)}
        exerciseDataViewModel.getExercisesData()
        startingParams = binding.exerciseRecyler.layoutParams
    }

    override fun onDestroy() {
        super.onDestroy()
        exerciseDataViewModel.searchFilter.postValue(searchText)
    }

    private fun updateFilterRecycler() {
        filterAdapter.selectedFilters = exerciseDataViewModel.selectedFilters.value!!.toMutableSet()
        binding.filterRecycler.adapter = filterAdapter
        filterAdapter.notifyDataSetChanged()
    }

    private fun filterListWithText() {
        val filteredExercises: MutableList<ExerciseData> = mutableListOf()
        if (searchText.isEmpty()) textFilteredList = exerciseDataViewModel.exercises.value!!
        else  {
            for (exercise in exerciseDataViewModel.exercises.value!!) {
                if (exercise.name.lowercase().contains(searchText.lowercase())) filteredExercises.add(exercise)
            }
            textFilteredList = filteredExercises
        }
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
            binding.exerciseRecyler.layoutParams = params
            filterShown = true
        }
    }

    private fun selectExercise(exercise : ExerciseData) {
        exerciseDataViewModel.selectedExercise.postValue(exercise)
        navController.navigate(R.id.exerciseReviewFragment)
        (activity as MainActivity).hideNavigationBar()
    }

    private fun selectFilter(data : Pair<String,Int>) {
        if (filterAdapter.selectedFilters.contains(data.first)) filterAdapter.selectedFilters.remove(data.first)
        else filterAdapter.selectedFilters.add(data.first)
        exerciseDataViewModel.selectedFilters.postValue(filterAdapter.selectedFilters)
        filterListWithText()
        filterListWithButtons()
        updateFirstFavorite()
        filterAdapter.notifyItemChanged(data.second)
        exerciseAdapter.notifyDataSetChanged()
        updateTextView()
    }

    private fun filterListWithButtons() {
        val filteredExercises: MutableList<ExerciseData> = mutableListOf()
        if (filterAdapter.selectedFilters.isEmpty()) exerciseAdapter.exercises = textFilteredList.toMutableList()
        else {
            val allEquipment = filterAdapter.selectedFilters.none{it in exerciseDataViewModel.equipmentFilters.value!!}
            var allMuscles = false

            if (!allEquipment) allMuscles = filterAdapter.selectedFilters.none{it in exerciseDataViewModel.muscleFilters.value!!}

            for (exercise in textFilteredList) {
                val equipment = exercise.equipment.toString().replace("_", " ")
                val muscle = exercise.primaryMuscle.toString().replace("_", " ")
                if ((allEquipment || equipment in filterAdapter.selectedFilters) && (allMuscles || muscle in filterAdapter.selectedFilters)) filteredExercises.add(exercise)
            }
            exerciseAdapter.exercises = filteredExercises
        }
    }

    private fun updateExercises(exercises: MutableList<ExerciseData>?) {
        if (exercises != null) {
            exerciseAdapter = ExerciseDataAdapter(exerciseDataViewModel.exercises.value!!, {selectExercise(it)})
            binding.exerciseRecyler.adapter = exerciseAdapter
            filterListWithText()
            filterListWithButtons()
            updateFirstFavorite()
            exerciseAdapter.notifyDataSetChanged()
            updateTextView()
        }
    }

    private fun updateFirstFavorite()  {
        val index = exerciseAdapter.exercises.indexOfFirst { !it.favourite }
        if (index == 0 && !exerciseAdapter.exercises[0].favourite) exerciseAdapter.lastFavorite = -1
        else exerciseAdapter.lastFavorite = index
    }

    private fun updateTextView() {
        if (exerciseAdapter.exercises.isEmpty()) binding.noExercise.visibility = View.VISIBLE
        else binding.noExercise.visibility = View.GONE
    }

}