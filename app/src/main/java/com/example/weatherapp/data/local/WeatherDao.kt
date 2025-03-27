package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.local.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather WHERE cityName = :cityName")
    fun getWeatherByCityName(cityName: String): Flow<WeatherEntity?>

    @Query("SELECT * FROM weather ORDER BY timestamp DESC LIMIT 1")
    fun getLastSearchedWeather(): Flow<WeatherEntity?>
}
