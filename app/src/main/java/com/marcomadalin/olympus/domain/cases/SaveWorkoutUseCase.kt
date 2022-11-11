package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseRepository
import com.marcomadalin.olympus.data.repositories.SetRepository
import com.marcomadalin.olympus.data.repositories.WorkoutRepository
import com.marcomadalin.olympus.domain.model.Workout
import javax.inject.Inject

class SaveWorkoutUseCase @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository,
    private val setRepository: SetRepository){

    suspend operator fun invoke(workout: Workout){
        workout.id = workoutRepository.saveWorkout(workout)
        workout.exercises.forEach {exercise ->
            exercise.workoutId = workout.id
            exercise.id = exerciseRepository.saveExercise(exercise)
            exercise.sets.forEach { set ->
                set.exerciseId = exercise.id
                set.id = setRepository.saveSet(set)
            }
        }
    }

}