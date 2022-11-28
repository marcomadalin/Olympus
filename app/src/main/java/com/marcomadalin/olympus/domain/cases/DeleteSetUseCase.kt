package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.SetRepository
import com.marcomadalin.olympus.domain.model.Set
import javax.inject.Inject

class DeleteSetUseCase @Inject constructor(private val setRepository : SetRepository){

    suspend operator fun invoke(set: Set) {
        setRepository.deleteSet(set)
    }

}