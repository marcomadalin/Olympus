package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.MeasureRepository
import com.marcomadalin.olympus.domain.model.Measure
import javax.inject.Inject

class DeleteMeasureUseCase @Inject constructor(private val measureRepository: MeasureRepository){

    suspend operator fun invoke(measure: Measure) {
        measureRepository.deleteMeasure(measure)
    }
}