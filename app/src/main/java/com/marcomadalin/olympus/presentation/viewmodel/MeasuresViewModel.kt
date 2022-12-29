package com.marcomadalin.olympus.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcomadalin.olympus.domain.cases.DeleteMeasureUseCase
import com.marcomadalin.olympus.domain.cases.GetMeasureValuesUseCase
import com.marcomadalin.olympus.domain.cases.GetMeasuresUseCase
import com.marcomadalin.olympus.domain.cases.SaveMeasureUseCase
import com.marcomadalin.olympus.domain.cases.SaveMeasuresUseCase
import com.marcomadalin.olympus.domain.model.Measure
import com.marcomadalin.olympus.domain.model.enums.MeasurePart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeasuresViewModel @Inject constructor(
    private val getMeasuresUseCase: GetMeasuresUseCase,
    private val getMeasureValuesUseCase: GetMeasureValuesUseCase,
    private val saveMeasureUseCase: SaveMeasureUseCase,
    private val saveMeasuresUseCase: SaveMeasuresUseCase,
    private val deleteMeasureUseCase: DeleteMeasureUseCase
    ) : ViewModel() {

    val lastMeasures = MutableLiveData<MutableList<Pair<Measure,Measure>>?>()

    val measureValues = MutableLiveData<MutableList<Measure>?>()

    val selectedPart = MutableLiveData<MeasurePart>()

    fun getAllMeasures() {
        viewModelScope.launch {
            lastMeasures.value = getMeasuresUseCase()
        }
    }

    fun getMeasureValues() {
        viewModelScope.launch {
            measureValues.value = getMeasureValuesUseCase(selectedPart.value.toString())
        }
    }

    fun saveMeasure(measure: Measure) {
        viewModelScope.launch {
            saveMeasureUseCase.invoke(measure)
            lastMeasures.value = getMeasuresUseCase()
            measureValues.value = getMeasureValuesUseCase(selectedPart.value.toString())
        }
    }

    fun saveAllMeasures(measures: List<Measure>) {
        viewModelScope.launch {
            saveMeasuresUseCase.invoke(measures)
            lastMeasures.value = getMeasuresUseCase()
            measureValues.value = getMeasureValuesUseCase(selectedPart.value.toString())
        }
    }

    fun deleteMeasure(measure: Measure) {
        viewModelScope.launch {
            deleteMeasureUseCase(measure)
        }
    }
}