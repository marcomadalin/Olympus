package com.marcomadalin.olympus.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marcomadalin.olympus.data.database.entities.StatisticEntity

@Dao
interface StatisticDAO {
    @Query("SELECT * FROM Statistics")
    suspend fun getAllStatistics() : List<StatisticEntity>

    @Query("SELECT * FROM Statistics")
    suspend fun getStatistic() : StatisticEntity

    @Query("DELETE FROM Statistics WHERE userId = :id")
    suspend fun deleteAllUserStatistics(id : Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStatistic(statistic : List<StatisticEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatistic(statistic : StatisticEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateStatistic(statistic: StatisticEntity)

    @Delete
    suspend fun deleteStatistic(statistic : StatisticEntity)

    @Query("DELETE FROM Statistics")
    suspend fun deleteAllStatistics()

}