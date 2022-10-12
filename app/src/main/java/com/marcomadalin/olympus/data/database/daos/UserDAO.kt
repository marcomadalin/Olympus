package com.marcomadalin.olympus.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marcomadalin.olympus.data.database.entities.ExerciseDataEntity
import com.marcomadalin.olympus.data.database.entities.MeasureEntity
import com.marcomadalin.olympus.data.database.entities.RoutineEntity
import com.marcomadalin.olympus.data.database.entities.StatisticEntity
import com.marcomadalin.olympus.data.database.entities.UserEntity

@Dao
interface UserDAO {
    @Query("SELECT * FROM Users")
    suspend fun getAllUsers() : List<UserEntity>

    @Query("SELECT * FROM Users WHERE id = :id")
    suspend fun getUser(id :Int) : UserEntity

    @Query("SELECT * FROM Users JOIN Routines On Users.id = Routines.userId WHERE Users.id = :id")
    suspend fun getAllUserRoutines(id :Int) : List<RoutineEntity>

    @Query("SELECT * FROM Users JOIN ExercisesData On Users.id = ExercisesData.userId WHERE Users.id = :id")
    suspend fun getAllUserExercisesData(id :Int) : List<ExerciseDataEntity>

    @Query("SELECT * FROM Users JOIN ExercisesData On Users.id = ExercisesData.userId WHERE ExercisesData.userId in (:ids)")
    suspend fun getUserExercisesData(ids : Set<Int>) : List<ExerciseDataEntity>

    @Query("SELECT * FROM Users JOIN Measures On Users.id = Measures.userId WHERE Users.id = :id")
    suspend fun getAllUserMeasures(id :Int) : List<MeasureEntity>

    @Query("SELECT * FROM Users JOIN Measures On Users.id = Measures.userId WHERE Measures.userId in (:ids)")
    suspend fun getUserMeasures(ids : Set<Int>) : List<MeasureEntity>

    @Query("SELECT * FROM Users JOIN Statistics On Users.id = Statistics.userId WHERE Users.id = :id")
    suspend fun getAllUserStatistics(id :Int) : List<StatisticEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(user : List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : UserEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user : UserEntity)

    @Query("DELETE FROM Users")
    suspend fun deleteAllUsers()

}