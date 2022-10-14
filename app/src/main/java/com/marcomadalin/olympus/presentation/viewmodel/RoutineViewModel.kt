package com.marcomadalin.olympus.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomadalin.olympus.domain.cases.GetRoutinesUseCase
import com.marcomadalin.olympus.domain.model.Routine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(private val getRoutinesUseCase: GetRoutinesUseCase) : ViewModel() {

    private val routinesModel = MutableLiveData<List<Routine>>()

    fun onCreate() {
        viewModelScope.launch {
            val result = getRoutinesUseCase()

            if (result.isNotEmpty()) routinesModel.postValue(result)
        }
    }

}