package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseDataRepository
import com.marcomadalin.olympus.data.repositories.ExerciseRepository
import com.marcomadalin.olympus.data.repositories.RoutineRepository
import com.marcomadalin.olympus.domain.model.Routine
import javax.inject.Inject

class GetRoutinesUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val exerciseRepository: ExerciseRepository,
    private val exerciseDataRepository: ExerciseDataRepository
){

    suspend operator fun invoke():List<Routine> {
        val result = routineRepository.getAllRoutines()
        result.forEach{ routine ->
            val exercises = routineRepository.getAllRoutineExercises(routine.id)
            exercises.forEach{ exercise ->
                exercise.name = exerciseDataRepository.getExercisesData(exercise.exerciseDataId).name
                exercise.sets = exerciseRepository.getAllExerciseSets(exercise.id).toMutableList()
            }
            routine.exercises = exercises.toMutableList()
        }
        return result
    }

}