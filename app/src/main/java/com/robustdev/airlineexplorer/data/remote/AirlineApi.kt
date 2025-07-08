package com.robustdev.airlineexplorer.data.remote

import com.robustdev.airlineexplorer.data.model.AirlineDto
import retrofit2.http.GET

interface AirlineApi {
    /*
    * We can use this to fetch airlines data from the server
    * In this project this is not used but here implemented for example
    * */
    @GET("airlines")
    suspend fun getAirlines(): List<AirlineDto>
}