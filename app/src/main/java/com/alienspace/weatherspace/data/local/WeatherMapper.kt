package com.alienspace.weatherspace.data.local

import com.alienspace.weatherspace.utils.Units
import com.alienspace.weatherspace.data.remote.CurrentWeatherResponse
import com.alienspace.weatherspace.data.remote.HourlyResponse
import com.alienspace.weatherspace.data.remote.WeatherResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

fun WeatherResponse.toCoreModel(): Weather = Weather(
    latitude = latitude,
    longitude = longitude,
    hourlyWeather = mapDatesToHourly(hourly),
    currentWeather = mapToCurrentWeather(currentWeather)
)

private fun mapToCurrentWeather(currentWeatherResponse: CurrentWeatherResponse): CurrentWeather =
    CurrentWeather(
        temperature = formatTemperatureValue(
            temperature = currentWeatherResponse.temperature, unit = Units.METRIC.value
        )
    )

private fun mapDatesToHourly(hourly: HourlyResponse): HourlyWeather {
    val weatherInfoList = mutableListOf<WeatherInfo>()
    for ((index, time) in hourly.times.withIndex()) {
        val temperature = hourly.temperatures[index]
        val formattedTemperature =
            formatTemperatureValue(temperature = temperature, unit = Units.METRIC.value)
        val formattedTime = formattedDateToHourlyTime(time)
        val weatherInfo = WeatherInfo(time = formattedTime, temperature = formattedTemperature)
        weatherInfoList.add(weatherInfo)
    }
    return HourlyWeather(weatherInfo = weatherInfoList)
}

fun formattedDateToHourlyTime(time: String): String {
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
    val outputFormat = DateTimeFormatter.ofPattern("HH:mm")

    val dateTime: LocalDateTime = LocalDateTime.parse(time, inputFormat)
    return dateTime.format(outputFormat)
}

private fun formatTemperatureValue(temperature: Float, unit: String): String =
    "${temperature.roundToInt()}${getUnitSymbol(unit)}"

private fun getUnitSymbol(unit: String) = when (unit) {
    Units.METRIC.value -> Units.METRIC.tempLabel
    Units.IMPERIAL.value -> Units.IMPERIAL.tempLabel
    else -> "N/A"
}
