package com.marcomadalin.olympus.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomadalin.olympus.domain.cases.GetStatisticUseCase
import com.marcomadalin.olympus.domain.cases.SaveStatisticUseCase
import com.marcomadalin.olympus.domain.model.Statistic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getStatisticUseCase: GetStatisticUseCase,
    private val saveStatisticUseCase: SaveStatisticUseCase,
    ) : ViewModel() {

    val statistic = MutableLiveData<Statistic?>()

    fun getStatistic() {
        viewModelScope.launch {statistic.postValue(getStatisticUseCase())}
    }

    fun saveStatistic() {
        viewModelScope.launch {saveStatisticUseCase(statistic.value!!)}
    }
}