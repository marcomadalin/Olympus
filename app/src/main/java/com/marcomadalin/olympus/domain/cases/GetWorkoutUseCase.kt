package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseDataRepository
import com.marcomadalin.olympus.data.repositories.ExerciseRepository
import com.marcomadalin.olympus.data.repositories.WorkoutRepository
import com.marcomadalin.olympus.domain.model.Workout
import java.time.LocalDate
import javax.inject.Inject

class GetWorkoutUseCase @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
    private val exerciseDataRepository: ExerciseDataRepository
){

    suspend operator fun invoke(date: LocalDate): Workout? {
        val result = workoutRepository.getWorkout(date)
        if (result != null) {
            val exercises = workoutRepository.getAllWorkoutExercises(result.id)
            exercises.forEach { exercise ->
                exercise.name = exerciseDataRepository.getExercisesData(exercise.exerciseDataId).name
                exercise.sets = exerciseRepository.getAllExerciseSets(exercise.id).toMutableList()
            }
            result.exercises = exercises.toMutableList()
        }
        return result
    }

}