package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.MeasureRepository
import com.marcomadalin.olympus.domain.model.Measure
import com.marcomadalin.olympus.domain.model.enums.MeasurePart
import javax.inject.Inject

class GetMeasuresUseCase @Inject constructor(private val measureRepository: MeasureRepository){

    suspend operator fun invoke(): MutableList<Pair<Measure, Measure>> {
        val parts = MeasurePart.values()
        val result = mutableListOf<Pair<Measure,Measure>>()
        for (part in parts) {
            val measures = measureRepository.getMeasures(part.toString())
            if (measures.isNotEmpty())  {
                if (measures.size == 1) {
                    result.add(Pair(measures[0], Measure(value=-1.0)))
                }
                else result.add(Pair(measures[0],measures[1]))
            }
        }
        return result
    }

}