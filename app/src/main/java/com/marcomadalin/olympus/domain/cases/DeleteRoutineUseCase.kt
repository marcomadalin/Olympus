package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseRepository
import com.marcomadalin.olympus.data.repositories.RoutineRepository
import com.marcomadalin.olympus.data.repositories.SetRepository
import com.marcomadalin.olympus.domain.model.Routine
import javax.inject.Inject

class DeleteRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val exerciseRepository: ExerciseRepository,
    private val setRepository : SetRepository
){

    suspend operator fun invoke(routine: Routine) {
        routine.exercises.forEach{ exercise ->
            exercise.sets.forEach { set ->
                setRepository.deleteSet(set)
            }
            exerciseRepository.deleteExercise(exercise)
        }
        routineRepository.deleteRoutine(routine)
    }

}