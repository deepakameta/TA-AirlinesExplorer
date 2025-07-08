package com.robustdev.airlineexplorer.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.robustdev.airlineexplorer.data.model.Airline
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

interface AirlineDataSource {
    fun getAirlines(): Flow<List<Airline>>
}

class AirlineLocalDataSource (private val context: Context) : AirlineDataSource {
    override fun getAirlines(): Flow<List<Airline>> = flow {
        val json = JsonUtils.loadJSONFromAsset(context, "airlines.json")
        val listType = object : TypeToken<List<Airline>>() {}.type
        val airlines: List<Airline> = Gson().fromJson(json, listType)
        emit(airlines)
    }
}

object JsonUtils {
    fun loadJSONFromAsset(context: Context, fileName: String): String? {
        return try {
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }
}