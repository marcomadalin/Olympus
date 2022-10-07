package com.marcomadalin.olympus.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marcomadalin.olympus.data.database.converters.MapConverters
import com.marcomadalin.olympus.data.database.converters.SetConverters
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
    SetEntity::class, StatisticEntity::class, UserEntity::class, WorkoutEntity::class], version = 7)
@TypeConverters(MapConverters::class, SetConverters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(context,AppDatabase::class.java, "database").build()
            }
            return INSTANCE!!
        }
    }

    abstract fun exerciseDAO(): ExerciseDAO

    abstract fun exerciseDataDAO(): ExerciseDataDAO

    abstract fun measureDAO(): MeasureDAO

    abstract fun routineDAO(): RoutineDAO

    abstract fun setDAO(): SetDAO

    abstract fun statisticDAO(): StatisticDAO

    abstract fun userDao(): UserDAO

    abstract fun workoutDAO(): WorkoutDAO
}