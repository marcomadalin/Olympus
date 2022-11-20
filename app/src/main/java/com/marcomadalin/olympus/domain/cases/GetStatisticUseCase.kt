package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.StatisticRepository
import com.marcomadalin.olympus.domain.model.Statistic
import javax.inject.Inject

class GetStatisticUseCase @Inject constructor(private val statisticRepository: StatisticRepository){

    suspend operator fun invoke(): Statistic? {
        return statisticRepository.getStatistic()
    }

}