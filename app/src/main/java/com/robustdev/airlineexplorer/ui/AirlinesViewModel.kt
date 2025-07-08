package com.robustdev.airlineexplorer.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robustdev.airlineexplorer.MyApp
import com.robustdev.airlineexplorer.data.local.AirlineLocalDataSource
import com.robustdev.airlineexplorer.data.local.AirlineRepositoryImpl
import com.robustdev.airlineexplorer.data.model.Airline
import com.robustdev.airlineexplorer.domain.usecase.GetAirlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class AirlinesViewModel : ViewModel() {

    private val getAirlinesUseCase: GetAirlinesUseCase = GetAirlinesUseCase(
        AirlineRepositoryImpl(AirlineLocalDataSource(MyApp.instance))
    )

    private val _airlines = MutableStateFlow<List<Airline>>(emptyList())
    val airlines: StateFlow<List<Airline>> = _airlines

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchAirlines()
    }

    private fun fetchAirlines() {
        _loading.value = true
        viewModelScope.launch {
            try {
                getAirlinesUseCase().collectLatest {
                    _airlines.value = it
                    _loading.value = false
                }
            } catch (e: Exception) {
                _loading.value = false
                _error.value = e.message
            }
        }
    }
}