package com.marcomadalin.olympus.data.repositories

import com.marcomadalin.olympus.data.database.daos.ExerciseDAO
import com.marcomadalin.olympus.data.database.entities.toData
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.toDomain
import javax.inject.Inject


class ExerciseRepository @Inject constructor(private val exerciseDAO: ExerciseDAO) {

    suspend fun getAllExercises() : List<Exercise> {
        return exerciseDAO.getAllExercises().map { it.toDomain() }
    }

    suspend fun getExercise(id: Long) : Exercise {
        return exerciseDAO.getExercise(id).toDomain()
    }

    suspend fun getAllExerciseSets(id: Long) : List<Set> {
        return exerciseDAO.getAllExerciseSets(id).map { it.toDomain() }
    }

    suspend fun deleteAllRoutineExercises(id : Long) {
        exerciseDAO.deleteAllRoutineExercises(id)
    }

    suspend fun saveAllExercises(exercises : List<Exercise>) {
        exerciseDAO.insertAllExercises(exercises.map { it.toData() })
    }

    suspend fun saveExercise(exercise : Exercise) : Long {
        return exerciseDAO.insertExercise(exercise.toData())
    }

    suspend fun updateExercise(exercise: Exercise) {
        exerciseDAO.updateExercise(exercise.toData())
    }

    suspend fun deleteExercise(exercise : Exercise) {
        exerciseDAO.deleteExercise(exercise.toData())
    }

    suspend fun deleteAllExercises() {
        exerciseDAO.deleteAllExercises()
    }
}