package br.unemat.ritmo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold { padding ->
                    Column(Modifier.padding(padding).padding(24.dp)) {
                        Text("Ritmo", style = MaterialTheme.typography.headlineLarge)
                        Text("Ambiente Android pronto.")
                    }
                }
            }
        }
    }
}
