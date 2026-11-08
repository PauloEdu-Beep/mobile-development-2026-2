package com.team03.wifi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Tela B: detalhes da verificação (Sprint 03)
@Composable
fun DetailsScreen(
    status: SignalStatus,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val recommendation = when (status) {
        SignalStatus.STRONG -> "Ótimo local para trabalhar ou fazer chamadas de vídeo."
        SignalStatus.MEDIUM -> "Sinal utilizável, mas pode oscilar em horários de pico."
        SignalStatus.WEAK -> "Considere se aproximar do roteador ou usar um repetidor."
        SignalStatus.NONE -> "Nenhuma verificação foi realizada ainda."
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Detalhes da Verificação",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceDark, RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            DetailRow(label = "Status", value = status.label, valueColor = status.color)
            Spacer(modifier = Modifier.height(12.dp))
            DetailRow(label = "Intensidade", value = "${status.dbm} dBm", valueColor = Color.White)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Recomendação", color = TextGray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = recommendation, color = Color.White, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = AccentCyan, contentColor = Color.Black),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("← Voltar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextGray, fontSize = 14.sp)
        Text(text = value, color = valueColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

