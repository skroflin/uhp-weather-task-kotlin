package com.example.weatherapp.presentation.weather

import com.example.weatherapp.domain.model.Weather

data class WeatherState(
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)
