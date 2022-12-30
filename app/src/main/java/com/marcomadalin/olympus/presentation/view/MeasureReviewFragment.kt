package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.marcomadalin.olympus.databinding.FragmentMeasureReviewBinding
import com.marcomadalin.olympus.presentation.view.recyclers.MeasureSelectAdapter
import com.marcomadalin.olympus.presentation.viewmodel.MeasuresViewModel

class MeasureReviewFragment : Fragment() {

    private var _binding: FragmentMeasureReviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private val measureViewModel : MeasuresViewModel by viewModels()

    private lateinit var adapter : MeasureSelectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMeasureReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }
}