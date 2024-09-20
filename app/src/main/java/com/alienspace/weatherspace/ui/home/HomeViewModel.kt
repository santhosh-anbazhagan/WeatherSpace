package com.alienspace.weatherspace.ui.home

import android.annotation.SuppressLint
import android.location.Geocoder
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alienspace.weatherspace.R
import com.alienspace.weatherspace.data.local.Weather
import com.alienspace.weatherspace.data.repo.WeatherRepository
import com.alienspace.weatherspace.utils.ErrorType
import com.alienspace.weatherspace.utils.Results
import com.alienspace.weatherspace.utils.isLocEnabled
import com.alienspace.weatherspace.utils.isNetworkAvailable
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: WeatherRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenUiState(isLoading = true))
    val state = _state.asStateFlow()


    @SuppressLint("MissingPermission")
    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.UpdateHomeContext -> {
                _state.update {
                    it.copy(
                        homeContext = event.context
                    )
                }
            }

            HomeUiEvent.DisplayCityName -> {

            }

            is HomeUiEvent.LoadWeatherData -> {
                viewModelScope.launch {
                    getWeatherData()
                    getLatLang(event.fusedLocationProviderClient)
                }
            }

            is HomeUiEvent.GetLatLang -> {
                getLatLang(event.fusedLocationProviderClient)
            }
        }
    }

    private fun processResult(result: Results<Weather>) {
        when (result) {
            is Results.Error -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = mapErrorTypeToResource(result.errorType)
                    )
                }
            }

            is Results.Success -> {
                _state.update {
                    it.copy(
                        weather = result.data,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            repo.fetchWeatherData(state.value.coordinates).collect { result ->
                processResult(result)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLatLang(
        fusedLocationProviderClient: FusedLocationProviderClient,
        priority: Boolean = true,
    ) {
        if (state.value.homeContext?.isLocEnabled()!! && state.value.homeContext?.isNetworkAvailable()!!) {

            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val accuracy =
                if (priority) Priority.PRIORITY_HIGH_ACCURACY else Priority.PRIORITY_BALANCED_POWER_ACCURACY
            fusedLocationProviderClient.getCurrentLocation(
                accuracy,
                CancellationTokenSource().token
            ).addOnSuccessListener { location ->
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            coordinates = Coordinates(location.latitude, location.longitude)
                        ).also {
                            Timber.d("Latitude : ${location.latitude}")
                            Timber.d("Longitude : ${location.longitude}")

                        }
                    }
                    if (state.value.homeContext != null) {
                        getCityName(location.latitude, location.longitude)
                    } else {
                        Timber.e("Home Context is Null !")
                    }

                    //fetching weather data
                    getWeatherData()
                }

            }

        } else {
            Toast.makeText(
                state.value.homeContext,
                "Please check Location & Internet Connection!",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun getCityName(latitude: Double, longitude: Double) {
        val geoCoder = Geocoder(state.value.homeContext!!, Locale.getDefault())
        val address = geoCoder.getFromLocation(
            latitude,
            longitude, 3
        )
        Timber.d(
            "LocationCheck", "Latitude: ${state.value.coordinates.latitude}, " +
                    "Longitude: ${state.value.coordinates.longitude}"
        )
        Timber.d("getCity Name : $address")
        address?.let { address ->
            if (address.size != 0) {
                _state.update {
                    it.copy(
                        cityName = address[0].locality
                    )
                }
            }
        }

    }


    private fun mapErrorTypeToResource(errorType: ErrorType): Int = when (errorType) {
        ErrorType.GENERIC -> R.string.error_generic
        ErrorType.IO_CONNECTION -> R.string.error_client
        ErrorType.UNAUTHORIZED -> R.string.error_unauthorized
        ErrorType.CLIENT -> R.string.error_client
        ErrorType.SERVER -> R.string.error_server
    }

}