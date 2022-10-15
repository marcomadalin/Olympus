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

    suspend fun getMeasure(id: Int) : Measure {
        return measureDAO.getMeasure(id).toDomain()
    }

    suspend fun deleteAllUserMeasures(id : Int) {
        measureDAO.deleteAllUserMeasures(id)
    }

    suspend fun saveAllMeasures(measures : List<Measure>) {
        measureDAO.insertAllMeasures(measures.map { it.toData() })
    }

    suspend fun savetMeasure(measure : Measure) {
        measureDAO.insertMeasure(measure.toData())
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