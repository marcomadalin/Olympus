package com.marcomadalin.olympus.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marcomadalin.olympus.data.database.entities.ExerciseEntity
import com.marcomadalin.olympus.data.database.entities.RoutineEntity

@Dao
interface RoutineDAO {
    @Query("SELECT * FROM Routines")
    suspend fun getAllRoutines() : Array<RoutineEntity>

    @Query("SELECT * FROM Routines JOIN Exercises On Routines.id = Exercises.routineId")
    suspend fun getAllRoutineExercises() : Array<ExerciseEntity>

    @Query("DELETE FROM Routines WHERE userId = :id")
    suspend fun deleteAllUserRoutines(id : Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine : RoutineEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRoutine(routine: RoutineEntity)

    @Delete
    suspend fun deleteRoutine(routine : RoutineEntity)

}