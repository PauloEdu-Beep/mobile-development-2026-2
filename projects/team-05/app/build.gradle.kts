// Arquivo de build do PROJETO inteiro (o "guarda-chuva" acima do módulo app).
// Aqui só se declaram os plugins que os módulos vão usar, sem aplicá-los ainda.
plugins {
    // Plugin que sabe montar um aplicativo Android (gera o APK). "apply false" = só disponibiliza, quem aplica é o módulo app.
    alias(libs.plugins.android.application) apply false
    // Plugin do compilador Kotlin para o Jetpack Compose (transforma as funções @Composable em telas).
    alias(libs.plugins.kotlin.compose) apply false
}
