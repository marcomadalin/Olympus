package com.marcomadalin.olympus.data.repositories

import com.marcomadalin.olympus.data.database.daos.WorkoutDAO
import com.marcomadalin.olympus.data.database.entities.toData
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.domain.model.toDomain
import java.time.LocalDate
import javax.inject.Inject


class WorkoutRepository @Inject constructor(private val workoutDAO: WorkoutDAO) {

    suspend fun getAllWorkouts() : List<Workout> {
        return workoutDAO.getAllWorkouts().map { it.toDomain() }
    }

    suspend fun getWorkout(id: Long) : Workout {
        return workoutDAO.getWorkout(id).toDomain()
    }

    suspend fun getWorkout(date: LocalDate) : Workout? {
        return workoutDAO.getWorkout(date.toString())?.toDomain()
    }

    suspend fun getAllWorkoutExercises(id: Long) : List<Exercise> {
        return workoutDAO.getAllWorkoutExercises(id).map { it.toDomain() }
    }

    suspend fun deleteAllUserWorkouts(id : Long) {
        workoutDAO.deleteAllUserWorkouts(id)
    }

    suspend fun saveAllWorkouts(workouts : List<Workout>) {
        workoutDAO.insertAllWorkouts(workouts.map { it.toData() })
    }

    suspend fun saveWorkout(workout : Workout) : Long {
        return workoutDAO.insertWorkout(workout.toData())
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