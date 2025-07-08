package com.robustdev.airlineexplorer.data.local

import com.robustdev.airlineexplorer.data.model.Airline
import kotlinx.coroutines.flow.Flow

interface AirlineRepository {
    fun getAllAirlines(): Flow<List<Airline>>
}