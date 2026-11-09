package com.team05.sarc.ui.screens

// Ferramentas de layout, rolagem e desenho do Jetpack Compose.
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll

// Componentes Material 3: botões, botões de escolha, campo de texto e aviso.
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

// Ferramentas de estado: lembram os valores digitados e escolhidos.
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

// Importação de medidas, fonte e modificadores.
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Importação das cores do SARC.
import com.team05.sarc.ui.theme.PriorityGreen
import com.team05.sarc.ui.theme.PriorityOrange
import com.team05.sarc.ui.theme.PriorityRed
import com.team05.sarc.ui.theme.PriorityYellow
import com.team05.sarc.ui.theme.SarcNavyDark

// FR-02: as listas de escolha (turmas e salas são de exemplo até o Paulo confirmar).
private val niveis = listOf(
    "Nível 1 - Apoio Pedagógico" to PriorityGreen,
    "Nível 2 - Atenção Disciplinar" to PriorityYellow,
    "Nível 3 - Prioridade Operacional" to PriorityOrange,
    "Nível 4 - Urgência Crítica" to PriorityRed
)
private val turmas = listOf("6º A", "6º B", "7º A", "7º B", "8º A", "8º B", "9º A", "9º B")
private val salas = listOf("01", "02", "03", "04", "05", "06", "07", "08")

// FR-03: o resultado da validação. Um aviso nulo significa "campo certo".
private class Erros(val nivel: String?, val turma: String?, val sala: String?, val situacao: String?) {
    val temErro get() = nivel != null || turma != null || sala != null || situacao != null
}

// FR-03: TODAS as regras ficam aqui, numa função só.
private fun validar(nivel: String, turma: String, sala: String, situacao: String) = Erros(
    nivel = if (nivel.isEmpty()) "Escolha o nível" else null,
    turma = if (turma.isEmpty()) "Escolha a turma" else null,
    sala = if (sala.isEmpty()) "Escolha a sala" else null,
    situacao = if (situacao.length > 100) "Máximo de 100 caracteres" else null
)

/**
 * Formulário "Novo acionamento" (Sprint 04). Não salva nada (isso é a Sprint 05).
 * Quem navega é o MainActivity: esta tela só avisa pelo onVoltar.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NovoAcionamentoScreen(onVoltar: () -> Unit = {}, onEnviar: (String) -> Unit = {}) {
    // FR-02: os 4 campos, guardados em rememberSaveable (sobrevivem a girar o celular).
    var nivel by rememberSaveable { mutableStateOf("") }
    var turma by rememberSaveable { mutableStateOf("") }
    var sala by rememberSaveable { mutableStateOf("") }
    var situacao by rememberSaveable { mutableStateOf("") }
    var tentouEnviar by rememberSaveable { mutableStateOf(false) } // Avisos só depois do 1º "Enviar".
    var aceito by rememberSaveable { mutableStateOf(false) }       // Mostra o aviso de sucesso.

    val erros = validar(nivel, turma, sala, situacao) // Reconferido a cada mudança: o aviso some ao corrigir.
    // A mensagem pronta do protocolo (usada no aviso de sucesso e salva no banco na Sprint 05).
    val mensagem = "$nivel | $turma | Sala $sala | ${situacao.trim().ifEmpty { "Solicito apoio." }}"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding() // Empurra o formulário para cima quando o teclado do celular abre.
            .verticalScroll(rememberScrollState()) // O formulário é maior que a tela: rola.
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Novo acionamento",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = SarcNavyDark
        )

        // Nível: 4 botões com o nome completo e a bolinha da cor da faixa.
        Text("Nível de urgência", fontWeight = FontWeight.Bold)
        niveis.forEach { (nome, cor) ->
            FilterChip(
                selected = nivel == nome,
                onClick = { nivel = nome },
                label = { Text(nome) },
                leadingIcon = { Box(Modifier.size(12.dp).background(cor, CircleShape)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Aviso(if (tentouEnviar) erros.nivel else null)

        // Turma e sala: a mesma função de botões de escolha, usada duas vezes.
        Text("Turma", fontWeight = FontWeight.Bold)
        Escolhas(turmas, turma) { turma = it }
        Aviso(if (tentouEnviar) erros.turma else null)

        Text("Sala", fontWeight = FontWeight.Bold)
        Escolhas(salas, sala) { sala = it }
        Aviso(if (tentouEnviar) erros.sala else null)

        // Situação: opcional. Deixa digitar além de 100 para o erro poder aparecer.
        OutlinedTextField(
            value = situacao,
            onValueChange = { situacao = it },
            label = { Text("Situação (opcional)") },
            isError = erros.situacao != null,
            supportingText = { Text("${situacao.length}/100" + (erros.situacao?.let { " - $it" } ?: "")) },
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onVoltar, modifier = Modifier.weight(1f)) { Text("Cancelar") }
            Button(
                onClick = {
                    tentouEnviar = true
                    if (!erros.temErro) { onEnviar(mensagem); aceito = true } // FR-04 e Sprint 05 (FR-02): só aceita e salva se não há erro.
                },
                modifier = Modifier.weight(1f)
            ) { Text("Enviar") }
        }
    }

    // FR-04: aviso de sucesso com a mensagem pronta; "OK" volta ao Painel.
    if (aceito) {
        AlertDialog(
            onDismissRequest = onVoltar,
            title = { Text("Acionamento pronto") },
            text = { Text(mensagem) },
            confirmButton = { TextButton(onClick = onVoltar) { Text("OK") } }
        )
    }
}

// Linha de botões de escolha (turma e sala): o tocado fica marcado.
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Escolhas(opcoes: List<String>, escolhido: String, onEscolher: (String) -> Unit) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        opcoes.forEach {
            FilterChip(selected = escolhido == it, onClick = { onEscolher(it) }, label = { Text(it) })
        }
    }
}

// Aviso vermelho embaixo de um campo (não desenha nada quando não há erro).
@Composable
private fun Aviso(texto: String?) {
    if (texto != null) Text(texto, color = MaterialTheme.colorScheme.error)
}
