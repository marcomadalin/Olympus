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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.ExerciseCreateRadioBinding
import com.marcomadalin.olympus.databinding.FragmentCreateExerciseBinding
import com.marcomadalin.olympus.domain.model.ExerciseData
import com.marcomadalin.olympus.domain.model.enums.Equipment
import com.marcomadalin.olympus.domain.model.enums.ExerciseType
import com.marcomadalin.olympus.domain.model.enums.Muscle
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseCreateFragment : Fragment() {

    //TODO STORE IMAGE

    private var _binding : FragmentCreateExerciseBinding? = null
    private val binding get() = _binding!!

    private val exerciseDataViewModel : ExerciseDataViewModel by activityViewModels()

    private lateinit var navController: NavController

    private var exerciseCreated = mutableListOf(false, false, false)
    private var nameChanged = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        navController = findNavController()

        exerciseDataViewModel.newExercise.postValue(ExerciseData())

        binding.backButtonSummary5.setOnClickListener{
            navController.popBackStack()
            (activity as MainActivity).showNavigationBar()
        }

        binding.exerciseNameCreate.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val name = binding.exerciseNameCreate.text.toString()
                if (name.isNotEmpty()) {
                    nameChanged = true
                    exerciseDataViewModel.newExercise.value!!.name = name
                    if (exerciseCreated[0] && exerciseCreated[1] && exerciseCreated[2] && nameChanged) binding.done.isEnabled = true
                }
            }
        }

        binding.typeSelect.setOnClickListener{createDialog(exerciseDataViewModel.exerciseTypes.value!!, "Select exercise type", 0)}
        binding.equipmentSelect.setOnClickListener{createDialog(exerciseDataViewModel.equipmentFilters.value!!, "Select equipment", 1)}
        binding.muscleSelect.setOnClickListener{createDialog(exerciseDataViewModel.muscleFilters.value!!, "Select muscle group", 2)}

        binding.done.setOnClickListener{
            if (it.isEnabled) {
                exerciseDataViewModel.saveNewExercise()
                navController.popBackStack()
            }
        }
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
                    when (buttonPressed) {
                        0 -> {
                            binding.typeSelect.text = items[radioBinding.radioGroup.indexOfChild(child)]
                            exerciseDataViewModel.newExercise.value!!.type = ExerciseType.valueOf(binding.typeSelect.text.toString().replace(" ","_"))
                        }
                        1 -> {
                            binding.equipmentSelect.text = items[radioBinding.radioGroup.indexOfChild(child)]
                            exerciseDataViewModel.newExercise.value!!.equipment = Equipment.valueOf(binding.equipmentSelect.text.toString().replace(" ","_"))
                        }
                        else -> {
                            binding.muscleSelect.text = items[radioBinding.radioGroup.indexOfChild(child)]
                            exerciseDataViewModel.newExercise.value!!.primaryMuscle = Muscle.valueOf(binding.muscleSelect.text.toString().replace(" ","_"))
                        }
                    }
                }
                else child.setTextColor(ContextCompat.getColor(this.requireContext(), R.color.text))
            }
        }

        radioBinding.title6.text = title

        builder.setView(view)
        val alertDialog = builder.show()

        radioBinding.creatSubmit.setOnClickListener{
            exerciseCreated[buttonPressed] = true
            if (exerciseCreated[0] && exerciseCreated[1] && exerciseCreated[2] && nameChanged) binding.done.isEnabled = true
            alertDialog.dismiss()
        }

        radioBinding.createCancel.setOnClickListener{
            binding.typeSelect.text = "Select"
            binding.done.isEnabled = false
            alertDialog.dismiss()
        }

    }
}