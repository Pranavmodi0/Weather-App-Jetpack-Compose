package com.only.practiceapp.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.only.practiceapp.R
import com.only.practiceapp.data.remote.WeatherInterface
import com.only.practiceapp.domain.models.CurrentWeather
import com.only.practiceapp.ui.theme.Cloudy1
import com.only.practiceapp.ui.theme.Cloudy2
import com.only.practiceapp.ui.theme.ParCloudy
import com.only.practiceapp.ui.theme.Sunny1
import com.only.practiceapp.ui.theme.Sunny2
import com.only.practiceapp.ui.theme.Typography


private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

@Composable
fun Home(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val homeState = homeViewModel.homeState

    when (homeState.isLoading) {
        true -> {
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                RequestLocationPermission(
                    onPermissionGranted = { },
                    onPermissionDenied = { },
                    onPermissionsRevoked = { }
                )

                CircularProgressIndicator()

                Spacer(Modifier.padding(46.dp))

                Text(
                    "Loading...",
                    style = Typography.bodySmall,
                    color = Color.Black
                )
            }
        }

        else -> {
            homeState.weather?.let {
                CurrentWeatherItem(modifier, it.currentWeather)
            }
        }
    }
}

@Composable
fun CurrentWeatherItem(modifier: Modifier, currentWeather: CurrentWeather){
    val color = listOf(Sunny1, Sunny2)
    val color2 = listOf(Cloudy1, Cloudy2)
    val color3 = listOf(ParCloudy, ParCloudy)

    val title = ""// TODO

    var textCol : Color = Color.Black

    if(title == ""){ //TODO
        textCol = Color.Black
    } else {
        textCol = Color.Black
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(Brush.verticalGradient(color2)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 50.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                title,
                style = Typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = textCol,
                fontSize = 20.sp,
            )
            Text(
                text = currentWeather.time,
                style = Typography.bodySmall,
                color = textCol,
                fontSize = 20.sp
            )
        }

        Spacer(Modifier.padding(50.dp))

        Image(
            painter = painterResource(id = currentWeather.weatherStatus.icon),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )

        Spacer(Modifier.padding(15.dp))

        Text(
            text = currentWeather.weatherStatus.info,
            style = Typography.bodySmall,
            color = textCol,
            fontSize = 30.sp
        )

        Spacer(Modifier.padding(15.dp))

        Text(
            "${currentWeather.temperature}\u2103",
            style = Typography.bodySmall,
            color = textCol,
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.padding(30.dp))

        Image(
            painter = painterResource(R.drawable.graph),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onPermissionsRevoked: () -> Unit
) {

    val context = LocalContext.current

    //Initialize it where you need it
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    // Initialize the state for managing multiple location permissions.
    val permissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    // Use LaunchedEffect to handle permissions logic when the composition is launched.
    LaunchedEffect(key1 = permissionState) {
        // Check if all previously granted permissions are revoked.
        val allPermissionsRevoked =
            permissionState.permissions.size == permissionState.revokedPermissions.size

        // Filter permissions that need to be requested.
        val permissionsToRequest = permissionState.permissions.filter {
            !it.status.isGranted
        }

        // If there are permissions to request, launch the permission request.
        if (permissionsToRequest.isNotEmpty()) permissionState.launchMultiplePermissionRequest()

        // Execute callbacks based on permission status.
        if (allPermissionsRevoked) {
            onPermissionsRevoked()
        } else {
            if (permissionState.allPermissionsGranted) {
                onPermissionGranted()
                getCurrentLocation({}, {}, areLocationPermissionsGranted(context), context)
            } else {
                onPermissionDenied()
            }
        }
    }
}

fun areLocationPermissionsGranted(context: Context): Boolean {
    return (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
}

@SuppressLint("MissingPermission")
private fun getLastUserLocation(
    onGetLastLocationSuccess: (Pair<Double, Double>) -> Unit,
    onGetLastLocationFailed: (Exception) -> Unit,
    context: Context
) {


    // Check if location permissions are granted
    if (areLocationPermissionsGranted(context)) {
        // Retrieve the last known location
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    // If location is not null, invoke the success callback with latitude and longitude
                    onGetLastLocationSuccess(Pair(it.latitude, it.longitude))
                }
            }
            .addOnFailureListener { exception ->
                // If an error occurs, invoke the failure callback with the exception
                onGetLastLocationFailed(exception)
            }
    }
}

@SuppressLint("MissingPermission")
private fun getCurrentLocation(
    onGetCurrentLocationSuccess: (Pair<Double, Double>) -> Unit,
    onGetCurrentLocationFailed: (Exception) -> Unit,
    priority: Boolean = true,
    context: Context
) {
    // Determine the accuracy priority based on the 'priority' parameter
    val accuracy = if (priority) Priority.PRIORITY_HIGH_ACCURACY
    else Priority.PRIORITY_BALANCED_POWER_ACCURACY

    // Check if location permissions are granted
    if (areLocationPermissionsGranted(context)) {
        // Retrieve the current location asynchronously
        fusedLocationProviderClient.getCurrentLocation(
            accuracy, CancellationTokenSource().token,
        ).addOnSuccessListener { location ->
            location?.let {
                // If location is not null, invoke the success callback with latitude and longitude
                onGetCurrentLocationSuccess(
                    Pair(it.latitude, it.longitude)
                )
            }
        }.addOnFailureListener { exception ->
            // If an error occurs, invoke the failure callback with the exception
            onGetCurrentLocationFailed(exception)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Home()
}