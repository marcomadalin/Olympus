package com.marcomadalin.olympus.data.repositories

import com.marcomadalin.olympus.data.database.daos.WorkoutDAO
import com.marcomadalin.olympus.data.database.entities.toData
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.domain.model.toDomain
import javax.inject.Inject


class WorkoutRepository @Inject constructor(private val workoutDAO: WorkoutDAO) {

    suspend fun getAllWorkouts() : List<Workout> {
        return workoutDAO.getAllWorkouts().map { it.toDomain() }
    }

    suspend fun getWorkout(id: Int) : Workout {
        return workoutDAO.getWorkout(id).toDomain()
    }

    suspend fun getAllWorkoutExercises(id: Int) : List<Exercise> {
        return workoutDAO.getAllWorkoutExercises(id).map { it.toDomain() }
    }

    suspend fun deleteAllUserWorkouts(id : Int) {
        workoutDAO.deleteAllUserWorkouts(id)
    }

    suspend fun insertAllWorkouts(workouts : List<Workout>) {
        workoutDAO.insertAllWorkouts(workouts.map { it.toData() })
    }

    suspend fun insertWorkout(workout : Workout) {
        workoutDAO.insertWorkout(workout.toData())
    }

    suspend fun updateWorkout(workout: Workout) {
        workoutDAO.updateWorkout(workout.toData())
    }

    suspend fun deleteWorkout(workout : Workout) {
        workoutDAO.deleteWorkout(workout.toData())
    }

    suspend fun deleteAllWorkouts() {
        workoutDAO.deleteAllWorkouts()
    }
}