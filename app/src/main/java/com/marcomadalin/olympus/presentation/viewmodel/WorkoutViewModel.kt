package com.marcomadalin.olympus.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomadalin.olympus.domain.cases.DeleteWorkoutUseCase
import com.marcomadalin.olympus.domain.cases.DeleteWorkoutsUseCase
import com.marcomadalin.olympus.domain.cases.GetLiveWorkoutUseCase
import com.marcomadalin.olympus.domain.cases.GetWorkoutUseCase
import com.marcomadalin.olympus.domain.cases.GetWorkoutsUseCase
import com.marcomadalin.olympus.domain.cases.SaveWorkoutUseCase
import com.marcomadalin.olympus.domain.model.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val getWorkoutUseCase: GetWorkoutUseCase,
    private val getLiveWorkoutUseCase: GetLiveWorkoutUseCase,
    private val getWorkoutsUseCase: GetWorkoutsUseCase,
    private val saveWorkoutUseCase: SaveWorkoutUseCase,
    private val deleteWorkoutsUseCase: DeleteWorkoutsUseCase,
    private val deleteWorkoutUseCase: DeleteWorkoutUseCase
    ) : ViewModel() {

    val workouts = MutableLiveData<MutableList<Workout>?>()

    val selectedDate = MutableLiveData(LocalDate.now())

    val selectedWorkout = MutableLiveData<Workout?>()

    val editingRoutine = MutableLiveData(false)

    val liveWorkout = MutableLiveData<Workout?>()

    fun getWorkout() {
        viewModelScope.launch {selectedWorkout.value = getWorkoutUseCase(selectedDate.value!!)}
    }

    fun getLiveWorkout() {
        viewModelScope.launch {liveWorkout.value = getLiveWorkoutUseCase()}
    }

    fun getWorkouts() {
        viewModelScope.launch {workouts.postValue(getWorkoutsUseCase())}
    }

    fun saveWorkout(workout: Workout) {
        viewModelScope.launch {
            saveWorkoutUseCase(workout)
            selectedWorkout.value = getWorkoutUseCase(selectedDate.value!!)
            workouts.value = getWorkoutsUseCase()
        }
    }

    fun saveLiveWorkout(workout: Workout) {
        viewModelScope.launch {
            saveWorkoutUseCase(workout)
            liveWorkout.value = getLiveWorkoutUseCase()
            workouts.value = getWorkoutsUseCase()
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            deleteWorkoutUseCase(workout)
            workouts.value = getWorkoutsUseCase()
        }
    }

}