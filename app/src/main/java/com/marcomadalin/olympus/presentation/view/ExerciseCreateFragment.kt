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
import com.marcomadalin.olympus.databinding.FragmentCreateExerciseBinding
import com.marcomadalin.olympus.domain.model.ExerciseData
import com.marcomadalin.olympus.domain.model.enums.Equipment
import com.marcomadalin.olympus.domain.model.enums.Muscle
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class ExerciseCreateFragment : Fragment() {

    //TODO STORE IMAGE

    private var _binding : FragmentCreateExerciseBinding? = null
    private val binding get() = _binding!!

    private val exerciseDataViewModel : ExerciseViewModel by activityViewModels()

    private lateinit var navController: NavController

    private lateinit var exerciseCreated : MutableList<Boolean>
    private var nameChanged by Delegates.notNull<Boolean>()
    private var radioIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        exerciseCreated = mutableListOf(false, false)
        nameChanged = false

        navController = findNavController()

        exerciseDataViewModel.newExercise.postValue(ExerciseData(default=false))

        binding.backButtonSummary5.setOnClickListener{
            navController.navigate(R.id.exercise)
            (activity as MainActivity).showNavigationBar()
        }

        binding.exerciseNameCreate.doOnTextChanged { _, _, _, _ ->
            val name = binding.exerciseNameCreate.text.toString()
            if (!name.isNullOrEmpty()) {
                nameChanged = true
                exerciseDataViewModel.newExercise.value!!.name = name
                if (exerciseCreated[0] && exerciseCreated[1] && nameChanged) binding.done.isEnabled = true
            }
        }

        binding.equipmentSelect.setOnClickListener{createDialog(exerciseDataViewModel.equipmentFilters.value!!, "Select equipment", 0)}
        binding.muscleSelect.setOnClickListener{createDialog(exerciseDataViewModel.muscleFilters.value!!, "Select muscle group", 1)}

        binding.done.setOnClickListener{
            exerciseDataViewModel.saveNewExercise()
            navController.navigate(R.id.exercise)
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
                binding.equipmentSelect.text = items[radioIndex]
                exerciseDataViewModel.selectedExercise.value!!.equipment = Equipment.valueOf(binding.equipmentSelect.text.toString().replace(" ","_"))
            }
            else {
                binding.muscleSelect.text = items[radioIndex]
                exerciseDataViewModel.selectedExercise.value!!.primaryMuscle = Muscle.valueOf(binding.muscleSelect.text.toString().replace(" ","_"))
            }
            exerciseCreated[buttonPressed] = true
            if (exerciseCreated[0] && exerciseCreated[1] && nameChanged) binding.done.isEnabled = true
            alertDialog.dismiss()
        }

        radioBinding.createCancel.setOnClickListener {
            if (buttonPressed == 0) binding.equipmentSelect.text = "Select"
            else binding.muscleSelect.text = "Select"
            binding.done.isEnabled = false
            alertDialog.dismiss()
        }

    }
}