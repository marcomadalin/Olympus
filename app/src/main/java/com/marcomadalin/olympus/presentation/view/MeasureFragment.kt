package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.marcomadalin.olympus.databinding.FragmentMeasureBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeasureFragment : Fragment() {

    private var _binding: FragmentMeasureBinding? = null
    private val binding get() = _binding!!

    //private val measureViewModel: MeasureViewModel by activityViewModels()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMeasureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        navController = findNavController()

    }
}