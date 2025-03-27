package com.example.weatherapp.presentation.weather

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> = _state

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    init {
        // Start with a default city
        getWeatherForCity("London")
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun searchWeather() {
        val cityName = searchQuery.value.trim()
        if (cityName.isNotEmpty()) {
            getWeatherForCity(cityName)
        }
    }

    private fun getWeatherForCity(cityName: String) {
        repository.getWeatherByCity(cityName).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = WeatherState(
                        weather = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = WeatherState(
                        weather = result.data,
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    _state.value = WeatherState(
                        weather = result.data,
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
