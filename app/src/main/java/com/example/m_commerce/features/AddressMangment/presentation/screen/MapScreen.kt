package com.example.m_commerce.features.AddressMangment.presentation.screen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.m_commerce.config.routes.AppRoutes
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.core.shared.components.default_top_bar.DefaultTopBar
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapScreenUi(navController: NavHostController) {
    val context = LocalContext.current
    val activity = context as Activity
    val snackbarHostState = remember { SnackbarHostState() }

    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
    var hasLocationPermission by remember { mutableStateOf(false) }
    val markerState = remember { MarkerState() }
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationRequest = LocationRequest.create().apply {
        priority = Priority.PRIORITY_HIGH_ACCURACY
    }

    val locationSettingsClient = remember { LocationServices.getSettingsClient(context) }

    val resolutionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            getCurrentLocation(fusedLocationClient, context) { location ->
                currentLocation = location
                markerState.position = location
            }
        } else {
            Toast.makeText(context, "Location services not enabled", Toast.LENGTH_SHORT).show()
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasLocationPermission = isGranted
        if (isGranted) {
            getCurrentLocation(fusedLocationClient, context) {
                currentLocation = it
                markerState.position = it
            }
        } else {
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
            currentLocation = LatLng(26.8206, 30.8025)
            markerState.position = currentLocation!!
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            currentLocation ?: LatLng(26.8206, 30.8025),
            15f
        )
    }

    LaunchedEffect(Unit) {
        val permissionCheck = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        hasLocationPermission = permissionCheck == PackageManager.PERMISSION_GRANTED

        if (hasLocationPermission) {
            getCurrentLocation(fusedLocationClient, context) {
                currentLocation = it
                markerState.position = it
            }
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    LaunchedEffect(currentLocation) {
        currentLocation?.let {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 15f)
        }
    }

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = "Select Location",
                navController = navController
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState, modifier = Modifier.padding(bottom = 100.dp))
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = { location ->
                    markerState.position = location
                    currentLocation = location
                },
                properties = MapProperties(
                    isMyLocationEnabled = hasLocationPermission
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false
                )
            ) {
                currentLocation?.let {
                    Marker(
                        state = markerState,
                        title = "Selected Location",
                        snippet = "${it.latitude}, ${it.longitude}",
                        draggable = true
                    )
                }
            }

            // FAB to re-center
            if (hasLocationPermission) {
                FloatingActionButton(
                    onClick = {
                        val settingsRequest = LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest)
                            .build()

                        locationSettingsClient.checkLocationSettings(settingsRequest)
                            .addOnSuccessListener {
                                getCurrentLocation(fusedLocationClient, context) { location ->
                                    currentLocation = location
                                    markerState.position = location
                                    cameraPositionState.position =
                                        CameraPosition.fromLatLngZoom(location, 15f)
                                }
                            }
                            .addOnFailureListener { exception ->
                                if (exception is ResolvableApiException) {
                                    try {
                                        val intentSenderRequest = IntentSenderRequest.Builder(
                                            exception.resolution.intentSender
                                        ).build()
                                        resolutionLauncher.launch(intentSenderRequest)
                                    } catch (sendEx: IntentSender.SendIntentException) {
                                        Toast.makeText(
                                            context,
                                            "Unable to resolve location settings.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Location services are unavailable.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 100.dp),
                    containerColor = Teal
                ) {
                    Icon(Icons.Default.MyLocation, contentDescription = "My Location", tint = White)
                }
            }

            // Select Location Button
            CustomButton(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(bottom = 40.dp)
                    .width(177.dp)
                    .align(Alignment.BottomCenter),
                height = 50,
                fontSize = 16,
                text = "Select Location",
                backgroundColor = Teal,
                onClick = {
                    currentLocation?.let {
                        navController.navigate(
                            AppRoutes.AddAddressScreen(lat = it.latitude, lng = it.longitude)
                        )
                    }
                }
            )
        }
    }
}

private fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    onLocationReceived: (LatLng) -> Unit
) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    onLocationReceived(LatLng(it.latitude, it.longitude))
                }
            }
            .addOnFailureListener {
                onLocationReceived(LatLng(26.8206, 30.8025))
            }
    }
}
