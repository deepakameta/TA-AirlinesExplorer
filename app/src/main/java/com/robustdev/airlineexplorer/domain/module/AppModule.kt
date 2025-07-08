package com.robustdev.airlineexplorer.domain.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.robustdev.airlineexplorer.data.local.AirlineDataSource
import com.robustdev.airlineexplorer.data.local.AirlineLocalDataSource
import com.robustdev.airlineexplorer.data.local.AirlineRepository
import com.robustdev.airlineexplorer.data.local.AirlineRepositoryImpl
import com.robustdev.airlineexplorer.data.remote.AirlineApi
import com.robustdev.airlineexplorer.domain.usecase.GetAirlinesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAirlineDataSource(
        @ApplicationContext context: Context
    ): AirlineDataSource = AirlineLocalDataSource(context)

    @Provides
    @Singleton
    fun provideAirlineRepository(
        dataSource: AirlineDataSource
    ): AirlineRepository = AirlineRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideGetAirlinesUseCase(
        repository: AirlineRepository
    ): GetAirlinesUseCase = GetAirlinesUseCase(repository)

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder().baseUrl("mock-base-url")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).build()
    }

    @Provides
    @Singleton
    fun provideAirlineApi(retrofit: Retrofit): AirlineApi {
        return retrofit.create(AirlineApi::class.java)
    }
}