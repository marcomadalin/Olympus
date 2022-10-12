package com.marcomadalin.olympus.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marcomadalin.olympus.data.database.entities.ExerciseEntity
import com.marcomadalin.olympus.data.database.entities.SetEntity

@Dao
interface ExerciseDAO {
    @Query("SELECT * FROM Exercises WHERE id = :id")
    suspend fun getExercise(id: Int) : ExerciseEntity

    @Query("SELECT * FROM Exercises")
    suspend fun getAllExercises() : List<ExerciseEntity>

    @Query("SELECT * FROM Exercises JOIN Sets On Exercises.id = Sets.exerciseId WHERE Exercises.id = :id")
    suspend fun getAllExerciseSets(id: Int) : List<SetEntity>

    @Query("DELETE FROM Exercises WHERE routineId = :id")
    suspend fun deleteAllRoutineExercises(id : Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllExercises(exercises : List<ExerciseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise : ExerciseEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExercise(exercise: ExerciseEntity)

    @Delete
    suspend fun deleteExercise(exercise : ExerciseEntity)

    @Query("DELETE FROM Exercises")
    suspend fun deleteAllExercises()


}