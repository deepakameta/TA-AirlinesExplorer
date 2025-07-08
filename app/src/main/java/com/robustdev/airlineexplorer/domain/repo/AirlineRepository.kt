package com.robustdev.airlineexplorer.domain.repo

import com.robustdev.airlineexplorer.data.model.Airline
import kotlinx.coroutines.flow.Flow

interface AirlineRepository {
    fun getAllAirlines(): Flow<List<Airline>>
}