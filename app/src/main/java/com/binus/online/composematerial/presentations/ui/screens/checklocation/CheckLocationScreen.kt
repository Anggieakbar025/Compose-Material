package com.binus.online.composematerial.presentations.ui.screens.checklocation

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.binus.online.composematerial.presentations.ui.screens.login.LoginScreen
import com.binus.online.composematerial.presentations.ui.theme.ComposeMaterialTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

@SuppressLint("UnrememberedMutableState")
@Composable
fun CheckLocationScreen(navController: NavHostController) {
    val initialPosition = LatLng(-6.269229182661631, 106.84319972991945)
    var selectedLocation by remember { mutableStateOf<LatLng?>(null) }
    val geocoder = Geocoder(LocalContext.current, Locale.getDefault())
    val coroutineScope = rememberCoroutineScope()
    var addressText by remember { mutableStateOf("") }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 10f)
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            val newPosition = cameraPositionState.position.target
            selectedLocation = newPosition
            println("Camera stopped moving at: $newPosition")
        }
    }

    Scaffold(
        content = { paddingValues ->
            Box(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize())
            {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "Cek wilayah Anda!",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                    )

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        cameraPositionState = cameraPositionState,
                        properties = MapProperties(
                            mapType = MapType.SATELLITE
                        ),
                        onMapClick = { latLng ->
                            selectedLocation = latLng
                            coroutineScope.launch {
                                val address = getAddressFromLatLng(geocoder, latLng)
                                addressText = address
                            }
                        }
                    ) {
                        Marker(
                            state = MarkerState(position = initialPosition),
                            title = "MyPosition",
                            snippet = "This is a description of this Marker",
                            draggable = true,

                            )
                    }

                    Text(
                        text = "Lokasi terpilih :",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                    Text(
                        text = addressText.ifBlank { "-" },
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )

                    Text(
                        text = "Jumlah Korban :",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    )
}

suspend fun getAddressFromLatLng(geocoder: Geocoder, latLng: LatLng): String {
    return withContext(Dispatchers.IO) {
        try {
            val addresses: MutableList<Address>? = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                val addressFragments = with(address) {
                    (0..maxAddressLineIndex).map { getAddressLine(it) }
                }
                addressFragments.joinToString(separator = "\n")
            } else {
                "No address found"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Unable to get address"
        }
    }
}

@Preview
@Composable
fun CheckLocationPreview() {
    ComposeMaterialTheme {
        CheckLocationScreen(rememberNavController())
    }
}