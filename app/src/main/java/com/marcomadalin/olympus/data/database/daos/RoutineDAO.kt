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
    suspend fun getAllRoutines() : List<RoutineEntity>

    @Query("SELECT * FROM Routines WHERE id = :id")
    suspend fun getRoutine(id: Long) : RoutineEntity

    @Query("SELECT * FROM Routines JOIN Exercises On Routines.id = Exercises.routineId " +
            "WHERE Routines.id = :id")
    suspend fun getAllRoutineExercises(id: Long) : List<ExerciseEntity>

    @Query("DELETE FROM Routines WHERE userId = :id")
    suspend fun deleteAllUserRoutines(id : Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRoutines(routine : List<RoutineEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine : RoutineEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRoutine(routine: RoutineEntity)

    @Delete
    suspend fun deleteRoutine(routine : RoutineEntity)

    @Query("DELETE FROM Routines")
    suspend fun deleteAllRoutines()

}