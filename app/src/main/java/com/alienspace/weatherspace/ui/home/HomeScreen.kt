package com.alienspace.weatherspace.ui.home

import android.content.Context
import android.location.LocationManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import com.alienspace.weatherspace.R
import com.alienspace.weatherspace.ui.theme.Sizing
import com.google.android.gms.location.FusedLocationProviderClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    fusedLocationProviderClient: FusedLocationProviderClient,
) {

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    //Update Context to HomeViewModel
    viewModel.onEvent(HomeUiEvent.UpdateHomeContext(context))

    Scaffold(
        modifier = Modifier
        .background(MaterialTheme.colorScheme.inverseOnSurface),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(HomeUiEvent.GetLatLang(fusedLocationProviderClient = fusedLocationProviderClient))
                    }) {
                        Icon(
                            imageVector = Icons.Filled.MyLocation,
                            contentDescription = "Refresh",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.inverseOnSurface)
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(paddingValues)
                .padding(horizontal = Sizing.medium),
        ) {
            if (state.isLoading) {
                Spacer(modifier = Modifier.weight(.5f))
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(Sizing.medium)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.weight(0.5f))
            } else {
//                DropDownSelector(
//                    modifier = Modifier.padding(horizontal = Sizing.small),
//                    selectedLocation = state.cityName
//                )
                Spacer(modifier = Modifier.height(5.dp))
                SearchBar(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(10.dp))
                WeatherCard(
                    modifier = Modifier,
                    temp = state.weather?.currentWeather?.temperature ?: "0",
                    cityName = state.cityName
                )
            }
        }

    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeUiEvent.LoadWeatherData(fusedLocationProviderClient))
    }
}

@Composable
private fun WeatherCard(
    modifier: Modifier,
    temp: String,
    cityName: String,
) {
    OutlinedCard(
        modifier = modifier
            .heightIn(min = 100.dp, max = 150.dp),
        colors = CardDefaults.outlinedCardColors(MaterialTheme.colorScheme.inverseOnSurface),
        elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(Sizing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cityName,
                    style = MaterialTheme.typography.displaySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,

                    )
                Spacer(modifier = Modifier.padding(top = 10.dp))
                ShortWeatherDescriptionWitIconRow(
                    icon = if (isSystemInDarkTheme()) Icons.Outlined.Cloud else Icons.Filled.Cloud,
                    description = "Clear Sky",
                    modifier = Modifier
                )

            }

            Text(
                modifier = Modifier,
                text = temp,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun ShortWeatherDescriptionWitIconRow(
    icon: ImageVector,
    modifier: Modifier,
    description: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = icon,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = description,
            maxLines = 1,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.labelLarge
        )
    }

}

//@Preview
//@Composable
//private fun WeatherCardPreview() {
//    Surface {
//        WeatherCard(
//            modifier = Modifier, temp = "0"
//        )
//    }
//}