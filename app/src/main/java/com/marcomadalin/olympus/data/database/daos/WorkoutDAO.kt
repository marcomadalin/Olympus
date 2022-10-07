package com.marcomadalin.olympus.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marcomadalin.olympus.data.database.entities.ExerciseEntity
import com.marcomadalin.olympus.data.database.entities.RoutineEntity
import com.marcomadalin.olympus.data.database.entities.WorkoutEntity

@Dao
interface WorkoutDAO {
    @Query("SELECT * FROM Workouts")
    suspend fun getAllWorkouts() : Array<WorkoutEntity>

    @Query("SELECT * FROM Workouts JOIN Exercises On Workouts.id = Exercises.routineId")
    suspend fun getAllRoutineExercises() : Array<ExerciseEntity>

    @Query("DELETE FROM Workouts WHERE userId = :id")
    suspend fun deleteAllUserWorkouts(id : Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout : RoutineEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWorkout(workout: RoutineEntity)

    @Delete
    suspend fun deleteWorkout(workout : RoutineEntity)

    @Query("DELETE FROM Workouts")
    suspend fun deleteAll()

}