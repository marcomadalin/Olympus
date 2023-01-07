package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentProfileBinding
import com.marcomadalin.olympus.domain.model.User
import com.marcomadalin.olympus.presentation.viewmodel.UserViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        navController = findNavController()

        binding.username.setText(userViewModel.selectedUser.value!!.name)
        binding.username.doOnTextChanged { _, _, _, _ ->
            val text = binding.username.text.toString()
            if (!text.isNullOrEmpty()) userViewModel.selectedUser.value!!.name = text
        }

        binding.numWorkouts.text = "Total workouts: " + workoutViewModel.workouts.value!!.size.toString()

        workoutViewModel.workouts.observe(viewLifecycleOwner) {
            binding.numWorkouts.text = "Total workouts: " + workoutViewModel.workouts.value!!.size.toString()
            updateWorkoutChart()
            updateStats(userViewModel.selectedUser.value!!)
        }
        userViewModel.selectedUser.observe(viewLifecycleOwner) {updateStats(it!!)}

        binding.volumeButtonStat.setOnClickListener {
            updateWorkoutChart()
        }

        binding.durationButtonStat.setOnClickListener {
            updateWorkoutChart(false)
        }

        workoutViewModel.getWorkouts()
        userViewModel.getUser()
    }

    override fun onStop() {
        super.onStop()
        userViewModel.saveUser(userViewModel.selectedUser.value!!)
    }

    private fun updateWorkoutChart(volumeSelected: Boolean = true) {
        if (workoutViewModel.workouts.value!!.isNullOrEmpty()) {
            binding.workoutVolumeChart.data = null
            binding.workoutVolumeChart.invalidate()
        }
        else {
            val values = workoutViewModel.workouts.value!!
            val entriesVolume : ArrayList<Entry> = arrayListOf()
            val entriesDuration : ArrayList<Entry> = arrayListOf()
            lateinit var dataEntries : LineDataSet

            if (volumeSelected) {
                for (workout in values) {
                    for (exercise in workout.exercises) {
                        for (set in exercise.sets) {
                            entriesVolume.add(
                                Entry(
                                    workout.date.dayOfYear.toFloat(),
                                    (set.reps * set.weight).toFloat()
                                )
                            )
                        }
                    }
                }
                dataEntries = LineDataSet(entriesVolume, "")
            }
            else {
                for (workout in values) {
                    entriesDuration.add(
                        Entry(
                            workout.date.dayOfYear.toFloat(),
                            workout.length.toMinutes().toFloat()
                        )
                    )
                }
                dataEntries = LineDataSet(entriesDuration, "")
            }

            dataEntries.axisDependency = YAxis.AxisDependency.LEFT;
            dataEntries.color = R.color.black
            dataEntries.circleColors = mutableListOf(R.color.buttons)
            dataEntries.lineWidth = 2f

            val dataSets : ArrayList<ILineDataSet> = ArrayList()
            dataSets.add(dataEntries)

            val plotData = LineData(dataSets)
            plotData.setValueTextSize(12f)
            binding.workoutVolumeChart.data = plotData

            binding.workoutVolumeChart.description.isEnabled = false
            binding.workoutVolumeChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            binding.workoutVolumeChart.setNoDataText("No values registered")
            binding.workoutVolumeChart.axisRight.isEnabled = false
            binding.workoutVolumeChart.axisLeft.textSize = 12.0f
            binding.workoutVolumeChart.axisRight.setDrawGridLines(false)
            binding.workoutVolumeChart.axisLeft.setDrawGridLines(false)
            binding.workoutVolumeChart.isAutoScaleMinMaxEnabled = true
            binding.workoutVolumeChart.xAxis.textSize = 12.0f
            binding.workoutVolumeChart.legend.isEnabled = false
            binding.workoutVolumeChart.xAxis.setDrawGridLines(false)
            binding.workoutVolumeChart.invalidate()
        }
    }

    private fun updateStats(user : User) {
        val numWorkouts = 55

        val entries: MutableList<PieEntry> = ArrayList()
        val values = user.muscleDivision.toList()
        for (value in values) {
            if (value.second > 0) entries.add(PieEntry(value.second.toFloat(), value.first.toString().replace("_", " ")))
        }

        val set = PieDataSet(entries, "Trained muscle division")
        set.valueTextSize = 12f
        set.valueFormatter = PercentFormatter()
        set.setColors(
            intArrayOf(R.color.color1, R.color.color2,  R.color.color3, R.color.color4, R.color.color5, R.color.color6, R.color.color7,
                R.color.color8, R.color.color9, R.color.color10, R.color.color11, R.color.color12, R.color.color13, R.color.color14, R.color.color15,
                R.color.color16, R.color.color17, R.color.color18, R.color.color19, R.color.color20), this.context
        )

        val data = PieData(set)
        binding.statChart.data = data
        binding.statChart.legend.isEnabled = false
        binding.statChart.setUsePercentValues(true)
        binding.statChart.setNoDataText("No values registered")
        binding.statChart.description.isEnabled = false
        binding.statChart.legend.textSize = 12f
        binding.statChart.invalidate()

        binding.totalVolumeStat6.text = user.totalVolume.toString() + " Kg"
        binding.totalRepStat4.text = user.totalReps.toString()
        binding.totalWorkoutTimeSt.text = ((user.totalWorkoutLength.seconds % 3600)/60).toString() + " min"
        if (user.totalWorkoutLength.toHours().toInt() != 0) binding.totalWorkoutTimeSt.text = user.totalWorkoutLength.toHours().toString() + " h " + binding.totalWorkoutTimeSt.text

        binding.avgVolSt.text = String.format("%.2f", (user.totalVolume / numWorkouts)) + "Kg"
        binding.avgRepsSt.text = (user.totalReps / numWorkouts).toString()
        binding.avgDurSt.text = (((user.totalWorkoutLength.seconds/numWorkouts) % 3600)/60).toString() + " min"
        if ((user.totalWorkoutLength.toHours().toInt() / numWorkouts) != 0) binding.avgDurSt.text = (user.totalWorkoutLength.toHours() / numWorkouts).toString() + " h " + binding.avgDurSt.text
    }
}