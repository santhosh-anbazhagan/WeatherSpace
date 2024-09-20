package com.alienspace.weatherspace.data.repo

import com.alienspace.weatherspace.data.local.Weather
import com.alienspace.weatherspace.data.local.toCoreModel
import com.alienspace.weatherspace.data.remote.OpenMeteoServiceAPI
import com.alienspace.weatherspace.ui.home.Coordinates
import com.alienspace.weatherspace.utils.ErrorType
import com.alienspace.weatherspace.utils.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val openMeteoServiceAPI: OpenMeteoServiceAPI,
) : WeatherRepository {
    override suspend fun fetchWeatherData(coordinates: Coordinates): Flow<Results<Weather>> {
        Timber.d("Fetching Response...1")

        return flow {
            try {
                Timber.d("Fetching Response...")
                val response =
                    openMeteoServiceAPI.getWeatherData(
                        latitude = coordinates.latitude,
                        longitude = coordinates.longitude,
                    )

                if (response.isSuccessful && response.body() != null) {
                    Timber.d("Weather Response : ${response.body()} ")
                    val weatherData = response.body()!!.toCoreModel()
                    emit(Results.Success(weatherData))
                } else {
                    val errorType = mapResponseCodeToErrorType(response.code())
                    emit(Results.Error(errorType))

                }
            } catch (e: Exception) {
                val errorType = when (e) {
                    is IOException -> ErrorType.IO_CONNECTION
                    else -> {
                        ErrorType.GENERIC
                    }
                }
                Timber.e("Exception : ${e.printStackTrace()}")
                emit(Results.Error(errorType))
            }
        }
    }

    private fun mapResponseCodeToErrorType(errorCode: Int): ErrorType {
        val errorType = when (errorCode) {
            HTTP_UNAUTHORIZED -> ErrorType.UNAUTHORIZED
            in 400..499 -> ErrorType.CLIENT
            in 500..600 -> ErrorType.SERVER
            else -> ErrorType.GENERIC
        }
        return errorType
    }
}