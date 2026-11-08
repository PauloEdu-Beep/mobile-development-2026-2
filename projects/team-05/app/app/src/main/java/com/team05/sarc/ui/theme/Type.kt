package com.team05.sarc.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Este arquivo define os "tamanhos de letra" do SARC, como os estilos de título e corpo de um editor de texto.
// Na tela, eles são usados assim: style = MaterialTheme.typography.headlineLarge
// Unidade "sp": tamanho de fonte que respeita a configuração de letra grande do celular (acessibilidade).
val Typography = Typography(
    // Título grande: usado no nome "SARC" do cabeçalho.
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default, // Fonte padrão do Android.
        fontWeight = FontWeight.Bold,    // Negrito.
        fontSize = 30.sp,                // Tamanho da letra.
        lineHeight = 36.sp,              // Altura da linha (espaço vertical ocupado).
        letterSpacing = 0.sp             // Espaço extra entre as letras.
    ),
    // Título de janela: usado nos títulos dos modais.
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp
    ),
    // Subtítulo: nome da escola, texto do botão "Entrar" e subtítulos dos modais.
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    // Corpo de texto grande (texto padrão digitado nos campos).
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // Corpo de texto médio: slogan e parágrafos dos modais.
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    // Etiqueta: nome de cada nível nos cartões de prioridade.
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
)
