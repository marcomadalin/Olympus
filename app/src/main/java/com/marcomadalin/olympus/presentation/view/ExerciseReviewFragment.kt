package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentExerciseReviewBinding
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class ExerciseReviewFragment : Fragment() {

    private var _binding : FragmentExerciseReviewBinding? = null
    private val binding get() = _binding!!

    private val exerciseDataViewModel : ExerciseViewModel by activityViewModels()

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        navController = findNavController()

        binding.backButtonReview.setOnClickListener {
            (activity as MainActivity).showNavigationBar()
            navController.popBackStack()
        }

        updateFavorite()
        binding.favButtonReview.setOnClickListener {
            exerciseDataViewModel.selectedExercise.value!!.favourite =
                !exerciseDataViewModel.selectedExercise.value!!.favourite
            updateFavorite()
        }

        binding.exerciseReviewDropdown.setOnClickListener { view ->
            val popupMenu = PopupMenu(this.context, view)

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.edit -> {
                        navController.navigate(R.id.action_exerciseReviewFragment_to_exerciseEditFragment)
                        true
                    }
                    R.id.delete -> {
                        navController.popBackStack()
                        exerciseDataViewModel.deleteExerciseData()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.inflate(R.menu.exercise_review_dropdown)
            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e: Exception) {
                Log.e("Main", "Error showing menu icons.", e)
            } finally {
                popupMenu.show()
            }
        }

        binding.summaryTitle6.text = exerciseDataViewModel.selectedExercise.value!!.name

        binding.equipmentReview.text =
            exerciseDataViewModel.selectedExercise.value!!.equipment.toString().replace("_", " ")
        binding.muscleReview.text =
            exerciseDataViewModel.selectedExercise.value!!.primaryMuscle.toString()
                .replace("_", " ")

        binding.weighReview.text =
            exerciseDataViewModel.selectedExercise.value!!.maxWeight.toString() + " Kg"
        binding.ormReview.text =
            exerciseDataViewModel.selectedExercise.value!!.orm.toString() + " Kg"
        binding.volumeSetReview.text =
            exerciseDataViewModel.selectedExercise.value!!.bestSetWeight.toString() + " Kg x " + exerciseDataViewModel.selectedExercise.value!!.bestSetReps
        binding.volumeWeightReview.text =
            (exerciseDataViewModel.selectedExercise.value!!.bestSetWeight * exerciseDataViewModel.selectedExercise.value!!.bestSetReps).toString() + " Kg"

        binding.volumeButtonStat2.setOnClickListener { updateChart() }
        binding.prButtonStat.setOnClickListener { updateChart(false) }

        workoutViewModel.workouts.observe(viewLifecycleOwner) {
            updateChart()
        }

        workoutViewModel.getWorkouts()
    }


    private fun updateFavorite() {
        if (exerciseDataViewModel.selectedExercise.value!!.favourite) {
            binding.favButtonReview.background = ContextCompat.getDrawable(this.requireContext(), R.drawable.star_gold)
        }
        else binding.favButtonReview.background = ContextCompat.getDrawable(this.requireContext(), R.drawable.star)
    }

    private fun updateChart(volumeSelected: Boolean = true) {
        if (workoutViewModel.workouts.value!!.isNullOrEmpty()) {
            binding.chartExercise.data = null
            binding.chartExercise.invalidate()
        }
        else {
            val selectedExerciseId = exerciseDataViewModel.selectedExercise.value!!.id
            val values = workoutViewModel.workouts.value!!.filter { workout ->
                workout.exercises.any { exercise ->
                    exercise.exerciseDataId == selectedExerciseId
                }
            }
            val entriesVolume : ArrayList<Entry> = arrayListOf()
            val entriesPr: ArrayList<Entry> = arrayListOf()
            val entriesWeight: ArrayList<Entry> = arrayListOf()
            lateinit var dataEntries : LineDataSet
            lateinit var dataEntries2 : LineDataSet

            for (workout in values) {
                for (exercise in workout.exercises) {
                    if (exercise.exerciseDataId == selectedExerciseId) {
                        for (set in exercise.sets) {
                            if (volumeSelected) {
                                entriesVolume.add(
                                    Entry(
                                        workout.date.dayOfYear.toFloat(),
                                        (set.reps * set.weight).toFloat()
                                    )
                                )
                            } else {
                                entriesWeight.add(
                                    Entry(
                                        workout.date.dayOfYear.toFloat(),
                                        (set.weight).toFloat()
                                    )
                                )
                                entriesPr.add(
                                    Entry(
                                        workout.date.dayOfYear.toFloat(),
                                        ((((set.weight / (1.0278 - 0.0278 * set.reps)) * 100.0).roundToInt() / 100.0)).toFloat()
                                    )
                                )
                            }
                        }
                    }
                }
            }

            if (volumeSelected) dataEntries = LineDataSet(entriesVolume, "")
            else {
                dataEntries = LineDataSet(entriesWeight, "Weight")
                dataEntries2 = LineDataSet(entriesPr, "RM")
            }

            dataEntries.axisDependency = YAxis.AxisDependency.LEFT;
            dataEntries.color = R.color.black
            dataEntries.circleColors = mutableListOf(R.color.buttons)
            dataEntries.lineWidth = 2f

            val dataSets : ArrayList<ILineDataSet> = ArrayList()

            dataSets.add(dataEntries)
            if (!volumeSelected) {
                dataEntries2.axisDependency = YAxis.AxisDependency.LEFT;
                dataEntries2.color = R.color.purple_700
                dataEntries2.circleColors = mutableListOf(R.color.buttons)
                dataEntries2.lineWidth = 2f
                dataSets.add(dataEntries2)
            }

            val plotData = LineData(dataSets)
            plotData.setValueTextSize(12f)
            binding.chartExercise.data = plotData

            binding.chartExercise.description.isEnabled = false
            binding.chartExercise.xAxis.position = XAxis.XAxisPosition.BOTTOM
            binding.chartExercise.setNoDataText("No values registered")
            binding.chartExercise.axisRight.isEnabled = false
            binding.chartExercise.axisLeft.textSize = 12.0f
            binding.chartExercise.axisRight.setDrawGridLines(false)
            binding.chartExercise.axisLeft.setDrawGridLines(false)
            binding.chartExercise.isAutoScaleMinMaxEnabled = true
            binding.chartExercise.xAxis.textSize = 12.0f
            binding.chartExercise.legend.isEnabled = false
            binding.chartExercise.xAxis.setDrawGridLines(false)
            binding.chartExercise.invalidate()
        }
    }
}