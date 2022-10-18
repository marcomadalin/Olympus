package com.marcomadalin.olympus.data.repositories

import com.marcomadalin.olympus.data.database.daos.SetDAO
import com.marcomadalin.olympus.data.database.entities.toData
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.toDomain
import javax.inject.Inject


class SetRepository @Inject constructor(private val setDAO: SetDAO) {

    suspend fun getAllSets() : List<Set> {
        return setDAO.getAllSets().map { it.toDomain() }
    }

    suspend fun getSet(id: Long) : Set {
        return setDAO.getSet(id).toDomain()
    }

    suspend fun deleteAllExerciseSets(id : Long) {
        setDAO.deleteAllExerciseSets(id)
    }

    suspend fun saveAllSet(sets : List<Set>) {
        setDAO.insertAllSet(sets.map { it.toData() })
    }

    suspend fun saveSet(set : Set) : Long {
        return setDAO.insertSet(set.toData())
    }

    suspend fun updateSet(set: Set) {
        setDAO.updateSet(set.toData())
    }

    suspend fun deleteSet(set : Set) {
        setDAO.deleteSet(set.toData())
    }

    suspend fun deleteAllSets() {
        setDAO.deleteAllSets()
    }
}