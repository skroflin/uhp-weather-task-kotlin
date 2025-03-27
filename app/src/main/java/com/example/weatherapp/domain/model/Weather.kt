package com.example.weatherapp.domain.model

data class Weather(
    val cityName: String,
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val icon: String,
    val timestamp: Long = System.currentTimeMillis()
)
