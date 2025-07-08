package com.robustdev.airlineexplorer.domain.usecase

import com.robustdev.airlineexplorer.data.local.AirlineRepository
import com.robustdev.airlineexplorer.data.model.Airline
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAirlinesUseCase @Inject constructor(
    private val repository: AirlineRepository
) {
    operator fun invoke(): Flow<List<Airline>> = repository.getAllAirlines()
}