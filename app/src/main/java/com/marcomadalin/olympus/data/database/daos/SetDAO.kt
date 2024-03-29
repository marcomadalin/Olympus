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
    suspend fun getAllSets() : List<SetEntity>

    @Query("SELECT * FROM Sets WHERE id = :id")
    suspend fun getSet(id: Long) : SetEntity

    @Query("DELETE FROM Sets WHERE exerciseId = :id")
    suspend fun deleteAllExerciseSets(id : Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSet(set : List<SetEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set : SetEntity) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSet(set: SetEntity)

    @Delete
    suspend fun deleteSet(set : SetEntity)

    @Query("DELETE FROM Sets")
    suspend fun deleteAllSets()

}