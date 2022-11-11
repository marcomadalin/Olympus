package com.marcomadalin.olympus.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomadalin.olympus.domain.cases.DeleteWorkoutUseCase
import com.marcomadalin.olympus.domain.cases.DeleteWorkoutsUseCase
import com.marcomadalin.olympus.domain.cases.GetWorkoutUseCase
import com.marcomadalin.olympus.domain.cases.SaveWorkoutUseCase
import com.marcomadalin.olympus.domain.model.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val getWorkoutUseCase: GetWorkoutUseCase,
    private val saveWorkoutUseCase: SaveWorkoutUseCase,
    private val deleteWorkoutsUseCase: DeleteWorkoutsUseCase,
    private val deleteWorkoutUseCase: DeleteWorkoutUseCase
    ) : ViewModel() {

    val selectedDate = MutableLiveData(LocalDate.now())

    val workout = MutableLiveData<Workout?>()

    fun getWorkout() {
        viewModelScope.launch {workout.postValue(getWorkoutUseCase(selectedDate.value!!))}
    }

    fun saveWorkout(workout: Workout) {
        viewModelScope.launch {saveWorkoutUseCase(workout)}
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch { deleteWorkoutUseCase(workout) }
    }

    fun deleteWorkouts() {
        viewModelScope.launch {deleteWorkoutsUseCase()}
    }
}