package com.marcomadalin.olympus.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marcomadalin.olympus.data.database.entities.SetEntity

@Dao
interface SetDAO {
    @Query("SELECT * FROM Sets")
    suspend fun getAllSets() : Array<SetEntity>

    @Query("DELETE FROM Sets WHERE exerciseId = :id")
    suspend fun deleteAllExerciseSets(id : Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set : SetEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSet(set: SetEntity)

    @Delete
    suspend fun deleteSet(set : SetEntity)

}