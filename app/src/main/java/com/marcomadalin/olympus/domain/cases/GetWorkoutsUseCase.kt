package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.ExerciseRepository
import com.marcomadalin.olympus.data.repositories.WorkoutRepository
import com.marcomadalin.olympus.domain.model.Workout
import java.time.LocalDate
import javax.inject.Inject

class GetWorkoutsUseCase @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val exerciseRepository: ExerciseRepository
){

    suspend operator fun invoke():MutableMap<LocalDate,Workout> {
        val result = workoutRepository.getAllWorkouts()
        result.forEach{ workout ->
            val exercises = workoutRepository.getAllWorkoutExercises(workout.id)
            exercises.forEach{ exercise ->
                exercise.sets = exerciseRepository.getAllExerciseSets(exercise.id).toMutableList()
            }
            workout.exercises = exercises.toMutableList()
        }
        return result.groupBy{ it.date }.mapValues{it.value[0]}.toMutableMap()
    }

}