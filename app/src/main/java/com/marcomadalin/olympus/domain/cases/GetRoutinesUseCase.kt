package com.marcomadalin.olympus.domain.cases

import com.marcomadalin.olympus.data.repositories.RoutineRepository
import com.marcomadalin.olympus.domain.model.Routine
import javax.inject.Inject

class GetRoutinesUseCase @Inject constructor(private val routineRepository: RoutineRepository){

    suspend operator fun invoke():List<Routine> = routineRepository.getAllRoutines()

}