package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseRepository
import com.marcomadalin.olympus.data.repositories.RoutineRepository
import com.marcomadalin.olympus.data.repositories.SetRepository
import javax.inject.Inject

class DeleteRoutinesUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val exerciseRepository: ExerciseRepository,
    private val setRepository : SetRepository
){

    suspend operator fun invoke() {
        setRepository.deleteAllSets()
        exerciseRepository.deleteAllExercises()
        routineRepository.deleteAllRoutines()
    }

}