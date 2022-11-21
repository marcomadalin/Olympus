package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentProfileBinding
import com.marcomadalin.olympus.domain.model.Statistic
import com.marcomadalin.olympus.presentation.viewmodel.StatisticViewModel
import com.marcomadalin.olympus.presentation.viewmodel.UserViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

//TODO add plots

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    private val statisticViewModel : StatisticViewModel by activityViewModels()

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

        binding.measureButton.setOnClickListener{
            navController.navigate(R.id.measureFragment)
            (activity as MainActivity).hideNavigationBar()
        }

        binding.username.text = userViewModel.user.value!!.name
        binding.numWorkouts.text = workoutViewModel.workouts.value!!.size.toString() + " workouts"

        binding.durationButtonStat.setOnClickListener{}
        binding.volumeButtonStat.setOnClickListener{}
        binding.repButtonStat.setOnClickListener{}

        statisticViewModel.statistic.observe(viewLifecycleOwner) {updateStats(it!!)}
    }

    private fun updateStats(statistic: Statistic) {
        var numWorkouts = workoutViewModel.workouts.value!!.size

        binding.totalVolumeStat.text = statistic.totalVolume.toString() + " Kg"
        binding.totalRepStat.text = statistic.totalReps.toString()
        binding.totalTimeStat.text = ((statistic.totalWorkoutLength.seconds % 3600)/60).toString() + " min"
        if (statistic.totalWorkoutLength.toHours().toInt() != 0) binding.totalTimeStat.text = statistic.totalWorkoutLength.toHours().toString() + " h " + binding.totalTimeStat.text

        binding.avgVolumeStat.text = (statistic.totalVolume / numWorkouts).toString() + "Kg"
        binding.avgRepStat.text = (statistic.totalReps / numWorkouts).toString()
        binding.avgTimeStat.text = (((statistic.totalWorkoutLength.seconds/numWorkouts) % 3600)/60).toString() + " min"
        if ((statistic.totalWorkoutLength.toHours().toInt() / numWorkouts) != 0) binding.avgTimeStat.text = (statistic.totalWorkoutLength.toHours() / numWorkouts).toString() + " h " + binding.avgTimeStat.text
    }
}