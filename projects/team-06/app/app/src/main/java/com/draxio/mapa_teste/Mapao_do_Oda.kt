package com.draxio.mapa_teste

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.draxio.mapa_teste.ui.theme.Mapa_testeTheme
import kotlinx.coroutines.delay

class Mapao_do_Oda : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var isFirstZoom = true

    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap?.let { map ->
                        if (isFirstZoom) {
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                            isFirstZoom = false
                        } else {
                            map.animateCamera(CameraUpdateFactory.newLatLng(currentLatLng))
                        }
                    }
                }
            }
        }

        setContent {
            Mapa_testeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        MapScreen(
                            onMapReady = { googleMap ->
                                mMap = googleMap
                                enableMyLocation()
                            }
                        )
                        
                        RideHailingOverlay(
                            onRideRequested = { dest, category ->
                                Toast.makeText(this@Mapao_do_Oda, "Buscando motorista para $dest ($category)...", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        enableMyLocation()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
            .setMinUpdateIntervalMillis(2000)
            .build()

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, mainLooper)
    }

    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            
            mMap?.isMyLocationEnabled = true
            startLocationUpdates()
            return
        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            } else {
                Toast.makeText(this, "Permissão de localização negada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun MapScreen(onMapReady: (GoogleMap) -> Unit) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val fragmentActivity = context as FragmentActivity
            androidx.fragment.app.FragmentContainerView(context).apply {
                id = android.view.View.generateViewId()
                tag = "map_fragment_container"
                val mapFragment = SupportMapFragment.newInstance()
                fragmentActivity.supportFragmentManager.beginTransaction()
                    .replace(this.id, mapFragment)
                    .commit()
                mapFragment.getMapAsync { googleMap ->
                    onMapReady(googleMap)
                }
            }
        },
        update = { _ -> }
    )
}

enum class RideStatus {
    IDLE,
    SELECTING_CATEGORY,
    SEARCHING_DRIVER,
    DRIVER_ACCEPTED
}

@Composable
fun RideHailingOverlay(
    onRideRequested: (String, String) -> Unit
) {
    var rideStatus by remember { mutableStateOf(RideStatus.IDLE) }
    var destination by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Moto (R$ 12,00)") }

    // Simula a aceitação do motorista após 4 segundos buscando
    LaunchedEffect(rideStatus) {
        if (rideStatus == RideStatus.SEARCHING_DRIVER) {
            delay(4000L)
            rideStatus = RideStatus.DRIVER_ACCEPTED
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        when (rideStatus) {
            RideStatus.IDLE -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Olá! Para onde vamos?",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = destination,
                            onValueChange = { destination = it },
                            label = { Text("Digite o destino...") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.DarkGray,
                                focusedLabelColor = Color.Black,
                                unfocusedLabelColor = Color.DarkGray,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                if (destination.isNotBlank()) {
                                    rideStatus = RideStatus.SELECTING_CATEGORY
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD1D719))
                        ) {
                            Text(text = "Continuar", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            RideStatus.SELECTING_CATEGORY -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Escolha a categoria",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        val categories = listOf("Moto (R$ 12,00)", "Confort Car (R$ 25,00)", "InDrive (Negociar)")
                        categories.forEach { cat ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (selectedCategory == cat),
                                    onClick = { selectedCategory = cat }
                                )
                                Text(text = cat, color = Color.Black, fontSize = 16.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { rideStatus = RideStatus.IDLE },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp)
                            ) {
                                Text("Voltar", color = Color.Black)
                            }
                            Button(
                                onClick = {
                                    rideStatus = RideStatus.SEARCHING_DRIVER
                                    onRideRequested(destination, selectedCategory)
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                            ) {
                                Text("Solicitar", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            RideStatus.SEARCHING_DRIVER -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = Color(0xFFD1D719))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Procurando motorista próximo...",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Destino: $destination\nCategoria: $selectedCategory",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { rideStatus = RideStatus.IDLE },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text("Cancelar Corrida", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            RideStatus.DRIVER_ACCEPTED -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Motorista a caminho!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00AA00)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Condutor: Carlos Silva", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                        Text(text = "Veículo: Honda Civic • Placa: ABC-1234", color = Color.Gray, fontSize = 14.sp)
                        Text(text = "Avaliação: 4.9 ★", color = Color.DarkGray, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { rideStatus = RideStatus.IDLE },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {
                            Text("Finalizar Corrida", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
