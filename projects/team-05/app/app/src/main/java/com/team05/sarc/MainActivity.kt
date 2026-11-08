package com.team05.sarc

// Importação das ferramentas do Android para criar a "janela" principal do aplicativo.
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

// Importação dos componentes do Jetpack Compose usados para montar o fundo da tela.
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

// Importação da navegação entre telas (Sprint 03).
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Importação das telas e do tema visual criados pela equipe.
import com.team05.sarc.ui.screens.HomeDocenteScreen
import com.team05.sarc.ui.screens.WelcomeScreen
import com.team05.sarc.ui.theme.SARCTheme

/**
 * Porta de entrada do aplicativo SARC.
 * Quando o usuário toca no ícone do app, o Android abre esta Activity primeiro
 * (isso está configurado no arquivo AndroidManifest.xml).
 */
class MainActivity : ComponentActivity() {

    // Função chamada automaticamente pelo Android no momento em que a tela é criada.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Executa a preparação padrão do Android antes do nosso código.

        enableEdgeToEdge() // Permite que o app desenhe de ponta a ponta, inclusive atrás da barra de status.

        // A partir daqui, o conteúdo da tela é desenhado com Jetpack Compose (e não com XML).
        setContent {
            // Aplica as cores e fontes institucionais do SARC em tudo que estiver dentro.
            SARCTheme {
                // Folha de fundo que ocupa a tela inteira com a cor de fundo do tema.
                Surface(
                    modifier = Modifier.fillMaxSize(), // Ocupa 100% da largura e altura.
                    color = MaterialTheme.colorScheme.background // Cor de fundo definida no Theme.kt.
                ) {
                    // Sprint 03: o NavController lembra em qual tela o usuário está.
                    val navController = rememberNavController()

                    // O NavHost é o "mapa" das telas: 2 rotas, começando no Login ("welcome").
                    NavHost(navController = navController, startDestination = "welcome") {

                        // Rota "welcome": a tela de login das Sprints 01 e 02.
                        composable("welcome") {
                            WelcomeScreen(
                                // "Entrar" abre o painel (o Login fica guardado embaixo, na pilha).
                                onLoginClick = { _, _ -> navController.navigate("home") }
                            )
                        }

                        // Rota "home": o Painel do Docente (ui/screens/HomeDocenteScreen.kt).
                        composable("home") {
                            HomeDocenteScreen(
                                // "Sair" tira o painel da pilha e volta ao Login.
                                onSair = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
