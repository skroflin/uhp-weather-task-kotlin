package com.example.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.domain.model.Weather

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
    val cityName: String,
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val icon: String,
    val timestamp: Long
)

fun WeatherEntity.toWeather(): Weather {
    return Weather(
        cityName = cityName,
        temperature = temperature,
        feelsLike = feelsLike,
        humidity = humidity,
        windSpeed = windSpeed,
        description = description,
        icon = icon,
        timestamp = timestamp
    )
}

fun Weather.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        cityName = cityName,
        temperature = temperature,
        feelsLike = feelsLike,
        humidity = humidity,
        windSpeed = windSpeed,
        description = description,
        icon = icon,
        timestamp = timestamp
    )
}