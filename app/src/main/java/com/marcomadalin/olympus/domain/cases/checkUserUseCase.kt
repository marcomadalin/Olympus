package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.UserRepository
import com.marcomadalin.olympus.domain.model.User
import javax.inject.Inject

class checkUserUseCase @Inject constructor(private val userRepository: UserRepository){

    operator fun invoke():Boolean = userRepository.checkUser()

}