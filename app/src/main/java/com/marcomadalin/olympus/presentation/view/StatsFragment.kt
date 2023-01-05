package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentStatsBinding
import com.marcomadalin.olympus.domain.model.User
import com.marcomadalin.olympus.presentation.viewmodel.UserViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

//TODO add plots

@AndroidEntryPoint
class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        navController = findNavController()

        binding.backButtonStat.setOnClickListener {
            navController.navigate(R.id.action_statsFragment_to_profile)
            (activity as MainActivity).showNavigationBar()
        }

        userViewModel.selectedUser.observe(viewLifecycleOwner) {updateStats(it!!)}
        workoutViewModel.workouts.observe(viewLifecycleOwner) {updateStats(userViewModel.selectedUser.value!!)}
        userViewModel.getUser()
    }

    private fun updateStats(user : User) {
        val numWorkouts = 55

        val pie = AnyChart.pie()
        pie.background().stroke("3 #BBA14F")

        val data: MutableList<DataEntry> = ArrayList()
        val values = user.muscleDivision.toList()

        for (value in values) data.add(ValueDataEntry(value.first.toString().replace("_", " "), value.second))

        pie.data(data)
        binding.statChart.setChart(pie)

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