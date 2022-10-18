package com.marcomadalin.olympus.data.repositories

import com.marcomadalin.olympus.data.database.daos.UserDAO
import com.marcomadalin.olympus.data.database.entities.toData
import com.marcomadalin.olympus.domain.model.ExerciseData
import com.marcomadalin.olympus.domain.model.Measure
import com.marcomadalin.olympus.domain.model.Routine
import com.marcomadalin.olympus.domain.model.Statistic
import com.marcomadalin.olympus.domain.model.User
import com.marcomadalin.olympus.domain.model.toDomain
import javax.inject.Inject


class UserRepository @Inject constructor(private val userDAO: UserDAO) {

    suspend fun getAllUsers() : List<User> {
        return userDAO.getAllUsers().map { it.toDomain() }
    }

    suspend fun getUser() : User {
        return userDAO.getUser(1).toDomain()
    }

    suspend fun getAllUserRoutines(id :Long) : List<Routine> {
        return userDAO.getAllUserRoutines(id).map { it.toDomain() }
    }

    suspend fun getAllUserExercisesData(id :Long) : List<ExerciseData> {
        return userDAO.getAllUserExercisesData(id).map { it.toDomain() }
    }

    suspend fun getUserExercisesData(ids : Set<Long>) : List<ExerciseData> {
        return userDAO.getUserExercisesData(ids).map { it.toDomain() }
    }

    suspend fun getAllUserMeasures(id :Long) : List<Measure> {
        return userDAO.getAllUserMeasures(id).map { it.toDomain() }
    }

    suspend fun getUserMeasures(ids : Set<Long>) : List<Measure> {
        return userDAO.getUserMeasures(ids).map { it.toDomain() }
    }

    suspend fun getAllUserStatistics(id :Long) : List<Statistic> {
        return userDAO.getAllUserStatistics(id).map { it.toDomain() }
    }

    suspend fun saveAllUsers(users : List<User>) {
        userDAO.insertAllUsers(users.map { it.toData() })
    }

    suspend fun saveUser(user : User) {
        userDAO.insertUser(user.toData())
    }

    suspend fun updateUser(user: User) {
        userDAO.updateUser(user.toData())
    }

    suspend fun deleteUser(user : User) {
        userDAO.deleteUser(user.toData())
    }

    suspend fun deleteAllUsers() {
        userDAO.deleteAllUsers()
    }
}