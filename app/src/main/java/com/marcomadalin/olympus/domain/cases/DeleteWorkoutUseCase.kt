package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseRepository
import com.marcomadalin.olympus.data.repositories.SetRepository
import com.marcomadalin.olympus.data.repositories.WorkoutRepository
import com.marcomadalin.olympus.domain.model.Workout
import javax.inject.Inject

class DeleteWorkoutUseCase @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
    private val setRepository : SetRepository
){

    suspend operator fun invoke(workout: Workout) {
        workout.exercises.forEach{ exercise ->
            exercise.sets.forEach { set ->
                setRepository.deleteSet(set)
            }
            exerciseRepository.deleteExercise(exercise)
        }
        workoutRepository.deleteWorkout(workout)
    }

}