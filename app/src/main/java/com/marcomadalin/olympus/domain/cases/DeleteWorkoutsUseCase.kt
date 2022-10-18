package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseRepository
import com.marcomadalin.olympus.data.repositories.SetRepository
import com.marcomadalin.olympus.data.repositories.WorkoutRepository
import javax.inject.Inject

class DeleteWorkoutsUseCase @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
    private val setRepository : SetRepository
){

    suspend operator fun invoke() {
        setRepository.deleteAllSets()
        exerciseRepository.deleteAllExercises()
        workoutRepository.deleteAllWorkouts()
    }

}