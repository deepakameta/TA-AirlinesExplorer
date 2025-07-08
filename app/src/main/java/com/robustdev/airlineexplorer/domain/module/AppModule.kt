package com.robustdev.airlineexplorer.domain.module

//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun provideAirlineDataSource(
//        @ApplicationContext context: Context
//    ): AirlineDataSource = AirlineLocalDataSource(context)
//
//    @Provides
//    @Singleton
//    fun provideAirlineRepository(
//        dataSource: AirlineDataSource
//    ): AirlineRepository = AirlineRepositoryImpl(dataSource)
//
//    @Provides
//    @Singleton
//    fun provideGetAirlinesUseCase(
//        repository: AirlineRepository
//    ): GetAirlinesUseCase = GetAirlinesUseCase(repository)

//    @Provides
//    @Singleton
//    fun provideRetrofit(gson: Gson): Retrofit {
//        return Retrofit.Builder().baseUrl("mock-base-url")
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideAirlineApi(retrofit: Retrofit): AirlineApi {
//        return retrofit.create(AirlineApi::class.java)
//    }
//}

//interface AirlineApi {
//    @GET("airlines")
//    suspend fun getAirlines(): List<Airline>
//}