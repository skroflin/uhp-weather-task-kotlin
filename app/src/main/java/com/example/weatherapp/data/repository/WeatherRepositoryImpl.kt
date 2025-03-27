package com.example.weatherapp.data.repository

import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.data.local.entity.toWeather
import com.example.weatherapp.data.local.entity.toWeatherEntity
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.remote.dto.toWeather
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    private val apiKey = "API_KEY"

    override fun getWeatherByCity(cityName: String): Flow<Resource<Weather>> = flow {
        emit(Resource.Loading())

        try {
            // Fetch fresh data from API
            val remoteWeather = api.getWeatherByCity(
                cityName = cityName,
                apiKey = apiKey
            ).toWeather()

            // Save to database
            dao.insertWeather(remoteWeather.toWeatherEntity())

            emit(Resource.Success(remoteWeather))
        } catch (e: HttpException) {
            emit(Resource.Error(
                message = "An error occurred: ${e.localizedMessage}"
            ))
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach server. Check your internet connection."
            ))
        } catch (e: Exception) {
            emit(Resource.Error(
                message = "An unexpected error occurred: ${e.message}"
            ))
        }
    }

    override fun getLastSearchedWeather(): Flow<Weather?> {
        return dao.getLastSearchedWeather().map { it?.toWeather() }
    }
}