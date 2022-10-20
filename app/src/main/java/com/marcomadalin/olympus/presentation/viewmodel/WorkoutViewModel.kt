package com.marcomadalin.olympus.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val deleteWorkoutsUseCase: DeleteWorkoutsUseCase
    ) : ViewModel() {

    val workoutModel = MutableLiveData<Workout?>()

    fun getWorkout(date: LocalDate) {
        viewModelScope.launch {workoutModel.postValue(getWorkoutUseCase(date))}
    }

    fun saveWorkout(workout: Workout) {
        viewModelScope.launch {saveWorkoutUseCase(workout)}
    }

    fun deleteWorkouts() {
        viewModelScope.launch {deleteWorkoutsUseCase()}
    }
}