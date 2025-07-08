package com.robustdev.airlineexplorer.domain.repo

import com.robustdev.airlineexplorer.data.model.AirlineDto

interface RemoteRepo {

    suspend fun getAirlinesList(): List<AirlineDto>
}