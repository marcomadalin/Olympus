package com.marcomadalin.olympus.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marcomadalin.olympus.data.database.converters.IntSetConverters
import com.marcomadalin.olympus.data.database.converters.ListSetConverters
import com.marcomadalin.olympus.data.database.converters.MapConverters
import com.marcomadalin.olympus.data.database.converters.StringSetConverters
import com.marcomadalin.olympus.data.database.daos.ExerciseDAO
import com.marcomadalin.olympus.data.database.daos.ExerciseDataDAO
import com.marcomadalin.olympus.data.database.daos.MeasureDAO
import com.marcomadalin.olympus.data.database.daos.RoutineDAO
import com.marcomadalin.olympus.data.database.daos.SetDAO
import com.marcomadalin.olympus.data.database.daos.StatisticDAO
import com.marcomadalin.olympus.data.database.daos.UserDAO
import com.marcomadalin.olympus.data.database.daos.WorkoutDAO
import com.marcomadalin.olympus.data.database.entities.ExerciseDataEntity
import com.marcomadalin.olympus.data.database.entities.ExerciseEntity
import com.marcomadalin.olympus.data.database.entities.MeasureEntity
import com.marcomadalin.olympus.data.database.entities.RoutineEntity
import com.marcomadalin.olympus.data.database.entities.SetEntity
import com.marcomadalin.olympus.data.database.entities.StatisticEntity
import com.marcomadalin.olympus.data.database.entities.UserEntity
import com.marcomadalin.olympus.data.database.entities.WorkoutEntity

@Database(entities = [ExerciseEntity::class, ExerciseDataEntity::class, MeasureEntity::class, RoutineEntity::class,
    SetEntity::class, StatisticEntity::class, UserEntity::class, WorkoutEntity::class], version = 34)
@TypeConverters(MapConverters::class, StringSetConverters::class, IntSetConverters::class, ListSetConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseDAO(): ExerciseDAO

    abstract fun exerciseDataDAO(): ExerciseDataDAO

    abstract fun measureDAO(): MeasureDAO

    abstract fun routineDAO(): RoutineDAO

    abstract fun setDAO(): SetDAO

    abstract fun statisticDAO(): StatisticDAO

    abstract fun userDao(): UserDAO

    abstract fun workoutDAO(): WorkoutDAO
}