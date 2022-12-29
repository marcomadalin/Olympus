package com.marcomadalin.olympus.data.repositories

import com.marcomadalin.olympus.data.database.daos.MeasureDAO
import com.marcomadalin.olympus.data.database.entities.toData
import com.marcomadalin.olympus.domain.model.Measure
import com.marcomadalin.olympus.domain.model.toDomain
import javax.inject.Inject


class MeasureRepository @Inject constructor(private val measureDAO: MeasureDAO) {

    suspend fun getAllMeasures() : List<Measure> {
        return measureDAO.getAllMeasures().map { it.toDomain() }
    }

    suspend fun getMeasures(part: String) : List<Measure> {
        return measureDAO.getMeasures(part).map {it.toDomain()}
    }

    suspend fun getMeasureValues(part: String) : List<Measure> {
        return measureDAO.getMeasureValues(part).map { it.toDomain() }
    }

    suspend fun getMeasure(id: Long) : Measure {
        return measureDAO.getMeasure(id).toDomain()
    }

    suspend fun deleteAllUserMeasures(id : Long) {
        measureDAO.deleteAllUserMeasures(id)
    }

    suspend fun saveAllMeasures(measures : List<Measure>) {
        measureDAO.insertAllMeasures(measures.map { it.toData() })
    }

    suspend fun saveMeasure(measure : Measure) : Long {
        return measureDAO.insertMeasure(measure.toData())
    }

    suspend fun updateMeasure(measure: Measure) {
        measureDAO.updateMeasure(measure.toData())
    }

    suspend fun deleteMeasure(measure : Measure) {
        measureDAO.deleteMeasure(measure.toData())
    }

    suspend fun deleteAllMeasures() {
        measureDAO.deleteAllMeasures()
    }
}