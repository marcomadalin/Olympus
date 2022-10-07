package com.marcomadalin.olympus.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marcomadalin.olympus.data.database.entities.ExerciseDataEntity
import com.marcomadalin.olympus.data.database.entities.ExerciseEntity

@Dao
interface ExerciseDataDAO {
    @Query("SELECT * FROM ExercisesData")
    suspend fun getAllExercisesData() : Array<ExerciseDataEntity>

    @Query("SELECT * FROM ExercisesData JOIN Exercises On ExercisesData.id = Exercises.exerciseDataId")
    suspend fun getAllExerciseDataExercises() : Array<ExerciseEntity>

    @Query("DELETE FROM ExercisesData WHERE userId = :id")
    suspend fun deleteAllUserExerciseData(id : Int)

    //TODO query to check if routine has exercise in case we delete one ExerciseData so we can propagate deletes without problems

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseData(exerciseData : ExerciseDataEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExerciseData(exerciseData: ExerciseDataEntity)

    @Delete
    suspend fun deleteExerciseData(exerciseData : ExerciseDataEntity)

    @Query("DELETE FROM ExercisesData")
    suspend fun deleteAll()

}