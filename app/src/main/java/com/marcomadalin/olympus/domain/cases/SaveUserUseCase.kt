package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.UserRepository
import com.marcomadalin.olympus.domain.model.User
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(private val userRepository: UserRepository){

    suspend operator fun invoke(user: User) = userRepository.saveUser(user)

}