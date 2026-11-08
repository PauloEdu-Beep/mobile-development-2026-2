package com.team05.sarc.ui.screens

// Importação da animação que mostra/esconde conteúdo suavemente (descrição dos cartões de prioridade).
import androidx.compose.animation.AnimatedVisibility

// Importação das ferramentas básicas de desenho, formato e estilo do Jetpack Compose.
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

// Importação dos ícones visuais prontos do Google (envelope, cadeado, olho, etc.).
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

// Importação dos componentes visuais modernos do Material Design 3 (botões, caixas de diálogo, campos de texto).
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

// Importação dos recursos de estado, que permitem ao aplicativo lembrar o que o usuário digita ou clica.
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

// Importação de recursos de alinhamento, cores, imagens, teclado e tamanhos na tela.
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Importação dos recursos do projeto: imagens e paleta de cores institucional do SARC.
import com.team05.sarc.R
import com.team05.sarc.ui.theme.PriorityGreen
import com.team05.sarc.ui.theme.PriorityOrange
import com.team05.sarc.ui.theme.PriorityRed
import com.team05.sarc.ui.theme.PriorityYellow
import com.team05.sarc.ui.theme.SARCTheme
import com.team05.sarc.ui.theme.SarcGreenSecondary
import com.team05.sarc.ui.theme.SarcNavyDark
import com.team05.sarc.ui.theme.SarcNavyPrimary

/**
 * Tela inicial e de autenticação institucional do SARC.
 * Esta função desenha toda a interface e responde às ações do usuário.
 */
@Composable
fun WelcomeScreen(
    // Ação disparada quando o usuário clica no botão "Entrar", enviando o e-mail e a senha digitados.
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    // Ação disparada quando o usuário clica no botão "Solicitar Cadastro".
    onRegisterClick: () -> Unit = {}
) {
    // Guarda o texto do e-mail digitado e não perde o valor mesmo se o celular for girado.
    var email by rememberSaveable { mutableStateOf("") }

    // Guarda o texto da senha digitada pelo usuário.
    var password by rememberSaveable { mutableStateOf("") }

    // Guarda se a senha está visível em texto aberto ou escondida com bolinhas.
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    // Guarda se a janela informativa "Sobre o SARC" deve ser exibida na tela.
    var showAboutDialog by remember { mutableStateOf(false) }

    // Guarda se a janela informativa "Solicitação de Cadastro" deve ser exibida na tela.
    var showRegisterDialog by remember { mutableStateOf(false) }

    // Permite que o conteúdo da tela role para cima ou para baixo caso a tela seja pequena.
    val scrollState = rememberScrollState()

    // Folha de fundo que preenche toda a tela do celular com a cor padrão do sistema.
    Surface(
        modifier = Modifier.fillMaxSize(), // Faz o fundo ocupar 100% da largura e altura da tela.
        color = MaterialTheme.colorScheme.background // Aplica a cor de fundo do tema.
    ) {
        // Coluna que empilha todos os elementos visuais verticalmente, um embaixo do outro.
        Column(
            modifier = Modifier
                .fillMaxSize() // Preenche todo o espaço vertical disponível.
                .verticalScroll(scrollState) // Habilita a rolagem vertical da tela.
                .padding(horizontal = 24.dp, vertical = 32.dp), // Adiciona margens laterais e verticais para respirar.
            horizontalAlignment = Alignment.CenterHorizontally, // Centraliza todos os itens na horizontal.
            verticalArrangement = Arrangement.Center // Posiciona o conteúdo no centro vertical da tela.
        ) {
            // ==========================================
            // Cabeçalho institucional (FR-01, AC-01)
            // ==========================================

            // Exibe a imagem do brasão oficial da Escola Cadidé.
            Image(
                painter = painterResource(id = R.drawable.ic_brasao_cadide), // Carrega a imagem da pasta de recursos.
                contentDescription = "Brasão da Escola Cadidé", // Descrição para pessoas com deficiência visual.
                modifier = Modifier.size(110.dp) // Define o tamanho do brasão como 110 por 110 pixels.
            )

            // Espaço vazio de 16 pixels para separar o brasão do título.
            Spacer(modifier = Modifier.height(16.dp))

            // Nome principal do aplicativo em letras destacadas e maiúsculas.
            Text(
                text = "SARC", // Sigla do projeto.
                style = MaterialTheme.typography.headlineLarge, // Estilo tipográfico de título grande.
                fontWeight = FontWeight.Bold, // Letras em negrito forte.
                color = SarcNavyPrimary, // Cor azul-marinho institucional.
                letterSpacing = 2.sp // Espaçamento suave entre as letras para dar elegância.
            )

            // Espaço de 4 pixels entre a sigla e o nome da escola.
            Spacer(modifier = Modifier.height(4.dp))

            // Nome oficial da instituição escolar.
            Text(
                text = "Escola Estadual Cívico-Militar\nMaria de Lima Cadidé", // Quebra de linha entre as frases.
                style = MaterialTheme.typography.titleMedium, // Tamanho médio para subtítulo.
                fontWeight = FontWeight.SemiBold, // Letras semi-negrito.
                color = SarcNavyDark, // Azul-marinho escuro para fácil leitura.
                textAlign = TextAlign.Center // Centraliza o texto no meio da tela.
            )

            // Espaço de 6 pixels antes do slogan.
            Spacer(modifier = Modifier.height(6.dp))

            // Significado completo da sigla SARC.
            Text(
                text = "Sistema de Acionamento e Resposta Cívico-Militar", // Slogan e descrição institucional.
                style = MaterialTheme.typography.bodyMedium, // Tamanho de corpo de texto.
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Cor suave para texto secundário.
                textAlign = TextAlign.Center // Texto centralizado.
            )

            // Espaço de 32 pixels separando o cabeçalho do formulário de acesso.
            Spacer(modifier = Modifier.height(32.dp))

            // ==========================================
            // Formulário de login (FR-02, AC-02)
            // ==========================================

            // Campo de entrada de texto com borda para o e-mail institucional.
            OutlinedTextField(
                value = email, // O valor atual que está guardado na memória.
                onValueChange = { email = it }, // Atualiza a memória toda vez que o usuário digita uma letra.
                label = { Text("E-mail Institucional") }, // Rótulo que fica no topo da caixa.
                placeholder = { Text("ex: docente@escola.gov.br") }, // Dica de exemplo quando a caixa estiver vazia.
                leadingIcon = { // Desenha o ícone no lado esquerdo dentro da caixa.
                    Icon(
                        imageVector = Icons.Default.Email, // Desenho de um envelope de correspondência.
                        contentDescription = "Ícone de E-mail", // Descrição de acessibilidade.
                        tint = SarcNavyPrimary // Cor azul-marinho no ícone.
                    )
                },
                singleLine = true, // Não permite pular linhas ao digitar o e-mail.
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Email, // Abre o teclado do celular com arroba e ponto facilitados.
                    imeAction = ImeAction.Next // Mostra o botão "Avançar" no teclado para ir para a senha.
                ),
                shape = RoundedCornerShape(12.dp), // Cantos da caixa levemente arredondados em 12 pixels.
                modifier = Modifier.fillMaxWidth() // Faz o campo ocupar toda a largura disponível.
            )

            // Espaço de 16 pixels entre o campo de e-mail e o campo de senha.
            Spacer(modifier = Modifier.height(16.dp))

            // Campo de entrada de texto com borda para a senha.
            OutlinedTextField(
                value = password, // O texto da senha guardado na memória.
                onValueChange = { password = it }, // Atualiza a senha conforme o usuário digita.
                label = { Text("Senha") }, // Rótulo da caixa.
                placeholder = { Text("Digite sua senha") }, // Texto explicativo temporário.
                leadingIcon = { // Ícone do lado esquerdo.
                    Icon(
                        imageVector = Icons.Default.Lock, // Desenho de um cadeado de segurança.
                        contentDescription = "Ícone de Senha", // Descrição para leitor de tela.
                        tint = SarcNavyPrimary // Cor azul-marinho.
                    )
                },
                trailingIcon = { // Botão clicável no lado direito da caixa para mostrar ou esconder a senha.
                    IconButton(onClick = { passwordVisible = !passwordVisible }) { // Alterna entre visível e invisível ao clicar.
                        Icon(
                            // Mostra o olhinho aberto ou o olhinho cortado dependendo do estado atual.
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Ocultar senha" else "Exibir senha",
                            tint = SarcNavyPrimary // Cor azul-marinho.
                        )
                    }
                },
                // Se a visibilidade estiver ligada mostra as letras reais, se não substitui por bolinhas.
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true, // Não permite pular linha na senha.
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Password, // Abre teclado próprio para senhas.
                    imeAction = ImeAction.Done // Mostra o botão "Concluído" no teclado.
                ),
                shape = RoundedCornerShape(12.dp), // Cantos arredondados da caixa.
                modifier = Modifier.fillMaxWidth() // Ocupa toda a largura da tela.
            )

            // Espaço de 24 pixels entre a senha e o botão principal.
            Spacer(modifier = Modifier.height(24.dp))

            // ==========================================
            // Botão primário: entrar (FR-03, AC-03)
            // ==========================================

            // Botão de destaque principal para acessar o sistema.
            Button(
                onClick = { onLoginClick(email, password) }, // Dispara a função enviando os dados digitados.
                modifier = Modifier
                    .fillMaxWidth() // Ocupa toda a largura da tela.
                    .height(52.dp), // Altura confortável de 52 pixels para toque com o dedo.
                shape = RoundedCornerShape(12.dp), // Cantos arredondados combinando com os campos.
                colors = ButtonDefaults.buttonColors(containerColor = SarcNavyPrimary) // Fundo azul-marinho forte.
            ) {
                // Texto dentro do botão principal.
                Text(
                    text = "Entrar", // Palavra de ação.
                    style = MaterialTheme.typography.titleMedium, // Letras de tamanho médio.
                    fontWeight = FontWeight.Bold, // Em negrito para dar destaque.
                    color = Color.White // Texto em cor branca para contraste perfeito.
                )
            }

            // Espaço de 16 pixels entre o botão entrar e o botão sobre.
            Spacer(modifier = Modifier.height(16.dp))

            // ==========================================
            // Botão informativo: sobre o SARC (FR-04, AC-04)
            // ==========================================

            // Botão secundário vazado com contorno para ver detalhes institucionais.
            OutlinedButton(
                onClick = { showAboutDialog = true }, // Ao clicar, ativa a janela modal na tela.
                modifier = Modifier
                    .fillMaxWidth() // Ocupa toda a largura.
                    .height(48.dp), // Altura de 48 pixels.
                shape = RoundedCornerShape(12.dp), // Cantos arredondados.
                border = BorderStroke(1.dp, SarcNavyPrimary) // Borda fina de 1 pixel em azul-marinho.
            ) {
                // Linha para colocar o ícone e o texto lado a lado horizontalmente.
                Row(verticalAlignment = Alignment.CenterVertically) { // Alinha tudo no centro da linha.
                    Icon(
                        imageVector = Icons.Default.Info, // Ícone de informação (letra "i" dentro de um círculo).
                        contentDescription = "Informações do SARC", // Acessibilidade.
                        tint = SarcNavyPrimary, // Cor azul-marinho.
                        modifier = Modifier.size(20.dp) // Tamanho de 20 pixels.
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espaço de 8 pixels entre o ícone e o texto.
                    Text(
                        text = "Sobre o SARC e Prioridades", // Texto do botão.
                        color = SarcNavyPrimary, // Cor azul-marinho.
                        fontWeight = FontWeight.SemiBold // Semi-negrito.
                    )
                }
            }

            // Espaço de 20 pixels antes do botão de cadastro.
            Spacer(modifier = Modifier.height(20.dp))

            // ==========================================
            // Botão de cadastro: solicitar acesso (FR-05, AC-05)
            // ==========================================

            // Botão em estilo de texto simples no rodapé para novos usuários.
            TextButton(
                onClick = {
                    showRegisterDialog = true // Abre a janela com instruções de cadastro.
                    onRegisterClick() // Notifica a ação de cadastro.
                },
                modifier = Modifier.fillMaxWidth() // Ocupa toda a largura.
            ) {
                // Linha contendo o ícone de adicionar pessoa e a frase.
                Row(verticalAlignment = Alignment.CenterVertically) { // Alinha os itens verticalmente no meio da linha.
                    Icon(
                        imageVector = Icons.Default.PersonAdd, // Ícone de pessoa com sinal de mais.
                        contentDescription = null, // Sem descrição adicional pois o texto já é autoexplicativo.
                        tint = SarcGreenSecondary, // Cor verde institucional.
                        modifier = Modifier.size(18.dp) // Tamanho de 18 pixels.
                    )
                    Spacer(modifier = Modifier.width(6.dp)) // Espaço de 6 pixels entre ícone e texto.
                    Text(
                        text = "Não possui acesso? Solicitar Cadastro", // Frase amigável de orientação.
                        style = MaterialTheme.typography.bodyMedium, // Estilo normal de corpo de texto.
                        fontWeight = FontWeight.SemiBold, // Semi-negrito.
                        color = SarcGreenSecondary // Cor verde institucional.
                    )
                }
            }
        }
    }

    // ==========================================
    // Diálogo modal: sobre o SARC (FR-04, AC-04)
    // ==========================================

    // Se o estado for verdadeiro, desenha a janela suspensa de explicação.
    if (showAboutDialog) {
        // Sprint 02 (FR-05, FR-06): guarda QUAL cartão de prioridade está aberto.
        // null = nenhum aberto; 1 a 4 = o nível aberto. Criado aqui dentro para voltar a null
        // sempre que o modal for fechado e aberto de novo.
        var expandedLevel by remember { mutableStateOf<Int?>(null) }

        // Regra do toque (FR-06): se o cartão tocado já está aberto, recolhe (null);
        // se não, abre este e, automaticamente, recolhe o anterior (só cabe um número no estado).
        val alternarNivel: (Int) -> Unit = { nivel ->
            expandedLevel = if (expandedLevel == nivel) null else nivel
        }

        AlertDialog(
            onDismissRequest = { showAboutDialog = false }, // Fecha a janela se o usuário tocar fora dela.
            containerColor = MaterialTheme.colorScheme.surface, // FR-08: fundo branco do tema (antes: cinza-lilás padrão #ECE6F0).
            icon = { // Ícone no topo da janela.
                Icon(
                    imageVector = Icons.Default.School, // Ícone de capelo de formatura representando a escola.
                    contentDescription = null, // Sem necessidade de leitor de tela pois o título explica.
                    tint = SarcNavyPrimary, // Cor azul-marinho.
                    modifier = Modifier.size(32.dp) // Tamanho de 32 pixels.
                )
            },
            title = { // Título da janela suspensa.
                Text(
                    text = "Sobre o SARC", // Título da janela.
                    style = MaterialTheme.typography.titleLarge, // Letras grandes de título.
                    fontWeight = FontWeight.Bold, // Em negrito.
                    color = SarcNavyPrimary, // Cor azul-marinho.
                    textAlign = TextAlign.Center // Centralizado no topo da janela.
                )
            },
            text = { // Conteúdo interno explicativo da janela.
                Column(
                    modifier = Modifier
                        .fillMaxWidth() // Ocupa toda a largura da janela.
                        .verticalScroll(rememberScrollState()) // Permite rolar se o texto for longo.
                ) {
                    // Parágrafo que resume a finalidade do aplicativo na escola.
                    Text(
                        text = "O SARC é o canal oficial de acionamento ágil e comunicação interna entre os professores e a Coordenação Militar da Escola Cadidé, garantindo suporte rápido e padronizado em ambiente escolar.",
                        style = MaterialTheme.typography.bodyMedium, // Tamanho de leitura agradável.
                        color = MaterialTheme.colorScheme.onSurfaceVariant // Cor neutra para texto corrido.
                    )

                    Spacer(modifier = Modifier.height(16.dp)) // Espaço de 16 pixels.
                    HorizontalDivider() // Linha fina divisória horizontal.
                    Spacer(modifier = Modifier.height(12.dp)) // Espaço de 12 pixels.

                    // Subtítulo da seção de prioridades.
                    Text(
                        text = "Faixas de Prioridade Escolar:", // Identificação da lista de cores.
                        style = MaterialTheme.typography.titleMedium, // Tamanho médio.
                        fontWeight = FontWeight.Bold, // Em negrito.
                        color = SarcNavyPrimary // Cor azul-marinho.
                    )

                    Spacer(modifier = Modifier.height(4.dp)) // Espaço de 4 dp.

                    // Instrução para o usuário saber que os cartões podem ser tocados (Sprint 02).
                    Text(
                        text = "Toque em um nível para ver quando utilizá-lo.",
                        style = MaterialTheme.typography.bodySmall, // Texto pequeno de apoio.
                        color = MaterialTheme.colorScheme.onSurfaceVariant // Cor secundária do tema.
                    )

                    Spacer(modifier = Modifier.height(10.dp)) // Espaço de 10 dp antes do primeiro cartão.

                    // Cartão verde para ocorrências simples.
                    PriorityBadge(
                        level = "Nível 1 - Apoio Pedagógico", // Título da faixa.
                        description = "Mediação de conflitos leves em sala de aula, assistência preventiva e suporte de rotina.", // Explicação da regra.
                        color = PriorityGreen, // Cor verde.
                        expanded = expandedLevel == 1, // Está aberto se o estado guarda o nível 1.
                        onClick = { alternarNivel(1) } // Toque: abre ou recolhe o nível 1.
                    )

                    Spacer(modifier = Modifier.height(8.dp)) // Espaço de 8 dp entre os cartões.

                    // Cartão amarelo para advertências e atenção.
                    PriorityBadge(
                        level = "Nível 2 - Atenção Disciplinar", // Título da faixa.
                        description = "Descumprimento reiterado de normas, condutas inadequadas e advertências formais.", // Explicação da regra.
                        color = PriorityYellow, // Cor amarela.
                        expanded = expandedLevel == 2,
                        onClick = { alternarNivel(2) }
                    )

                    Spacer(modifier = Modifier.height(8.dp)) // Espaço de 8 dp.

                    // Cartão laranja para apoio operacional militar urgente.
                    PriorityBadge(
                        level = "Nível 3 - Prioridade Operacional", // Título da faixa.
                        description = "Situações de desordem ou resistência que exigem presença militar imediata.", // Explicação da regra.
                        color = PriorityOrange, // Cor laranja.
                        expanded = expandedLevel == 3,
                        onClick = { alternarNivel(3) }
                    )

                    Spacer(modifier = Modifier.height(8.dp)) // Espaço de 8 dp.

                    // Cartão vermelho para emergências e risco grave.
                    PriorityBadge(
                        level = "Nível 4 - Urgência Crítica", // Título da faixa.
                        description = "Risco à integridade física, ameaça direta, emergências de saúde ou segurança.", // Explicação da regra.
                        color = PriorityRed, // Cor vermelha de perigo.
                        expanded = expandedLevel == 4,
                        onClick = { alternarNivel(4) }
                    )
                }
            },
            confirmButton = { // Botão de fechar no rodapé da janela.
                TextButton(onClick = { showAboutDialog = false }) { // Ao clicar, fecha a janela.
                    Text(
                        text = "Entendido", // Palavra de confirmação.
                        fontWeight = FontWeight.Bold, // Em negrito.
                        color = SarcNavyPrimary // Cor azul-marinho.
                    )
                }
            }
        )
    }

    // ==========================================
    // Diálogo modal: solicitação de cadastro (FR-05, AC-05)
    // ==========================================

    // Se o estado de cadastro for verdadeiro, desenha a janela com o guia.
    if (showRegisterDialog) {
        // Sprint 02 (FR-01 a FR-04): guarda em QUAL etapa do guia o usuário está (1, 2 ou 3).
        // Criado aqui dentro: quando o modal fecha, o valor é esquecido e, ao reabrir, volta para 1.
        var registerStep by remember { mutableStateOf(1) }

        // Conteúdo das 3 etapas: cada par é (título da etapa, texto da etapa).
        val etapas = listOf(
            "Quem pode solicitar" to
                "O acesso ao SARC é restrito a servidores e docentes credenciados da Escola Estadual Cívico-Militar Maria de Lima Cadidé: professores, monitores da Coordenação Militar e equipe gestora.",
            "Como funciona a aprovação" to
                "Você preenche seus dados na solicitação de cadastro e o pedido é enviado à Coordenação Militar, que o analisa pelo próprio SARC. Somente após essa homologação o seu acesso é liberado.",
            "O que fazer depois" to
                "Após enviar sua solicitação, aguarde a aprovação da Coordenação Militar. Se preferir, procure a Coordenação para informar que já fez o pedido e está aguardando a liberação. O formulário de solicitação estará disponível em breve no aplicativo."
        )
        val totalEtapas = etapas.size // 3 etapas.
        val (tituloEtapa, textoEtapa) = etapas[registerStep - 1] // Pega a etapa atual (a lista começa no 0).

        AlertDialog(
            onDismissRequest = { showRegisterDialog = false }, // Fecha se tocar fora da janela ou apertar Voltar do celular.
            containerColor = MaterialTheme.colorScheme.surface, // FR-08: fundo branco do tema (antes: cinza-lilás padrão #ECE6F0).
            icon = { // Ícone superior.
                Icon(
                    imageVector = Icons.Default.PersonAdd, // Ícone de cadastro de nova pessoa.
                    contentDescription = null,
                    tint = SarcGreenSecondary, // Cor verde institucional.
                    modifier = Modifier.size(32.dp) // Tamanho de 32 pixels.
                )
            },
            title = { // Título da janela.
                Text(
                    text = "Solicitação de Cadastro", // Texto do título.
                    style = MaterialTheme.typography.titleLarge, // Tamanho grande.
                    fontWeight = FontWeight.Bold, // Negrito.
                    color = SarcNavyPrimary, // Cor azul-marinho.
                    textAlign = TextAlign.Center // Centralizado.
                )
            },
            text = { // Corpo do guia: progresso + conteúdo da etapa atual.
                Column(modifier = Modifier.fillMaxWidth()) { // Organiza os itens um embaixo do outro.
                    // FR-03: indicador em texto ("Etapa 2 de 3").
                    Text(
                        text = "Etapa $registerStep de $totalEtapas", // $ coloca o valor do estado dentro do texto.
                        style = MaterialTheme.typography.labelLarge, // Estilo de etiqueta.
                        color = MaterialTheme.colorScheme.onSurfaceVariant // Cor secundária do tema.
                    )
                    Spacer(modifier = Modifier.height(6.dp)) // Espaço de 6 dp.
                    // FR-03: barra de progresso proporcional à etapa (1/3, 2/3, 3/3).
                    LinearProgressIndicator(
                        progress = { registerStep / totalEtapas.toFloat() }, // Fração preenchida da barra.
                        modifier = Modifier.fillMaxWidth(), // Ocupa toda a largura do modal.
                        color = SarcGreenSecondary, // Parte preenchida em verde (cor do cadastro).
                        trackColor = SarcGreenSecondary.copy(alpha = 0.2f) // Trilho em verde clarinho.
                    )
                    Spacer(modifier = Modifier.height(16.dp)) // Espaço de 16 dp.
                    // FR-01: título da etapa atual.
                    Text(
                        text = tituloEtapa,
                        style = MaterialTheme.typography.titleMedium, // Subtítulo.
                        fontWeight = FontWeight.Bold, // Negrito.
                        color = SarcNavyDark // Azul-marinho escuro.
                    )
                    Spacer(modifier = Modifier.height(8.dp)) // Espaço de 8 dp.
                    // FR-01 e FR-10: texto da etapa atual, na linguagem do usuário.
                    Text(
                        text = textoEtapa,
                        style = MaterialTheme.typography.bodyMedium, // Texto padrão de leitura.
                        color = MaterialTheme.colorScheme.onSurfaceVariant // Cor neutra suave.
                    )
                }
            },
            confirmButton = { // FR-02 e FR-04: botão principal ("Próximo" ou "Entendi").
                TextButton(
                    onClick = {
                        if (registerStep < totalEtapas) {
                            registerStep++ // Avança uma etapa (1 → 2 → 3).
                        } else {
                            showRegisterDialog = false // Na última etapa, fecha o guia.
                        }
                    }
                ) {
                    Text(
                        text = if (registerStep < totalEtapas) "Próximo" else "Entendi", // Muda na última etapa.
                        fontWeight = FontWeight.Bold, // Negrito.
                        color = SarcNavyPrimary // Cor azul-marinho.
                    )
                }
            },
            // FR-02: botão "Voltar", que só existe a partir da etapa 2 (na etapa 1, o espaço fica vazio: null).
            dismissButton = if (registerStep > 1) {
                {
                    TextButton(onClick = { registerStep-- }) { // Volta uma etapa (3 → 2 → 1).
                        Text(
                            text = "Voltar",
                            color = SarcNavyPrimary // Cor azul-marinho.
                        )
                    }
                }
            } else {
                null
            }
        )
    }
}

/**
 * Componente visual reutilizável para desenhar cada um dos cartões coloridos de prioridade.
 */
@Composable
private fun PriorityBadge(
    level: String, // O nome da faixa de prioridade recebido por parâmetro.
    description: String, // A descrição do que aquela faixa significa.
    color: Color, // A cor específica (verde, amarelo, laranja ou vermelho).
    expanded: Boolean, // Sprint 02: true = cartão aberto (mostra a descrição); false = recolhido.
    onClick: () -> Unit // Sprint 02: o que fazer quando o cartão for tocado (decidido por quem chama).
) {
    // Cartão tocável (Card com onClick já dá o efeito de toque) e fundo translúcido na cor da prioridade.
    Card(
        onClick = onClick, // FR-05: tocar no cartão avisa o modal, que muda o estado expandedLevel.
        modifier = Modifier.fillMaxWidth(), // Ocupa toda a largura da coluna.
        shape = RoundedCornerShape(8.dp), // Cantos arredondados de 8 dp.
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.12f)), // Fundo bem clarinho com 12% da cor.
        border = if (expanded) {
            BorderStroke(2.dp, color) // FR-07: aberto = borda mais grossa, com a cor cheia.
        } else {
            BorderStroke(1.dp, color.copy(alpha = 0.5f)) // Recolhido = contorno suave com 50% da cor.
        }
    ) {
        // Coluna interna: a linha do título em cima e, se aberto, a descrição embaixo.
        Column(
            modifier = Modifier
                .fillMaxWidth() // Ocupa toda a largura interna do cartão.
                .padding(10.dp) // Espaçamento interno de 10 dp em volta de tudo.
        ) {
            // Linha com bolinha, nome do nível e seta.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 28.dp), // NFR-01: 28 + 10 + 10 de padding = 48 dp de área de toque.
                verticalAlignment = Alignment.CenterVertically // Tudo alinhado pelo meio da linha.
            ) {
                // Bolinha circular colorida que representa a cor da prioridade.
                Box(
                    modifier = Modifier
                        .size(12.dp) // Tamanho de 12 por 12 dp.
                        .clip(CircleShape) // Corta no formato de círculo perfeito.
                        .background(color) // Pinta com a cor da prioridade.
                )
                Spacer(modifier = Modifier.width(10.dp)) // Espaço de 10 dp entre a bolinha e o texto.
                Text(
                    text = level, // Exibe o nível (ex: Nível 1 - Apoio Pedagógico).
                    style = MaterialTheme.typography.labelLarge, // Tamanho de etiqueta.
                    fontWeight = FontWeight.Bold, // Em negrito para destaque.
                    color = SarcNavyDark, // FR-09: azul-marinho escuro (contraste acima de 11:1); a cor do nível fica na bolinha, borda e fundo.
                    modifier = Modifier.weight(1f) // Ocupa o espaço que sobra, empurrando a seta para a direita.
                )
                // FR-07: seta que indica se o cartão está recolhido (▼) ou aberto (▲).
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Recolher descrição" else "Ver descrição", // Leitor de tela.
                    tint = SarcNavyDark // Mesma cor do nome do nível.
                )
            }
            // FR-05: a descrição só aparece quando o cartão está aberto, com uma animação suave.
            AnimatedVisibility(visible = expanded) {
                Text(
                    text = description, // Exibe a descrição detalhada da prioridade.
                    style = MaterialTheme.typography.bodySmall, // Letra menor para caber confortavelmente.
                    color = MaterialTheme.colorScheme.onSurface, // Cor de texto padrão do tema.
                    modifier = Modifier.padding(start = 22.dp, top = 4.dp) // Alinha o texto com o nome (depois da bolinha).
                )
            }
        }
    }
}

/**
 * Função de pré-visualização no Android Studio.
 * Permite ver o design da tela no editor sem precisar compilar no celular.
 */
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    SARCTheme { // Aplica o tema visual oficial do SARC.
        WelcomeScreen() // Renderiza a tela completa na prévia.
    }
}
