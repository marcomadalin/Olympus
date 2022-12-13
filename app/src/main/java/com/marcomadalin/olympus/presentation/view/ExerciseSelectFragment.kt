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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentSelectExerciseBinding
import com.marcomadalin.olympus.domain.model.ExerciseData
import com.marcomadalin.olympus.presentation.view.recyclers.ExerciseDataSelectAdapter
import com.marcomadalin.olympus.presentation.view.recyclers.ExerciseFilterAdapter
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExerciseSelectFragment : Fragment() {

    private var _binding : FragmentSelectExerciseBinding? = null
    private val binding get() = _binding!!

    private val exerciseViewModel : ExerciseViewModel by activityViewModels()

    private lateinit var exerciseAdapter : ExerciseDataSelectAdapter
    private lateinit var filterAdapter: ExerciseFilterAdapter

    private lateinit var navController: NavController

    private var filterShown = false
    private var onEquipment = false

    private lateinit var startingParams : ViewGroup.LayoutParams

    private var searchText = ""
    private var textFilteredList : List<ExerciseData> = listOf()

    private var first = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        first = true
        filterShown = false
        onEquipment = false
        searchText = ""

        (activity as MainActivity).hideNavigationBar()
        navController = findNavController()

        binding.backButtonSummary6.setOnClickListener {
            exerciseViewModel.selectedExercises.value = mutableMapOf()
            navController.popBackStack()
        }

        binding.complete.setBackgroundResource(R.drawable.tick_grey)
        binding.complete.isEnabled = false
        binding.complete.setOnClickListener{navController.popBackStack()}

        binding.create2.setOnClickListener { navController.navigate(R.id.createExericeFragment)}

        exerciseViewModel.selectedFilters2.postValue(emptySet())
        binding.filterRecycler2.visibility = View.GONE
        binding.filterRecycler2.layoutManager = GridLayoutManager(this.context, 3)
        filterAdapter = ExerciseFilterAdapter(emptyList(), {selectFilter(it)})
        filterAdapter.selectedFilters = exerciseViewModel.selectedFilters2.value!!.toMutableSet()
        binding.filterRecycler2.adapter = filterAdapter
        binding.exerciseRecyler2.addItemDecoration(DividerItemDecoration(binding.exerciseRecyler2.context, DividerItemDecoration.VERTICAL))

        exerciseViewModel.getFilters()

        binding.equipment2.setOnClickListener{
            renderFilterRecycler(true)
            if (filterShown) {
                filterAdapter = ExerciseFilterAdapter(exerciseViewModel.equipmentFilters.value!!, {selectFilter(it)})
                updateFilterRecycler()
                onEquipment = true
            }
        }

        binding.muscle2.setOnClickListener{
            renderFilterRecycler(false)
            if (filterShown) {
                filterAdapter = ExerciseFilterAdapter(exerciseViewModel.muscleFilters.value!!, {selectFilter(it)})
                updateFilterRecycler()
                onEquipment = false
            }
        }

        binding.search2.clearFocus()
        binding.search2.setOnClickListener { binding.search2.isIconified = false }
        binding.search2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        binding.exerciseRecyler2.layoutManager = LinearLayoutManager(this.context)
        exerciseAdapter = ExerciseDataSelectAdapter(mutableListOf(), {selectExercise(it)})
        binding.exerciseRecyler2.adapter = exerciseAdapter
        exerciseViewModel.exercises.observe(viewLifecycleOwner) {updateExercises(it)}
        exerciseViewModel.getExercisesData()
        startingParams = binding.exerciseRecyler2.layoutParams
    }

    private fun updateFilterRecycler() {
        filterAdapter.selectedFilters = exerciseViewModel.selectedFilters2.value!!.toMutableSet()
        binding.filterRecycler2.adapter = filterAdapter
        filterAdapter.notifyDataSetChanged()
    }

    private fun filterListWithText() {
        val filteredExercises: MutableList<ExerciseData> = mutableListOf()
        if (searchText.isEmpty()) textFilteredList = exerciseViewModel.exercises.value!!
        else  {
            for (exercise in exerciseViewModel.exercises.value!!) {
                if (exercise.name.lowercase().contains(searchText.lowercase())) filteredExercises.add(exercise)
            }
            textFilteredList = filteredExercises
        }
    }

    private fun renderFilterRecycler(equipmentClicked : Boolean) {
        if (filterShown && ((onEquipment && equipmentClicked) || (!onEquipment && !equipmentClicked)) ) {
            binding.filterRecycler2.visibility = View.GONE
            binding.dropShadow6.visibility = View.GONE
            binding.exerciseRecyler2.layoutParams = startingParams
            filterShown = false
        }
        else {
            binding.dropShadow6.visibility = View.VISIBLE
            binding.filterRecycler2.visibility = View.VISIBLE
            val params = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.addRule(RelativeLayout.BELOW, binding.filterRecycler2.id)
            binding.exerciseRecyler2.layoutParams = params
            filterShown = true
        }
    }

    private fun selectExercise(exercise : ExerciseData) {
        if (exerciseViewModel.selectOne.value!!) {
            if (exerciseViewModel.selectedExercises.value!!.contains(exercise.id)) {
                exerciseViewModel.selectedExercises.postValue(mutableMapOf())
                exerciseAdapter.selectedExercises = mutableSetOf()
            }
            else {
                exerciseViewModel.selectedExercises.postValue(mutableMapOf(Pair(exercise.id, exercise.name)))
                exerciseAdapter.selectedExercises = mutableSetOf(exercise.id)
            }
        }
        else {
            if (!exerciseViewModel.selectedExercises.value!!.contains(exercise.id)) {
                exerciseViewModel.selectedExercises.value!!.put(exercise.id, exercise.name)
                exerciseAdapter.selectedExercises.add(exercise.id)
            } else {
                exerciseViewModel.selectedExercises.value!!.remove(exercise.id)
                exerciseAdapter.selectedExercises.remove(exercise.id)
            }
        }
        exerciseAdapter.notifyDataSetChanged()

        if (exerciseAdapter.selectedExercises.isNotEmpty()) {
            binding.complete.setBackgroundResource(R.drawable.tick)
            binding.complete.isEnabled = true
        }
        else {
            binding.complete.setBackgroundResource(R.drawable.tick_grey)
            binding.complete.isEnabled = false
        }
    }

    private fun selectFilter(data : Pair<String,Int>) {
        if (filterAdapter.selectedFilters.contains(data.first)) filterAdapter.selectedFilters.remove(data.first)
        else filterAdapter.selectedFilters.add(data.first)
        exerciseViewModel.selectedFilters2.postValue(filterAdapter.selectedFilters)
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
            val allEquipment = filterAdapter.selectedFilters.none{it in exerciseViewModel.equipmentFilters.value!!}
            var allMuscles = false

            if (!allEquipment) allMuscles = filterAdapter.selectedFilters.none{it in exerciseViewModel.muscleFilters.value!!}

            for (exercise in textFilteredList) {
                val equipment = exercise.equipment.toString().replace("_", " ")
                val muscle = exercise.primaryMuscle.toString().replace("_", " ")
                if ((allEquipment || equipment in filterAdapter.selectedFilters) && (allMuscles || muscle in filterAdapter.selectedFilters)) filteredExercises.add(exercise)
            }
            exerciseAdapter.exercises = filteredExercises
        }
    }

    private fun updateExercises(exercises: MutableList<ExerciseData>?) {
        if (exercises != null && first) {
            exerciseAdapter = ExerciseDataSelectAdapter(exerciseViewModel.exercises.value!!, {selectExercise(it)})
            binding.exerciseRecyler2.adapter = exerciseAdapter
            filterListWithText()
            filterListWithButtons()
            updateFirstFavorite()
            exerciseAdapter.notifyDataSetChanged()
            updateTextView()
            first = false
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