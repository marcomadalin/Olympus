package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseRepository
import com.marcomadalin.olympus.data.repositories.RoutineRepository
import com.marcomadalin.olympus.data.repositories.SetRepository
import com.marcomadalin.olympus.domain.model.Routine
import javax.inject.Inject

class SaveRoutineUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val exerciseRepository: ExerciseRepository,
    private val setRepository: SetRepository){

    suspend operator fun invoke(routine: Routine){
        routine.id = routineRepository.saveRoutine(routine)
        routine.exercises.forEach {exercise ->
            exercise.routineId = routine.id
            exercise.workoutId = -1
            exercise.id = exerciseRepository.saveExercise(exercise)
            exercise.sets.forEach { set ->
                set.exerciseId = exercise.id
                set.id = setRepository.saveSet(set)
            }
        }
    }

}