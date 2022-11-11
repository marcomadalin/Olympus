package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseDataRepository
import com.marcomadalin.olympus.domain.model.ExerciseData
import javax.inject.Inject

class GetExercisesDataUseCase @Inject constructor(
    private val exerciseDataRepository: ExerciseDataRepository
){

    suspend operator fun invoke() : MutableList<ExerciseData?> {
        return exerciseDataRepository.getAllExercisesData().toMutableList()
    }

}