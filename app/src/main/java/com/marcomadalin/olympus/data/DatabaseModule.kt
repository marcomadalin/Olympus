package com.marcomadalin.olympus.data

import android.content.Context
import androidx.room.Room
import com.marcomadalin.olympus.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideExerciseDAO(db: AppDatabase) = db.exerciseDAO()

    @Singleton
    @Provides
    fun provideExerciseDataDAO(db: AppDatabase) = db.exerciseDataDAO()

    @Singleton
    @Provides
    fun provideMeasureDAO(db: AppDatabase) = db.measureDAO()

    @Singleton
    @Provides
    fun provideRoutineDAO(db: AppDatabase) = db.routineDAO()

    @Singleton
    @Provides
    fun provideSetDAO(db: AppDatabase) = db.setDAO()

    @Singleton
    @Provides
    fun provideStatisticDAO(db: AppDatabase) = db.statisticDAO()

    @Singleton
    @Provides
    fun provideUserDAO(db: AppDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideWorkoutDAO(db: AppDatabase) = db.workoutDAO()
}