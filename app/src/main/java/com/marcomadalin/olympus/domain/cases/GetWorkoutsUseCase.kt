package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseDataRepository
import com.marcomadalin.olympus.data.repositories.ExerciseRepository
import com.marcomadalin.olympus.data.repositories.WorkoutRepository
import com.marcomadalin.olympus.domain.model.Workout
import javax.inject.Inject

class GetWorkoutsUseCase @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
    private val exerciseDataRepository: ExerciseDataRepository,
){

    suspend operator fun invoke():MutableList<Workout> {
        val result = workoutRepository.getAllWorkouts()
        result.forEach{ workout ->
            val exercises = workoutRepository.getAllWorkoutExercises(workout.id)
            exercises.forEach{ exercise ->
                exercise.name = exerciseDataRepository.getExercisesData(exercise.exerciseDataId).name
                exercise.sets = exerciseRepository.getAllExerciseSets(exercise.id).toMutableList()
            }
            workout.exercises = exercises.toMutableList()
        }
        return result.toMutableList()
    }

}