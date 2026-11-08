package com.team03.wifi

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSeeDetails: (SignalStatus) -> Unit
) {
    var signalStatus by rememberSaveable { mutableStateOf(SignalStatus.NONE) }
    var roomName by rememberSaveable { mutableStateOf("") }
    var roomError by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(SurfaceDark, CircleShape)
                    .border(2.dp, AccentCyan, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                WifiIcon(color = AccentCyan, size = 24.dp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("Wiflux", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("SIGNAL DIAGNOSTICS", color = TextGray, fontSize = 10.sp, letterSpacing = 1.5.sp)
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Verificação de Sinal",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Mapeie a rede. Conecte no melhor ponto.",
                color = AccentCyan.copy(alpha = 0.85f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            RadarView(status = signalStatus)

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .background(SurfaceDark, RoundedCornerShape(50))
                    .border(1.dp, statusBorderColor(signalStatus), RoundedCornerShape(50)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = signalStatus.label,
                    color = signalStatus.color,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = roomName,
                onValueChange = {
                    roomName = it
                    roomError = false
                },
                label = { Text("Nome do cômodo") },
                isError = roomError,
                supportingText = {
                    if (roomError) Text("Digite pelo menos 3 letras")
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = AccentCyan,
                    unfocusedLabelColor = TextGray,
                    focusedBorderColor = AccentCyan,
                    unfocusedBorderColor = LineGray
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (roomName.trim().length < 3) {
                        roomError = true
                    } else {
                        roomError = false
                        signalStatus = SignalStatus.entries
                            .filter { it != SignalStatus.NONE }
                            .random(Random(System.nanoTime()))
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = AccentCyan, contentColor = Color.Black),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(24.dp, RoundedCornerShape(14.dp), ambientColor = AccentCyan, spotColor = AccentCyan)
            ) {
                Text("▶  Iniciar Verificação", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            // Só aparece depois de ao menos uma verificação (AC-04: ação que navega)
            if (signalStatus != SignalStatus.NONE) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { onSeeDetails(signalStatus) },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceDark, contentColor = AccentCyan),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text("Ver Detalhes", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun statusBorderColor(status: SignalStatus): Color =
    if (status == SignalStatus.NONE) LineGray else status.color.copy(alpha = 0.4f)

@Composable
private fun RadarView(status: SignalStatus) {
    Box(modifier = Modifier.size(240.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val outer = size.minDimension / 2f
            for (i in 0 until 60) {
                val angle = Math.toRadians(i * 6.0)
                val long = i % 5 == 0
                val inner = outer * if (long) 0.88f else 0.93f
                drawLine(
                    color = LineGray,
                    start = Offset(
                        center.x + (inner * cos(angle)).toFloat(),
                        center.y + (inner * sin(angle)).toFloat()
                    ),
                    end = Offset(
                        center.x + (outer * cos(angle)).toFloat(),
                        center.y + (outer * sin(angle)).toFloat()
                    ),
                    strokeWidth = (if (long) 2.dp else 1.dp).toPx()
                )
            }
            if (status != SignalStatus.NONE) {
                val arcRadius = outer * 0.78f
                val sweep = ((status.dbm + 100) / 65f).coerceIn(0.15f, 1f) * 320f
                drawArc(
                    color = status.color,
                    startAngle = -90f,
                    sweepAngle = sweep,
                    useCenter = false,
                    topLeft = Offset(center.x - arcRadius, center.y - arcRadius),
                    size = Size(arcRadius * 2, arcRadius * 2),
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(SurfaceDark, CircleShape)
                    .border(1.dp, statusBorderColor(status), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                WifiIcon(color = status.color, size = 28.dp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            if (status == SignalStatus.NONE) {
                Text(
                    text = "—",
                    color = TextGray,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${status.dbm}",
                        color = status.color,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "dBm",
                        color = TextGray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun WifiIcon(color: Color, size: Dp) {
    Canvas(modifier = Modifier.size(size)) {
        val stroke = Stroke(width = this.size.minDimension * 0.11f, cap = StrokeCap.Round)
        val cx = this.size.width / 2f
        val baseY = this.size.height * 0.86f
        for (i in 1..3) {
            val r = this.size.width * (0.18f + 0.16f * i)
            drawArc(
                color = color,
                startAngle = -135f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = Offset(cx - r, baseY - r),
                size = Size(r * 2, r * 2),
                style = stroke
            )
        }
        drawCircle(color, radius = this.size.width * 0.07f, center = Offset(cx, baseY))
    }
}
