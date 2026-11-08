package com.team05.sarc.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Conjunto de cores usado quando o celular está no MODO ESCURO.
// Cada "papel" (primary, secondary, background, surface) recebe uma cor do arquivo Color.kt.
private val DarkColorScheme = darkColorScheme(
    primary = SarcNavyLight,        // Cor principal (botões, destaques).
    secondary = SarcGreenSecondary, // Cor secundária.
    background = SarcBackgroundDark, // Fundo da tela.
    surface = SarcSurfaceDark        // Fundo de cartões e janelas.
)

// Conjunto de cores usado quando o celular está no MODO CLARO.
private val LightColorScheme = lightColorScheme(
    primary = SarcNavyPrimary,
    secondary = SarcGreenSecondary,
    background = SarcBackgroundLight,
    surface = SarcSurfaceLight
)

/**
 * Tema visual do SARC.
 * Funciona como um "uniforme": tudo o que for desenhado dentro de SARCTheme { ... }
 * herda automaticamente estas cores e fontes (acessadas por MaterialTheme.colorScheme e MaterialTheme.typography).
 */
@Composable
fun SARCTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Descobre sozinho se o celular está no modo escuro.
    dynamicColor: Boolean = false, // false para garantir a identidade visual cívico-militar da escola
    content: @Composable () -> Unit // O conteúdo (telas) que vai "vestir" este tema.
) {
    // Escolhe qual conjunto de cores usar, testando as condições de cima para baixo.
    val colorScheme = when {
        // Cores dinâmicas (baseadas no papel de parede, Android 12+). Desligado no SARC.
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme // Celular no modo escuro.
        else -> LightColorScheme     // Caso padrão: modo claro.
    }

    // Entrega as cores e as fontes (Type.kt) para todo o conteúdo interno.
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
