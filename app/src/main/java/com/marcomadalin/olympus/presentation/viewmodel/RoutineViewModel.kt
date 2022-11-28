package com.marcomadalin.olympus.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomadalin.olympus.domain.cases.DeleteRoutineUseCase
import com.marcomadalin.olympus.domain.cases.DeleteRoutinesUseCase
import com.marcomadalin.olympus.domain.cases.GetRoutinesUseCase
import com.marcomadalin.olympus.domain.cases.SaveRoutineUseCase
import com.marcomadalin.olympus.domain.model.Routine
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

    fun getRoutines() {
        viewModelScope.launch {routines.postValue(getRoutinesUseCase())}
    }

    fun saveRoutine(routine: Routine) {
        viewModelScope.launch {
            saveRoutineUseCase(routine)
            routines.postValue(getRoutinesUseCase())
        }
    }

    fun deleteRoutine(routine: Routine) {
        viewModelScope.launch { deleteRoutineUseCase(routine) }
    }

    fun deleteAllRoutines() {
        viewModelScope.launch { deleteAllRoutines() }
    }

}