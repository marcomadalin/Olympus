package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.MeasureRepository
import com.marcomadalin.olympus.domain.model.Measure
import javax.inject.Inject

class SaveMeasuresUseCase @Inject constructor(private val measureRepository: MeasureRepository){

    suspend operator fun invoke(measures: List<Measure>){
        for (measure in measures) {
            measure.id = measureRepository.saveMeasure(measure)
        }
    }

}