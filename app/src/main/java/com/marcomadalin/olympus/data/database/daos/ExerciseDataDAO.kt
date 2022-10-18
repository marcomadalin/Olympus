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
    suspend fun getAllExercisesData() : List<ExerciseDataEntity>

    @Query("SELECT * FROM ExercisesData WHERE id = :id")
    suspend fun getExercisesData(id: Long) : ExerciseDataEntity

    @Query("SELECT * FROM ExercisesData JOIN Exercises On ExercisesData.id = Exercises.exerciseDataId " +
            "WHERE ExercisesData.id = :id")
    suspend fun getAllExerciseDataExercises(id: Long) : List<ExerciseEntity>

    @Query("DELETE FROM ExercisesData WHERE userId = :id")
    suspend fun deleteAllUserExerciseData(id : Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllExerciseData(exerciseData : List<ExerciseDataEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseData(exerciseData : ExerciseDataEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExerciseData(exerciseData: ExerciseDataEntity)

    @Delete
    suspend fun deleteExerciseData(exerciseData : ExerciseDataEntity)

    @Query("DELETE FROM ExercisesData")
    suspend fun deleteAllExercisesData()

}