package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.MeasureRepository
import com.marcomadalin.olympus.domain.model.Measure
import javax.inject.Inject

class GetMeasureValuesUseCase @Inject constructor(private val measureRepository: MeasureRepository){

    suspend operator fun invoke(part: String):MutableList<Measure> {
        return measureRepository.getMeasureValues(part).toMutableList()
    }

}