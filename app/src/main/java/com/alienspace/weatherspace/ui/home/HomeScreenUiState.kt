package com.alienspace.weatherspace.ui.home

import android.content.Context
import androidx.annotation.StringRes
import com.alienspace.weatherspace.data.local.Weather

data class HomeScreenUiState(
    val isLoading: Boolean = false,
    val cityName: String = "Heaven",
    val weather: Weather? = null,
    @StringRes val error: Int? = null,
    val homeContext: Context? = null,
    val coordinates: Coordinates = Coordinates(0.00, 0.00),
)

data class Coordinates(val latitude: Double, val longitude: Double)