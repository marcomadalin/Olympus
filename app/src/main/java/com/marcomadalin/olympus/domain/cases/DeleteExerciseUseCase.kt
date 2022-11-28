package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseRepository
import com.marcomadalin.olympus.data.repositories.SetRepository
import com.marcomadalin.olympus.domain.model.Exercise
import javax.inject.Inject

class DeleteExerciseUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val setRepository : SetRepository
){

    suspend operator fun invoke(exercise: Exercise) {
        exercise.sets.forEach { set -> setRepository.deleteSet(set) }
        exerciseRepository.deleteExercise(exercise)
    }

}