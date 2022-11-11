package com.marcomadalin.olympus.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomadalin.olympus.domain.cases.DeleteExerciseDataUseCase
import com.marcomadalin.olympus.domain.cases.DeleteExercisesDataUseCase
import com.marcomadalin.olympus.domain.cases.GetExercisesDataUseCase
import com.marcomadalin.olympus.domain.cases.SaveExerciseDataUseCase
import com.marcomadalin.olympus.domain.model.ExerciseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDataViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesDataUseCase,
    private val saveExerciseDataUseCase: SaveExerciseDataUseCase,
    private val deleteExercisesDataCase: DeleteExercisesDataUseCase,
    private val deleteExerciseDataUseCase: DeleteExerciseDataUseCase
    ) : ViewModel() {

    val exercises = MutableLiveData<MutableList<ExerciseData?>?>()

    fun getExercisesData() {
        viewModelScope.launch {exercises.postValue(getExercisesUseCase())}
    }

    fun saveExerciseData(exerciseData: ExerciseData) {
        viewModelScope.launch {saveExerciseDataUseCase(exerciseData)}
    }

    fun deleteExerciseData(exerciseData: ExerciseData) {
        viewModelScope.launch {
            deleteExerciseDataUseCase(exerciseData)
        }
    }

    fun deleteExercisesData() {
        viewModelScope.launch {deleteExercisesDataCase()}
    }
}