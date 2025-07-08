package com.robustdev.airlineexplorer.data.datasource

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.robustdev.airlineexplorer.data.model.Airline
import com.robustdev.airlineexplorer.data.model.AirlineDto
import com.robustdev.airlineexplorer.data.model.toAirlines
import com.robustdev.airlineexplorer.utils.JsonUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface AirlineDataSource {
    fun getAirlines(): Flow<List<Airline>>
}

class AirlineLocalDataSource(private val context: Context) : AirlineDataSource {
    /*
    I did not get dynamic API so using static data.
    Here we are loading static data only form the json file created within the project.
    */
    override fun getAirlines(): Flow<List<Airline>> = flow {
        val json = JsonUtils.loadJSONFromAsset(context, "airlines.json")
        val listType = object : TypeToken<List<AirlineDto>>() {}.type
        val airlines: List<AirlineDto> = Gson().fromJson(json, listType)
        emit(airlines.toAirlines())
    }
}