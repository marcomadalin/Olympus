package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentExerciseReviewBinding
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseDataViewModel

class ExerciseReviewFragment : Fragment() {

    //TODO Add plots

    //TODO reorder favorites

    private var _binding : FragmentExerciseReviewBinding? = null
    private val binding get() = _binding!!

    private val exerciseDataViewModel : ExerciseDataViewModel by activityViewModels()

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

        binding.backButtonReview.setOnClickListener{
            (activity as MainActivity).showNavigationBar()
            navController.popBackStack()
        }

        updateFavorite()
        binding.favButtonReview.setOnClickListener{
            exerciseDataViewModel.selectedExercise.value!!.favourite = !exerciseDataViewModel.selectedExercise.value!!.favourite
            updateFavorite()
        }

        binding.summaryTitle6.text = exerciseDataViewModel.selectedExercise.value!!.name

        binding.typeReview.text = exerciseDataViewModel.selectedExercise.value!!.type.toString().replace("_", " ")
        binding.equipmentReview.text = exerciseDataViewModel.selectedExercise.value!!.equipment.toString().replace("_", " ")
        binding.muscleReview.text = exerciseDataViewModel.selectedExercise.value!!.primaryMuscle.toString().replace("_", " ")

        binding.weighReview.text = exerciseDataViewModel.selectedExercise.value!!.maxWeight.toString() + " Kg"
        binding.ormReview.text = exerciseDataViewModel.selectedExercise.value!!.orm.toString() + " Kg"
        binding.volumeSetReview.text = exerciseDataViewModel.selectedExercise.value!!.bestSetWeight.toString() + " x " + exerciseDataViewModel.selectedExercise.value!!.bestSetReps
        binding.volumeWeightReview.text = (exerciseDataViewModel.selectedExercise.value!!.bestSetWeight * exerciseDataViewModel.selectedExercise.value!!.bestSetReps).toString() + " Kg"

        binding.ormButtonReview.setOnClickListener{}
        binding.volumeButtonReview.setOnClickListener{}
        binding.weightButtonReview.setOnClickListener{}
    }


    private fun updateFavorite() {
        if (exerciseDataViewModel.selectedExercise.value!!.favourite) {
            binding.favButtonReview.background = ContextCompat.getDrawable(this.requireContext(), R.drawable.star_gold)
        }
        else binding.favButtonReview.background = ContextCompat.getDrawable(this.requireContext(), R.drawable.star)
    }
}