package com.alienspace.weatherspace.ui.home

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient

sealed class HomeUiEvent {

    data class LoadWeatherData(val fusedLocationProviderClient: FusedLocationProviderClient) : HomeUiEvent()
    data object DisplayCityName : HomeUiEvent()
    data class GetLatLang(val fusedLocationProviderClient: FusedLocationProviderClient) :
        HomeUiEvent()

    data class UpdateHomeContext(val context: Context) : HomeUiEvent()
}