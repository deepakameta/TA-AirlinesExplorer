package com.robustdev.airlineexplorer.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {

    fun <Api> buildApi(api: Class<Api>): Api {

        val client = OkHttpClient.Builder()
            .readTimeout(90, TimeUnit.SECONDS).build()

        return Retrofit.Builder()
            .baseUrl("write-base-url-here")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(api)
    }
}