package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.StatisticRepository
import com.marcomadalin.olympus.domain.model.Statistic
import javax.inject.Inject

class SaveStatisticUseCase @Inject constructor(
    private val statisticRepository: StatisticRepository){

    suspend operator fun invoke(statistic: Statistic) {
        statisticRepository.saveStatistic(statistic)
    }

}