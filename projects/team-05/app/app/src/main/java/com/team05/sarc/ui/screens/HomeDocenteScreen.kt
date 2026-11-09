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

// Importação dos componentes Material 3 (botões e textos).
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text

// Importação da marca de função desenhável do Compose.
import androidx.compose.runtime.Composable

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
 * Versão MVP: título, botão "Novo acionamento" (abre o formulário da Sprint 04),
 * legenda dos níveis, lista "Meus acionamentos" vazia e botão "Sair".
 */
@Composable
fun HomeDocenteScreen(
    // Ação do botão "Sair": quem chama (o NavHost) decide para onde ir.
    onSair: () -> Unit = {},
    // Ação do botão "Novo acionamento": o NavHost abre o formulário.
    onNovoAcionamento: () -> Unit = {}
) {
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

        // Botão que abre o formulário "Novo acionamento" (Sprint 04).
        Button(
            onClick = onNovoAcionamento,
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

        // FR-05: lista vazia (os acionamentos chegam na Sprint 05).
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
