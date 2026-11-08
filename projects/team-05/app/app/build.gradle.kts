// Arquivo de build do MÓDULO app: diz como montar o aplicativo SARC e quais bibliotecas ele usa.

plugins {
    alias(libs.plugins.android.application) // Este módulo é um aplicativo Android (gera o APK).
    alias(libs.plugins.kotlin.compose)      // Ativa o compilador do Jetpack Compose para as telas.
}

android {
    namespace = "com.team05.sarc" // "Endereço" do código Kotlin (mesmo nome dos pacotes em java/com/team05/sarc).
    compileSdk {
        version = release(37) // Versão do Android usada para compilar (API 37).
    }

    defaultConfig {
        applicationId = "com.team05.sarc" // Identidade única do app no celular e na loja.
        minSdk = 24        // Android mais antigo suportado (7.0).
        targetSdk = 37     // Android para o qual o app foi testado (o emulador Pixel 8 usa API 37).
        versionCode = 1    // Número interno da versão (sobe a cada publicação).
        versionName = "1.0" // Versão mostrada ao usuário.

        // Ferramenta que executa os testes instrumentados (testes que rodam no emulador).
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        // Versão de publicação (release): otimização/encolhimento do código desligada por enquanto.
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        // O código é compilado no padrão do Java 11.
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true // Liga o Jetpack Compose neste módulo.
    }
}

// Bibliotecas usadas pelo app. Os nomes "libs.xxx" vêm do catálogo gradle/libs.versions.toml.
dependencies {
    // BOM ("lista de materiais"): escolhe versões do Compose que funcionam juntas, sem precisar escrever versão em cada uma.
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)                // Liga a Activity (MainActivity) ao Compose (setContent).
    implementation(libs.androidx.compose.material3)               // Componentes Material 3: Button, Card, AlertDialog, LinearProgressIndicator...
    implementation(libs.androidx.compose.material.icons.extended) // Pacote completo de ícones (e-mail, cadeado, olho, setas...).
    implementation(libs.androidx.compose.ui)                      // Base do Compose: Modifier, layout, desenho.
    implementation(libs.androidx.compose.ui.graphics)             // Cores e formas (Color, CircleShape...).
    implementation(libs.androidx.compose.ui.tooling.preview)      // Anotação @Preview (pré-visualizar a tela no Android Studio).
    implementation(libs.androidx.core.ktx)                        // Atalhos Kotlin para o Android (inclui o enableEdgeToEdge).
    implementation(libs.androidx.lifecycle.runtime.ktx)           // Ciclo de vida da tela (quando abre, pausa, fecha).
    implementation(libs.androidx.navigation.compose)              // Navegação entre telas: NavHost e NavController (Sprint 03).
    testImplementation(libs.junit)                                // Testes de unidade no computador.
    androidTestImplementation(platform(libs.androidx.compose.bom)) // Mesmas versões do Compose para os testes.
    androidTestImplementation(libs.androidx.compose.ui.test.junit4) // Testes de tela do Compose no emulador.
    androidTestImplementation(libs.androidx.espresso.core)        // Ferramenta de testes de interface do Android.
    androidTestImplementation(libs.androidx.junit)                // JUnit adaptado para testes no Android.
    debugImplementation(libs.androidx.compose.ui.test.manifest)   // Apoio aos testes de tela (só na versão de depuração).
    debugImplementation(libs.androidx.compose.ui.tooling)         // Ferramentas do @Preview e do inspetor de layout (só depuração).
}
