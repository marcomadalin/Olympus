package com.marcomadalin.olympus.data.repositories

import com.marcomadalin.olympus.data.database.daos.StatisticDAO
import com.marcomadalin.olympus.data.database.entities.toData
import com.marcomadalin.olympus.domain.model.Statistic
import com.marcomadalin.olympus.domain.model.toDomain
import javax.inject.Inject


class StatisticRepository @Inject constructor(private val statisticDAO: StatisticDAO) {

    suspend fun getAllStatistics() : List<Statistic> {
        return statisticDAO.getAllStatistics().map { it.toDomain() }
    }

    suspend fun getStatistic() : Statistic {
        return statisticDAO.getStatistic().toDomain()
    }

    suspend fun deleteAllUserStatistics(id : Long) {
        statisticDAO.deleteAllUserStatistics(id)
    }

    suspend fun saveAllStatistic(statistics : List<Statistic>) {
        statisticDAO.insertAllStatistic(statistics.map { it.toData() })
    }

    suspend fun saveStatistic(statistic : Statistic) {
        statisticDAO.insertStatistic(statistic.toData())
    }

    suspend fun updateStatistic(statistic: Statistic) {
        statisticDAO.updateStatistic(statistic.toData())
    }

    suspend fun deleteStatistic(statistic : Statistic) {
        statisticDAO.deleteStatistic(statistic.toData())
    }

    suspend fun deleteAllStatistics() {
        statisticDAO.deleteAllStatistics()
    }
}