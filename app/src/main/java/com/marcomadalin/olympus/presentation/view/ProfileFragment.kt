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
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentProfileBinding
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

        binding.statButton.setOnClickListener {
            navController.navigate(R.id.action_profile_to_statsFragment)
            (activity as MainActivity).hideNavigationBar()
        }

        binding.measuresButton.setOnClickListener {
            navController.navigate(R.id.action_profile_to_measureFragment)
            (activity as MainActivity).hideNavigationBar()
        }

        binding.username.setText(userViewModel.selectedUser.value!!.name)
        binding.username.doOnTextChanged { _, _, _, _ ->
            val text = binding.username.text.toString()
            if (!text.isNullOrEmpty()) userViewModel.selectedUser.value!!.name = text
        }

        binding.numWorkouts.text = "Workouts: " + workoutViewModel.workouts.value!!.size.toString()
        workoutViewModel.workouts.observe(viewLifecycleOwner) {
            binding.numWorkouts.text = "Workouts: " + workoutViewModel.workouts.value!!.size.toString()
        }

        binding.durationButtonStat.setOnClickListener {}
        binding.volumeButtonStat.setOnClickListener {}
        binding.repButtonStat.setOnClickListener {}

    }

    override fun onStop() {
        super.onStop()
        userViewModel.saveUser(userViewModel.selectedUser.value!!)
    }
}