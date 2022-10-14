package com.marcomadalin.olympus.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marcomadalin.olympus.data.database.entities.ExerciseEntity
import com.marcomadalin.olympus.data.database.entities.WorkoutEntity

@Dao
interface WorkoutDAO {
    @Query("SELECT * FROM Workouts")
    suspend fun getAllWorkouts() : List<WorkoutEntity>

    @Query("SELECT * FROM Workouts WHERE id = :id")
    suspend fun getWorkout(id: Int) : WorkoutEntity

    @Query("SELECT * FROM Workouts JOIN Exercises On Workouts.id = Exercises.routineId WHERE Workouts.id = :id")
    suspend fun getAllWorkoutExercises(id: Int) : List<ExerciseEntity>

    @Query("DELETE FROM Workouts WHERE userId = :id")
    suspend fun deleteAllUserWorkouts(id : Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWorkouts(workout : List<WorkoutEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout : WorkoutEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWorkout(workout: WorkoutEntity)

    @Delete
    suspend fun deleteWorkout(workout : WorkoutEntity)

    @Query("DELETE FROM Workouts")
    suspend fun deleteAllWorkouts()

}