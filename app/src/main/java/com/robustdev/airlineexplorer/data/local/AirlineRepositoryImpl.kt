package com.robustdev.airlineexplorer.data.local

import com.robustdev.airlineexplorer.data.model.Airline
import kotlinx.coroutines.flow.Flow

class AirlineRepositoryImpl constructor(
    private val dataSource: AirlineDataSource
) : AirlineRepository {
    override fun getAllAirlines(): Flow<List<Airline>> = dataSource.getAirlines()
}