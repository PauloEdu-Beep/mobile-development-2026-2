package com.team05.sarc.ui.theme

import androidx.compose.ui.graphics.Color

// Este arquivo funciona como uma "caixa de tintas": guarda todas as cores do SARC em um só lugar.
// O formato 0xFF1B365D significa: FF = cor 100% opaca, e 1B365D = código hexadecimal da cor (igual ao usado na web).

// Paleta Institucional Cívico-Militar - Escola Cadidé
val SarcNavyPrimary = Color(0xFF1B365D) // Azul-marinho principal: título, botão "Entrar" e ícones.
val SarcNavyDark = Color(0xFF0F2042)    // Azul-marinho escuro: nome da escola no cabeçalho.
val SarcNavyLight = Color(0xFF3F6399)   // Azul mais claro: cor principal quando o celular está no modo escuro.

val SarcGreenSecondary = Color(0xFF2D5A27) // Verde institucional: botão "Solicitar Cadastro".
val SarcGreenDark = Color(0xFF1A3816)      // Verde escuro reservado para uso futuro.

val SarcBackgroundLight = Color(0xFFF8F9FA) // Fundo da tela no modo claro (quase branco).
val SarcSurfaceLight = Color(0xFFFFFFFF)    // Fundo de cartões e janelas no modo claro (branco).

val SarcBackgroundDark = Color(0xFF121212) // Fundo da tela no modo escuro (quase preto).
val SarcSurfaceDark = Color(0xFF1E1E1E)    // Fundo de cartões e janelas no modo escuro.

// Faixas de Prioridade Escolar SARC (usadas nos cartões do modal "Sobre o SARC")
val PriorityGreen = Color(0xFF2E7D32)   // Nível 1 - Apoio Pedagógico
val PriorityYellow = Color(0xFFF9A825)  // Nível 2 - Atenção Disciplinar
val PriorityOrange = Color(0xFFEF6C00)  // Nível 3 - Prioridade Operacional
val PriorityRed = Color(0xFFC62828)     // Nível 4 - Urgência Crítica
