package com.marcomadalin.olympus.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marcomadalin.olympus.data.database.entities.MeasureEntity

@Dao
interface MeasureDAO {
    @Query("SELECT * FROM Measures")
    suspend fun getAllMeasures() : Array<MeasureEntity>

    @Query("DELETE FROM Measures WHERE userId = :id")
    suspend fun deleteAllUserMeasures(id : Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasure(measure : MeasureEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMeasure(measure: MeasureEntity)

    @Delete
    suspend fun deleteMeasure(measure : MeasureEntity)

    @Query("DELETE FROM Measures")
    suspend fun deleteAll()

}