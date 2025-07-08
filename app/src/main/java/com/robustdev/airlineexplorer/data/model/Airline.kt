package com.robustdev.airlineexplorer.data.model

data class Airline(
    val name: String,
    val country: String,
    val headquarters: String,
    val fleetSize: Int,
    val website: String,
    val logoUrl: String
)

data class AirlineDto(
    val id: String,
    val name: String,
    val country: String,
    val headquarters: String,
    val fleet_size: Int,
    val website: String,
    val logo_url: String
)

fun AirlineDto.toAirline(): Airline {
    return Airline(
        name = name,
        country = country,
        headquarters = headquarters,
        fleetSize = fleet_size,
        website = website,
        logoUrl = logo_url
    )
}

fun List<AirlineDto>.toAirlines(): List<Airline> {
    return this.map { it.toAirline() }
}