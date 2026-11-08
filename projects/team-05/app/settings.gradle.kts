// Configurações gerais do Gradle: de onde baixar as peças (bibliotecas e plugins) e quais módulos fazem parte do projeto.

// De onde o Gradle baixa os PLUGINS (ferramentas que montam o app).
pluginManagement {
    repositories {
        // Repositório do Google: só para peças do Android, do Google e do AndroidX (o filtro deixa a busca mais rápida e segura).
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()       // Maior repositório público de bibliotecas Java/Kotlin.
        gradlePluginPortal() // Loja oficial de plugins do Gradle.
    }
}
plugins {
    // Permite ao Gradle baixar sozinho a versão certa do Java (toolchain), se o computador não tiver.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
// De onde o Gradle baixa as BIBLIOTECAS usadas pelo código do app.
dependencyResolutionManagement {
    // Obriga todos os módulos a usarem só os repositórios listados aqui (evita fontes desconhecidas).
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()       // Bibliotecas do Android e do Jetpack Compose.
        mavenCentral() // Demais bibliotecas (ex.: JUnit para testes).
    }
}

rootProject.name = "SARC" // Nome do projeto exibido no Android Studio.
include(":app")           // O projeto tem um módulo: a pasta app/, onde fica o aplicativo.
