package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.ExerciseCreateRadioBinding
import com.marcomadalin.olympus.databinding.FragmentEditExerciseBinding
import com.marcomadalin.olympus.domain.model.enums.Equipment
import com.marcomadalin.olympus.domain.model.enums.Muscle
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseEditFragment : Fragment() {

    private var _binding : FragmentEditExerciseBinding? = null
    private val binding get() = _binding!!

    private val exerciseDataViewModel : ExerciseViewModel by activityViewModels()

    private lateinit var navController: NavController

    private var radioIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        navController = findNavController()

        binding.backButtonExerciseEdit.setOnClickListener{
            navController.navigate(R.id.action_exerciseEditFragment_to_exerciseReviewFragment)
            (activity as MainActivity).showNavigationBar()
        }
        val exercise = exerciseDataViewModel.selectedExercise.value!!

        binding.exerciseNameEdit2.setText(exerciseDataViewModel.selectedExercise.value!!.name)
        binding.exerciseNameEdit2.doOnTextChanged { _, _, _, _ ->
            val name = binding.exerciseNameEdit2.text.toString()
            if (!name.isNullOrEmpty()) exerciseDataViewModel.selectedExercise.value!!.name = name
        }

        binding.equipmentSelectEdit.text = exercise.equipment.toString().replace(" ","_")
        binding.muscleSelectEdit.text = exercise.primaryMuscle.toString().replace(" ","_")
        binding.equipmentSelectEdit.setOnClickListener{createDialog(exerciseDataViewModel.equipmentFilters.value!!, "Select equipment", 0)}
        binding.muscleSelectEdit.setOnClickListener{createDialog(exerciseDataViewModel.muscleFilters.value!!, "Select muscle group", 1)}
    }

    override fun onDestroy() {
        super.onDestroy()
        exerciseDataViewModel.saveExerciseData(exerciseDataViewModel.selectedExercise.value!!)
    }

    private fun createDialog(items : List<String>, title : String, buttonPressed : Int) {
        val builder = AlertDialog.Builder(this.requireContext(), R.style.MyDialog)
        val view = layoutInflater.inflate(R.layout.exercise_create_radio, null, false)
        val radioBinding = ExerciseCreateRadioBinding.bind(view)

        val radioStyle = ContextThemeWrapper(radioBinding.radioGroup.context, R.style.MyRadioButton)
        for (type in items.toTypedArray()) {
            val radioButton = RadioButton(radioStyle)
            radioButton.text = type
            radioButton.setTextColor(ContextCompat.getColor(this.requireContext(), R.color.text))
            radioBinding.radioGroup.addView(radioButton)
        }

        radioBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            for (child in radioBinding.radioGroup.children) {
                child as RadioButton
                if (child.id == checkedId) {
                    child.setTextColor(ContextCompat.getColor(this.requireContext(), R.color.buttons))
                    radioIndex = radioBinding.radioGroup.indexOfChild(child)
                }
                else child.setTextColor(ContextCompat.getColor(this.requireContext(), R.color.text))
            }
        }

        radioBinding.title6.text = title

        builder.setView(view)
        val alertDialog = builder.show()

        radioBinding.creatSubmit.setOnClickListener{
            if (buttonPressed == 0) {
                binding.equipmentSelectEdit.text = items[radioIndex]
                exerciseDataViewModel.selectedExercise.value!!.equipment = Equipment.valueOf(binding.equipmentSelectEdit.text.toString().replace(" ","_"))
            }
            else {
                binding.muscleSelectEdit.text = items[radioIndex]
                exerciseDataViewModel.selectedExercise.value!!.primaryMuscle = Muscle.valueOf(binding.muscleSelectEdit.text.toString().replace(" ","_"))
            }
            alertDialog.dismiss()
        }

        radioBinding.createCancel.setOnClickListener {
            val exercise = exerciseDataViewModel.selectedExercise.value!!
            if (buttonPressed == 0) binding.equipmentSelectEdit.text = exercise.equipment.toString().replace(" ","_")
            else binding.muscleSelectEdit.text = exercise.primaryMuscle.toString().replace(" ","_")
            alertDialog.dismiss()
        }

    }
}