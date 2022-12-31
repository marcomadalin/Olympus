package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentMeasureBinding
import com.marcomadalin.olympus.presentation.view.recyclers.MeasureSelectAdapter
import com.marcomadalin.olympus.presentation.viewmodel.MeasuresViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeasureFragment : Fragment() {

    private var _binding: FragmentMeasureBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private val measureViewModel : MeasuresViewModel by activityViewModels()

    private lateinit var adapter : MeasureSelectAdapter

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

        binding.backButtonMeasure.setOnClickListener {
            navController.navigate(R.id.action_measureFragment_to_profile)
            (activity as MainActivity).showNavigationBar()
        }

        binding.recylerMeasure.layoutManager = GridLayoutManager(this.context, 2)
        adapter = MeasureSelectAdapter(::onMeasureClick)

        binding.recylerMeasure.adapter = adapter

        measureViewModel.lastMeasures.observe(viewLifecycleOwner) {
            adapter.measures = measureViewModel.lastMeasures.value!!
            adapter.notifyDataSetChanged()
        }

        measureViewModel.getAllMeasures()
    }

    private fun onMeasureClick(measurePosition: Int) {
        measureViewModel.selectedPart.value = measureViewModel.lastMeasures.value!![measurePosition].first.part
        navController.navigate(R.id.action_measureFragment_to_measureReviewFragment)
    }
}