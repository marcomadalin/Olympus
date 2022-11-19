package com.marcomadalin.olympus.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomadalin.olympus.domain.cases.DeleteExerciseDataUseCase
import com.marcomadalin.olympus.domain.cases.DeleteExercisesDataUseCase
import com.marcomadalin.olympus.domain.cases.GetExercisesDataUseCase
import com.marcomadalin.olympus.domain.cases.SaveExerciseDataUseCase
import com.marcomadalin.olympus.domain.cases.SaveExercisesDataUseCase
import com.marcomadalin.olympus.domain.model.ExerciseData
import com.marcomadalin.olympus.domain.model.enums.Equipment
import com.marcomadalin.olympus.domain.model.enums.ExerciseType
import com.marcomadalin.olympus.domain.model.enums.Muscle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDataViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesDataUseCase,
    private val saveExerciseDataUseCase: SaveExerciseDataUseCase,
    private val saveExercisesDataUseCase: SaveExercisesDataUseCase,
    private val deleteExercisesDataCase: DeleteExercisesDataUseCase,
    private val deleteExerciseDataUseCase: DeleteExerciseDataUseCase
    ) : ViewModel() {

    val exercises = MutableLiveData<MutableList<ExerciseData>?>()

    val selectedExercise = MutableLiveData<ExerciseData>()

    val equipmentFilters = MutableLiveData<List<String>>()

    val muscleFilters = MutableLiveData<List<String>>()

    val exerciseTypes = MutableLiveData<List<String>>()

    val selectedFilters = MutableLiveData<Set<String>>(setOf())

    val searchFilter = MutableLiveData("")

    val newExercise = MutableLiveData<ExerciseData>()

    fun getExercisesData() {
        viewModelScope.launch {exercises.postValue(getExercisesUseCase())}
    }

    fun getFilters() {
        equipmentFilters.postValue(Equipment.values().toList().map { it.toString().replace("_"," ") })
        muscleFilters.postValue(Muscle.values().toList().map { it.toString().replace("_"," ") })
        exerciseTypes.postValue(ExerciseType.values().toList().map { it.toString().replace("_"," ") })
    }

    fun saveExerciseData(exerciseData: ExerciseData) {
        viewModelScope.launch {saveExerciseDataUseCase(exerciseData)}
    }

    fun saveAllExercisesData(exercisesData: List<ExerciseData>) {
        viewModelScope.launch {saveExercisesDataUseCase(exercisesData)}
    }

    fun deleteExerciseData(exerciseData: ExerciseData) {
        viewModelScope.launch {
            deleteExerciseDataUseCase(exerciseData)
        }
    }

    fun deleteAllExercisesData() {
        viewModelScope.launch {deleteExercisesDataCase()}
    }

    fun saveNewExercise() {
        viewModelScope.launch {
            saveExerciseDataUseCase(newExercise.value!!)
            newExercise.postValue(ExerciseData())
            exercises.postValue(getExercisesUseCase())
        }
    }
}