package com.gscapin.conexanewsapp.screens.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.gscapin.conexanewsapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(latitude: String? = "", longitude: String? = "", username: String? = "", goBackToUsers: () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.topbar_title_user_location)) },
                navigationIcon = {
                    IconButton(onClick = goBackToUsers) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to users"
                        )
                    }
                })
        }
    ) { paddingValues ->

        val lat = latitude?.toDoubleOrNull() ?: 0.0
        val lng = longitude?.toDoubleOrNull() ?: 0.0

        val userPosition = LatLng(lat, lng)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(userPosition, 10f)
        }
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = userPosition),
                title = username,
                snippet = "$latitude, $longitude"
            )
        }
    }
}