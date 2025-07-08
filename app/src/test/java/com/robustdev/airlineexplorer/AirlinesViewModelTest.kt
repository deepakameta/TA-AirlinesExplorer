package com.robustdev.airlineexplorer

import com.robustdev.airlineexplorer.data.model.Airline
import com.robustdev.airlineexplorer.domain.usecase.GetAirlinesUseCase
import com.robustdev.airlineexplorer.ui.viewmodel.AirlinesViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AirlinesViewModelTest {

    private lateinit var getAirlinesUseCase: GetAirlinesUseCase
    private lateinit var viewModel: AirlinesViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getAirlinesUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchAirlines updates airlines and stops loading`() = runTest {

        val dummyAirlines = listOf(
            Airline(
                "Air India",
                "India",
                "Delhi",
                100,
                "https://airindia.com",
                "logo.png",
            )
        )

        coEvery { getAirlinesUseCase.invoke() } returns flow { emit(dummyAirlines) }

        viewModel = AirlinesViewModel(getAirlinesUseCase)
        advanceUntilIdle()

        assert(viewModel.airlines.value == dummyAirlines)
        assert(!viewModel.loading.value)
        assert(viewModel.error.value == null)
    }

    @Test
    fun `fetchAirlines sets error on exception`() = runTest {
        val errorMessage = "Something went wrong"
        coEvery { getAirlinesUseCase.invoke() } throws RuntimeException(errorMessage)

        viewModel = AirlinesViewModel(getAirlinesUseCase)
        advanceUntilIdle()

        assert(viewModel.airlines.value.isEmpty())
        assert(!viewModel.loading.value)
        assert(viewModel.error.value == errorMessage)
    }
}