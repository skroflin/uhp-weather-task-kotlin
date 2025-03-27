package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherByCity(cityName: String): Flow<Resource<Weather>>
    fun getLastSearchedWeather(): Flow<Weather?>
}
