package com.robustdev.airlineexplorer.data.remote

import com.robustdev.airlineexplorer.data.model.AirlineDto
import com.robustdev.airlineexplorer.domain.repo.RemoteRepo
import javax.inject.Inject

class RemoteRepoImpl @Inject constructor(
    private val api: AirlineApiService,
) : RemoteRepo {

    override suspend fun getAirlinesList(): List<AirlineDto> {
        return api.getAirlines()

    }
}
