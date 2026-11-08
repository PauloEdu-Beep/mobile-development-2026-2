package com.team03.wifi

import androidx.compose.ui.graphics.Color

enum class SignalStatus(val label: String, val color: Color, val dbm: Int) {
    NONE("Aguardando", TextGray, 0),
    STRONG("Sinal Forte", Color(0xFF35D07F), -45),
    MEDIUM("Sinal Médio", Color(0xFFF5C518), -65),
    WEAK("Sinal Fraco", Color(0xFFF5455C), -85)
}

// Paleta compartilhada
val BackgroundDark = Color(0xFF0A0E17)
val SurfaceDark = Color(0xFF11172A)
val AccentCyan = Color(0xFF22E7F5)
val TextGray = Color(0xFF8A93A8)
val LineGray = Color(0xFF232B42)