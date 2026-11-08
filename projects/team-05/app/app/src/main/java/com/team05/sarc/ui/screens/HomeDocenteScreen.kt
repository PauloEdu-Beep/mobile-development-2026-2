package com.team05.sarc.ui.screens

// Importação das ferramentas de layout e desenho do Jetpack Compose.
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape

// Importação dos componentes Material 3 (botões, textos e a janela de aviso).
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

// Importação das ferramentas de estado (lembrar se o aviso está aberto).
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// Importação de alinhamento, cores, prévia e medidas.
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Importação das cores e do tema do SARC.
import com.team05.sarc.ui.theme.PriorityGreen
import com.team05.sarc.ui.theme.PriorityOrange
import com.team05.sarc.ui.theme.PriorityRed
import com.team05.sarc.ui.theme.PriorityYellow
import com.team05.sarc.ui.theme.SARCTheme
import com.team05.sarc.ui.theme.SarcNavyDark

/**
 * Painel do Docente (Sprint 03): a tela que aparece depois do "Entrar".
 * Versão MVP: título, botão "Novo acionamento" (só um aviso), legenda dos níveis,
 * lista "Meus acionamentos" vazia e botão "Sair".
 */
@Composable
fun HomeDocenteScreen(
    // Ação do botão "Sair": quem chama (o NavHost) decide para onde ir.
    onSair: () -> Unit = {}
) {
    // FR-06: guarda se o aviso "Novo acionamento" está aberto (começa fechado).
    var showAviso by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding() // Não desenha por baixo da barra de status nem da barra de navegação.
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Espaço igual entre os blocos.
    ) {
        // FR-03: título e subtítulo.
        Text(
            text = "Painel do Docente",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = SarcNavyDark
        )
        Text(text = "Acione a Coordenação em poucos toques")

        // FR-06: botão que abre o aviso.
        Button(
            onClick = { showAviso = true },
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Novo acionamento")
        }

        // FR-04: legenda dos 4 níveis (só consulta).
        Text(text = "Níveis de prioridade", fontWeight = FontWeight.Bold)
        NivelLegenda(cor = PriorityGreen, nome = "Nível 1 - Apoio Pedagógico")
        NivelLegenda(cor = PriorityYellow, nome = "Nível 2 - Atenção Disciplinar")
        NivelLegenda(cor = PriorityOrange, nome = "Nível 3 - Prioridade Operacional")
        NivelLegenda(cor = PriorityRed, nome = "Nível 4 - Urgência Crítica")

        // FR-05: lista vazia (os acionamentos chegam na Sprint 04).
        Text(text = "Meus acionamentos", fontWeight = FontWeight.Bold)
        Text(text = "Nenhum acionamento ainda")

        Spacer(modifier = Modifier.weight(1f)) // Empurra o "Sair" para o fim da tela.

        // FR-07: botão "Sair" volta ao Login.
        OutlinedButton(
            onClick = onSair,
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Sair")
        }
    }

    // FR-06: o aviso só aparece quando showAviso é verdadeiro.
    if (showAviso) {
        AlertDialog(
            onDismissRequest = { showAviso = false }, // Tocar fora do aviso também fecha.
            title = { Text("Novo acionamento") },
            text = { Text("O formulário de acionamento chega na Sprint 04") },
            confirmButton = {
                TextButton(onClick = { showAviso = false }) { Text("Entendi") }
            }
        )
    }
}

/**
 * Uma linha da legenda: bolinha com a cor do nível + nome em azul-marinho (legível).
 */
@Composable
fun NivelLegenda(cor: Color, nome: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(cor, CircleShape) // Bolinha colorida do nível.
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(text = nome, color = SarcNavyDark)
    }
}

// Prévia no Android Studio.
@Preview(showBackground = true)
@Composable
fun HomeDocenteScreenPreview() {
    SARCTheme {
        HomeDocenteScreen()
    }
}
