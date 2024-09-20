package com.alienspace.weatherspace.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient

@Composable
fun BottomNavigation(
    fusedLocationClient: FusedLocationProviderClient,
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val navController = rememberNavController()


    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.inverseOnSurface
            ) {
                BottomNavigationItems().bottomNavigationItems()
                    .forEachIndexed { index, bottomNavigationItems ->
                        NavigationBarItem(
                            alwaysShowLabel = true,
                            label = { Text(text = bottomNavigationItems.label) },
                            selected = index == selectedIndex,
                            onClick = {
                                selectedIndex = index
                                when (selectedIndex) {
                                    0 -> navController.navigate(
                                        HomeScreenNav
                                    ) {
                                        popUpTo(navController.graph.findStartDestination().id)
                                        launchSingleTop = true
                                        restoreState = true
                                    }

                                    1 -> navController.navigate(SettingsScreenNav)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = bottomNavigationItems.icon,
                                    contentDescription = "",
                                    tint = if (index == selectedIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            })
                    }

            }
        }
    ) { paddingValues ->

        WeatherSpaceNavigation(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            fusedLocationProviderClient = fusedLocationClient
        )

    }
}