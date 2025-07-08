package com.robustdev.airlineexplorer

import com.robustdev.airlineexplorer.data.local.AirlineRepository
import com.robustdev.airlineexplorer.data.model.Airline
import com.robustdev.airlineexplorer.domain.usecase.GetAirlinesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetAirlinesUseCaseTest {

    private lateinit var repository: AirlineRepository
    private lateinit var useCase: GetAirlinesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetAirlinesUseCase(repository)
    }

    @Test
    fun `invoke should return airline list from repository`() = runTest {
        val airlines = listOf(
            Airline("Air India", "India", "Delhi", 100, "https://airindia.com", "logo.png"),
            Airline("EasyJet", "UK", "London", 200, "https://easyjet.com", "logo2.png")
        )

        every { repository.getAllAirlines() } returns flowOf(airlines)

        val result = useCase().toList()

        assert(result.size == 1)
        assert(result[0] == airlines)
        verify(exactly = 1) { repository.getAllAirlines() }
    }
}