package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseDataRepository
import javax.inject.Inject

class DeleteExercisesDataUseCase @Inject constructor(
    private val exerciseDataRepository: ExerciseDataRepository
){

    suspend operator fun invoke() {
        exerciseDataRepository.deleteAllExercisesData()
    }

}