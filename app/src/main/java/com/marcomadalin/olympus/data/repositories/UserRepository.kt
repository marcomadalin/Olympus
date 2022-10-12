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

    suspend fun getUser(id: Int) : User {
        return userDAO.getUser(id).toDomain()
    }

    suspend fun getAllUserRoutines(id :Int) : List<Routine> {
        return userDAO.getAllUserRoutines(id).map { it.toDomain() }
    }

    suspend fun getAllUserExercisesData(id :Int) : List<ExerciseData> {
        return userDAO.getAllUserExercisesData(id).map { it.toDomain() }
    }

    suspend fun getUserExercisesData(ids : Set<Int>) : List<ExerciseData> {
        return userDAO.getUserExercisesData(ids).map { it.toDomain() }
    }

    suspend fun getAllUserMeasures(id :Int) : List<Measure> {
        return userDAO.getAllUserMeasures(id).map { it.toDomain() }
    }

    suspend fun getUserMeasures(ids : Set<Int>) : List<Measure> {
        return userDAO.getUserMeasures(ids).map { it.toDomain() }
    }

    suspend fun getAllUserStatistics(id :Int) : List<Statistic> {
        return userDAO.getAllUserStatistics(id).map { it.toDomain() }
    }

    suspend fun insertAllUsers(users : List<User>) {
        userDAO.insertAllUsers(users.map { it.toData() })
    }

    suspend fun insertUser(user : User) {
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