package com.example.weatherapp.data.remote.dto

import com.example.weatherapp.domain.model.Weather
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val main: Main,
    val weather: List<WeatherInfo>,
    val wind: Wind,
    val name: String
)

data class Main(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: Int
)

data class WeatherInfo(
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double
)

fun WeatherResponse.toWeather(): Weather {
    return Weather(
        cityName = name,
        temperature = main.temp,
        feelsLike = main.feelsLike,
        humidity = main.humidity,
        windSpeed = wind.speed,
        description = weather.firstOrNull()?.description ?: "",
        icon = weather.firstOrNull()?.icon ?: ""
    )
}
