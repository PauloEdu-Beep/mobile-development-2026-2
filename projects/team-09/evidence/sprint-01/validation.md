# Validação — Sprint 01

Executada em 28/09/2026 pelo Codex, em Pixel 6 virtual Android 15/API 35 (x86_64), com o APK desta branch. Evidências são capturas reais via ADB, não mockups. Revisão pessoal pelos alunos ainda pendente.

Comando: `gradlew.bat assembleDebug connectedDebugAndroidTest --console=plain`.
Resultado: **BUILD SUCCESSFUL**, testes instrumentados aprovados. Logs completos em [build-and-tests.txt](build-and-tests.txt); resultados JUnit em [instrumented-tests.xml](instrumented-tests.xml).

| Critério da SPEC | Resultado | Como foi verificado |
| --- | --- | --- |
| AC-01 | PASS | Nome na abertura, assertIsDisplayed e inspeção da captura. |
| AC-02 | PASS | Slogan e descrição presentes, teste e inspeção. |
| AC-03 | PASS | Ação visível e tocável; continua na mesma tela. |
| AC-04 | PASS | Execução no emulador sem crash; Scaffold/insets e rolagem conferidos. |

## Evidências

![first-screen.png](first-screen.png)
