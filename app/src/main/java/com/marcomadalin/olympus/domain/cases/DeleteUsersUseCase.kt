package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.UserRepository
import javax.inject.Inject

class DeleteUsersUseCase @Inject constructor(private val userRepository: UserRepository){

    suspend operator fun invoke() = userRepository.deleteAllUsers()

}