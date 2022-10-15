package com.marcomadalin.olympus.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomadalin.olympus.domain.cases.GetWorkoutsUseCase
import com.marcomadalin.olympus.domain.cases.SaveWorkoutUseCase
import com.marcomadalin.olympus.domain.model.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val getWorkoutUseCase: GetWorkoutsUseCase,
    private val saveWorkoutUseCase: SaveWorkoutUseCase) : ViewModel() {

    private val workoutModel = MutableLiveData<MutableMap<LocalDate,Workout>>()

    fun getWorkouts() {
        viewModelScope.launch {
            val result = getWorkoutUseCase()
            if (result.isNotEmpty()) workoutModel.postValue(result)
        }
    }

    fun saveWorkout(workout: Workout) {
        viewModelScope.launch {
            val result = saveWorkoutUseCase(workout)
            val workouts = workoutModel.value
            workouts?.set(result.date, result)
            workoutModel.postValue(workouts!!)
        }
    }


}