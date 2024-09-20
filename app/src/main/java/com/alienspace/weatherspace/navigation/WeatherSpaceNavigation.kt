package com.alienspace.weatherspace.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alienspace.weatherspace.ui.home.HomeScreen
import com.alienspace.weatherspace.ui.settings.SettingsScreen
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenNav

@Serializable
data object SettingsScreenNav

@Composable
fun WeatherSpaceNavigation(
    navController: NavHostController,
    modifier: Modifier,
    fusedLocationProviderClient: FusedLocationProviderClient,
) {

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = HomeScreenNav
    ) {

        composable<HomeScreenNav> {
            HomeScreen(fusedLocationProviderClient = fusedLocationProviderClient)
        }
        composable<SettingsScreenNav> {
            SettingsScreen()
        }
    }
}