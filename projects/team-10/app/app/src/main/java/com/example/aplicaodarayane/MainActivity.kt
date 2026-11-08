package com.example.aplicaodarayane

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aplicaodarayane.ui.theme.AplicaçãoDaRayaneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AplicaçãoDaRayaneTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LoginScreen(
                        onLoginClick = { email, _ ->
                            Toast.makeText(
                                this,
                                "Login iniciado para: $email",
                                Toast.LENGTH_SHORT,
                            ).show()
                        },
                        onForgotPasswordClick = {
                            Toast.makeText(
                                this,
                                "Recuperação de senha clicada",
                                Toast.LENGTH_SHORT,
                            ).show()
                        },
                        onSignUpClick = {
                            Toast.makeText(
                                this,
                                "Ir para tela de cadastro",
                                Toast.LENGTH_SHORT,
                            ).show()
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainActivityPreview() {
    AplicaçãoDaRayaneTheme {
        LoginScreen()
    }
}