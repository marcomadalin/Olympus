package com.marcomadalin.olympus.data.repositories

import com.marcomadalin.olympus.data.database.daos.RoutineDAO
import com.marcomadalin.olympus.data.database.entities.toData
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.domain.model.Routine
import com.marcomadalin.olympus.domain.model.toDomain
import javax.inject.Inject


class RoutineRepository @Inject constructor(private val routineDAO: RoutineDAO) {

    suspend fun getAllRoutines() : List<Routine> {
        return routineDAO.getAllRoutines().map { it.toDomain() }
    }

    suspend fun getRoutine(id: Int) : Routine {
        return routineDAO.getRoutine(id).toDomain()
    }

    suspend fun getAllRoutineExercises(id: Int) : List<Exercise> {
        return routineDAO.getAllRoutineExercises(id).map { it.toDomain() }
    }

    suspend fun deleteAllUserRoutines(id : Int) {
        routineDAO.deleteAllUserRoutines(id)
    }

    suspend fun saveAllRoutines(routines : List<Routine>) {
        routineDAO.insertAllRoutines(routines.map { it.toData() })
    }

    suspend fun saveRoutine(routine : Routine) {
        routineDAO.insertRoutine(routine.toData())
    }

    suspend fun updateRoutine(routine: Routine) {
        routineDAO.updateRoutine(routine.toData())
    }

    suspend fun deleteRoutine(routine : Routine) {
        routineDAO.deleteRoutine(routine.toData())
    }

    suspend fun deleteAllRoutines() {
        routineDAO.deleteAllRoutines()
    }
}