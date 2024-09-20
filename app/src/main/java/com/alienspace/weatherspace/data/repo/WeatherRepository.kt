package com.alienspace.weatherspace.data.repo

import com.alienspace.weatherspace.data.local.Weather
import com.alienspace.weatherspace.ui.home.Coordinates
import com.alienspace.weatherspace.utils.Results
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {
    suspend fun fetchWeatherData(dummyCoordinates: Coordinates): Flow<Results<Weather>>
}