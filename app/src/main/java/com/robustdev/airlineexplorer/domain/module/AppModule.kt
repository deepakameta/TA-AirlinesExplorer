package com.robustdev.airlineexplorer.domain.module

import android.content.Context
import com.robustdev.airlineexplorer.data.datasource.AirlineDataSource
import com.robustdev.airlineexplorer.data.datasource.AirlineLocalDataSource
import com.robustdev.airlineexplorer.data.local.AirlineRepositoryImpl
import com.robustdev.airlineexplorer.data.remote.AirlineApiService
import com.robustdev.airlineexplorer.data.remote.RemoteRepoImpl
import com.robustdev.airlineexplorer.domain.repo.AirlineRepository
import com.robustdev.airlineexplorer.domain.repo.RemoteRepo
import com.robustdev.airlineexplorer.domain.usecase.GetAirlinesUseCase
import com.robustdev.airlineexplorer.utils.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideApi(remoteDataSource: RemoteDataSource): AirlineApiService {
        return remoteDataSource.buildApi(AirlineApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteRepo(api: AirlineApiService): RemoteRepo {
        return RemoteRepoImpl(api)
    }
}