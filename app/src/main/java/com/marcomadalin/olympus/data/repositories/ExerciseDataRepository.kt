package com.marcomadalin.olympus.data.repositories

import com.marcomadalin.olympus.data.database.daos.ExerciseDataDAO
import com.marcomadalin.olympus.data.database.entities.toData
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.domain.model.ExerciseData
import com.marcomadalin.olympus.domain.model.toDomain
import javax.inject.Inject


class ExerciseDataRepository @Inject constructor(private val exerciseDataDAO: ExerciseDataDAO) {

    //TODO query to check if routine has exercise in case we delete one ExerciseData so we can propagate deletes without problems

    suspend fun getAllExercisesData() : List<ExerciseData>? {
        return exerciseDataDAO.getAllExercisesData()?.map { it.toDomain() }
    }

    suspend fun getExercisesData(id: Long) : ExerciseData {
        return exerciseDataDAO.getExercisesData(id).toDomain()
    }

    suspend fun getAllExerciseDataExercises(id: Long) : List<Exercise> {
        return exerciseDataDAO.getAllExerciseDataExercises(id).map { it.toDomain() }
    }

    suspend fun deleteAllUserExerciseData(id : Long) {
        exerciseDataDAO.deleteAllUserExerciseData(id)
    }

    suspend fun saveAllExerciseData(exercisesData : List<ExerciseData>) {
        exerciseDataDAO.insertAllExerciseData(exercisesData.map { it.toData() })
    }

    suspend fun saveExerciseData(exerciseData : ExerciseData) {
        exerciseDataDAO.insertExerciseData(exerciseData.toData())
    }

    suspend fun updateExerciseData(exerciseData: ExerciseData) {
        exerciseDataDAO.updateExerciseData(exerciseData.toData())
    }

    suspend fun deleteExerciseData(exerciseData : ExerciseData) {
        exerciseDataDAO.deleteExerciseData(exerciseData.toData())
    }

    suspend fun deleteAllExercisesData() {
        exerciseDataDAO.deleteAllExercisesData()
    }
}