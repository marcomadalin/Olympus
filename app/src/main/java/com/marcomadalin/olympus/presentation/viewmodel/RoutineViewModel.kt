package com.marcomadalin.olympus.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomadalin.olympus.domain.cases.DeleteRoutineUseCase
import com.marcomadalin.olympus.domain.cases.DeleteRoutinesUseCase
import com.marcomadalin.olympus.domain.cases.GetRoutinesUseCase
import com.marcomadalin.olympus.domain.cases.SaveRoutineUseCase
import com.marcomadalin.olympus.domain.model.Routine
import com.marcomadalin.olympus.domain.model.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val getRoutinesUseCase: GetRoutinesUseCase,
    private val saveRoutineUseCase: SaveRoutineUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase,
    private val deleteRoutinesUseCase: DeleteRoutinesUseCase
    ) : ViewModel() {

    val searchText = MutableLiveData("")

    val routines = MutableLiveData<List<Routine>?>()

    val selectedRoutine = MutableLiveData<Routine?>()

    val newRoutine = MutableLiveData(false)

    fun getRoutines() {
        viewModelScope.launch { routines.value = getRoutinesUseCase()}
    }

    fun saveRoutine(routine: Routine) {
        viewModelScope.launch {
            saveRoutineUseCase(routine)
            routines.value = getRoutinesUseCase()
        }
    }

    fun createSelectedRoutineCopy() {
        viewModelScope.launch {
            val routine = Routine(selectedRoutine.value!!)
            saveRoutineUseCase(routine)
            for (i in selectedRoutine.value!!.supersets.indices) {
                for (j in selectedRoutine.value!!.supersets[i].indices) {
                    for (k in selectedRoutine.value!!.exercises.indices) {
                        if (selectedRoutine.value!!.exercises[k].id == selectedRoutine.value!!.supersets[i].elementAt(j))
                            routine.supersets[i].add(routine.exercises[k].id)
                    }
                }
            }
            saveRoutineUseCase(routine)
            routines.value = getRoutinesUseCase()
        }
    }

    fun deleteRoutine(routine: Routine) {
        viewModelScope.launch {
            deleteRoutineUseCase(routine)
            routines.value = getRoutinesUseCase()
        }
    }

    fun createRoutineFromWorkout(workout: Workout) {
        viewModelScope.launch {
            val routine = Routine(workout)
            saveRoutineUseCase(routine)
            for (i in workout.supersets.indices) {
                for (j in workout.supersets[i].indices) {
                    for (k in workout.exercises.indices) {
                        if (workout.exercises[k].id == workout.supersets[i].elementAt(j))
                            routine.supersets[i].add(routine.exercises[k].id)
                    }
                }
            }
            saveRoutineUseCase(routine)
            routines.value = getRoutinesUseCase()
        }
    }

}