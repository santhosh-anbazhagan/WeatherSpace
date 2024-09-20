package com.alienspace.weatherspace.ui

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.alienspace.weatherspace.navigation.BottomNavigation
import com.alienspace.weatherspace.ui.theme.WeatherSpaceTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        var allPermissionsGranted by mutableStateOf<Boolean>(false)

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions(),
                callback = { permission ->
                    allPermissionsGranted = permission.values.all { it }
                    permission.values.forEach {
                        Timber.d(
                            "permission : ${it}"
                        )
                    }
                })

        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        setContent {
            WeatherSpaceTheme {
                if (allPermissionsGranted) {

                    val isGPSEnabled =
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    if (!isGPSEnabled) {
                        Toast.makeText(
                            LocalContext.current,
                            "Please enable location service",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    BottomNavigation(
                        fusedLocationClient = fusedLocationProviderClient
                    )
                } else {
                    Toast.makeText(
                        LocalContext.current,
                        "Necessary Location Permissions Not Granted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
