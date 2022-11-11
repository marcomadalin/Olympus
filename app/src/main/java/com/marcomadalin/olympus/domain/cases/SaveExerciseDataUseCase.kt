package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseDataRepository
import com.marcomadalin.olympus.domain.model.ExerciseData
import javax.inject.Inject

class SaveExerciseDataUseCase @Inject constructor(
    private val exerciseDataRepository: ExerciseDataRepository){

    suspend operator fun invoke(exerciseData: ExerciseData) {
        exerciseDataRepository.saveExerciseData(exerciseData)
    }

}