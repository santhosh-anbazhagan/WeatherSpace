package com.alienspace.weatherspace.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination


data class BottomNavigationItems(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
) {
    fun bottomNavigationItems(): List<BottomNavigationItems> {
        return listOf(
            BottomNavigationItems(
                label = "Home",
                icon = Icons.Filled.Home,
            ),
            BottomNavigationItems(
                label = "Settings",
                icon = Icons.Filled.Settings,
            )
        )
    }
}
