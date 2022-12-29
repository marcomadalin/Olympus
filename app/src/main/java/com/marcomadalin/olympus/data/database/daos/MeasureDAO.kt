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
    suspend fun getAllMeasures() : List<MeasureEntity>

    @Query("SELECT * FROM Measures WHERE part like :part ORDER BY date")
    suspend fun getMeasureValues(part : String) : List<MeasureEntity>

    @Query("SELECT * FROM Measures WHERE part like :part ORDER BY date LIMIT 2")
    suspend fun getMeasures(part : String) : List<MeasureEntity>

    @Query("SELECT * FROM Measures WHERE id = :id")
    suspend fun getMeasure(id: Long) : MeasureEntity

    @Query("DELETE FROM Measures WHERE userId = :id")
    suspend fun deleteAllUserMeasures(id : Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMeasures(measure : List<MeasureEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasure(measure : MeasureEntity) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMeasure(measure: MeasureEntity)

    @Delete
    suspend fun deleteMeasure(measure : MeasureEntity)

    @Query("DELETE FROM Measures")
    suspend fun deleteAllMeasures()

}