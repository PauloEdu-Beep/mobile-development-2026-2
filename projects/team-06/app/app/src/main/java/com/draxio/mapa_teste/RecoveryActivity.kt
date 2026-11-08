package com.draxio.mapa_teste

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.draxio.mapa_teste.ui.theme.Mapa_testeTheme

class RecoveryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Mapa_testeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFD1D719)
                ) {
                    RecoveryScreen(
                        onRecoverSent = { email ->
                            Toast.makeText(this, "Instruções de recuperação enviadas para $email", Toast.LENGTH_LONG).show()
                            finish()
                        },
                        onBack = { finish() },
                        onShowError = { msg ->
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RecoveryScreen(
    onRecoverSent: (String) -> Unit,
    onBack: () -> Unit,
    onShowError: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }

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
                text = "Recuperar Conta",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Digite seu e-mail cadastrado para receber as instruções de recuperação.",
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail de recuperação") },
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

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isNotBlank()) {
                        onRecoverSent(email)
                    } else {
                        onShowError("Por favor, digite seu e-mail.")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = "Enviar Instruções",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBack) {
                Text(
                    text = "Voltar ao Login",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecoveryScreenPreview() {
    Mapa_testeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFD1D719)) {
            RecoveryScreen(
                onRecoverSent = {},
                onBack = {},
                onShowError = {}
            )
        }
    }
}
