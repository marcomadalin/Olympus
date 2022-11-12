package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.marcomadalin.olympus.databinding.FragmentProfileBinding
import com.marcomadalin.olympus.presentation.viewmodel.UserViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.String
import java.util.*

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()

    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener { userViewModel.getUser() }
        binding.button.setOnClickListener {
            userViewModel.deleteUsers()
            workoutViewModel.deleteWorkouts()
        }
        userViewModel.userModel.observe(viewLifecycleOwner) {
            binding.profileText.text = String.format(Locale.ENGLISH,"%d: " + it.name, it.id)
        }

    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).showNavigationBar()
    }
}