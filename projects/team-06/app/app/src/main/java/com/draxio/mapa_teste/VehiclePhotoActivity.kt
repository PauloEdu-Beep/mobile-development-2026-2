package com.draxio.mapa_teste

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.draxio.mapa_teste.ui.theme.Mapa_testeTheme

class VehiclePhotoActivity : ComponentActivity() {

    private var capturedBitmap by mutableStateOf<Bitmap?>(null)

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            capturedBitmap = bitmap
            Toast.makeText(this, "Foto capturada com sucesso!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Captura de foto cancelada.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mapa_testeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFD1D719)
                ) {
                    VehiclePhotoScreen(
                        bitmap = capturedBitmap,
                        onTakePicture = {
                            takePictureLauncher.launch(null)
                        },
                        onFinishRegistration = {
                            if (capturedBitmap != null) {
                                Toast.makeText(this, "Cadastro concluído com sucesso!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, Mapao_do_Oda::class.java)
                                startActivity(intent)
                                finishAffinity()
                            } else {
                                Toast.makeText(this, "Por favor, tire uma foto do automóvel.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun VehiclePhotoScreen(
    bitmap: Bitmap?,
    onTakePicture: () -> Unit,
    onFinishRegistration: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Foto do Automóvel",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Box(
                modifier = Modifier
                    .size(220.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Foto do Automóvel",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Card(
                        modifier = Modifier.fillMaxSize(),
                        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Sem foto", color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onTakePicture,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text(
                    text = "Tirar Foto com a Câmera",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onFinishRegistration,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = "Finalizar e Ir para o Mapa",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VehiclePhotoScreenPreview() {
    Mapa_testeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFD1D719)) {
            VehiclePhotoScreen(
                bitmap = null,
                onTakePicture = {},
                onFinishRegistration = {}
            )
        }
    }
}
