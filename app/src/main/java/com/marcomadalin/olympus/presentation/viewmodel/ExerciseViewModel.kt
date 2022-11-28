package com.marcomadalin.olympus.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomadalin.olympus.domain.cases.DeleteExerciseDataUseCase
import com.marcomadalin.olympus.domain.cases.DeleteExerciseUseCase
import com.marcomadalin.olympus.domain.cases.DeleteExercisesDataUseCase
import com.marcomadalin.olympus.domain.cases.DeleteSetUseCase
import com.marcomadalin.olympus.domain.cases.GetExercisesDataUseCase
import com.marcomadalin.olympus.domain.cases.SaveExerciseDataUseCase
import com.marcomadalin.olympus.domain.cases.SaveExercisesDataUseCase
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.domain.model.ExerciseData
import com.marcomadalin.olympus.domain.model.enums.Equipment
import com.marcomadalin.olympus.domain.model.enums.ExerciseType
import com.marcomadalin.olympus.domain.model.enums.Muscle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getExercisesDataUseCase: GetExercisesDataUseCase,
    private val saveExerciseDataUseCase: SaveExerciseDataUseCase,
    private val saveExercisesDataUseCase: SaveExercisesDataUseCase,
    private val deleteExercisesDataCase: DeleteExercisesDataUseCase,
    private val deleteExerciseDataUseCase: DeleteExerciseDataUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase,
    private val deleteSetUseCase: DeleteSetUseCase,
    ) : ViewModel() {

    //Misc
    val equipmentFilters = MutableLiveData<List<String>>()

    val muscleFilters = MutableLiveData<List<String>>()

    val exerciseTypes = MutableLiveData<List<String>>()

    //Exercise fragment
    val exercises = MutableLiveData<MutableList<ExerciseData>?>()

    val selectedExercise = MutableLiveData<ExerciseData>()

    val selectedFilters = MutableLiveData<Set<String>>(setOf())

    val searchFilter = MutableLiveData("")

    val swappedExercisePosition = MutableLiveData<Int>()

    //Create exercise fragment

    val newExercise = MutableLiveData<ExerciseData>()

    //Select exercise fragment

    val selectedExercises = MutableLiveData<MutableMap<Long,String>>(mutableMapOf())

    val selectedFilters2 = MutableLiveData<Set<String>>(setOf())

    val selectOne = MutableLiveData(false)

    fun getExercisesData() {
        viewModelScope.launch {exercises.postValue(getExercisesDataUseCase())}
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

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch { deleteExerciseUseCase(exercise) }
    }

    fun deleteSet(set: com.marcomadalin.olympus.domain.model.Set) {
        viewModelScope.launch { deleteSetUseCase(set) }
    }

    fun deleteAllExercisesData() {
        viewModelScope.launch {deleteExercisesDataCase()}
    }

    fun saveNewExercise() {
        viewModelScope.launch {
            saveExerciseDataUseCase(newExercise.value!!)
            newExercise.postValue(ExerciseData())
            exercises.postValue(getExercisesDataUseCase())
        }
    }
}