# SPRINT 00 — Development Environment Report

## Team Identification

- **Team Number:** Team 05
- **Project / App Name:** SARC (Sistema de Acionamento e Resposta Cívico-Militar)
- **Members:**
  - Matheus Gabriel de Morais Barros ([@MatheusGabriel-25](https://github.com/MatheusGabriel-25))
  - Paulo Vitor ([@PAULOSANTOS1309](https://github.com/PAULOSANTOS1309))

---

## Development Environment Status

| Component             |    Status     | Details                                                                 |
| :-------------------- | :-----------: | :---------------------------------------------------------------------- |
| **Android Studio**    | ✅ Configurado | Android Studio Ladybug / Koala Feature Drop instalado                   |
| **Android SDK**       | ✅ Configurado | SDK Platform 34 / 37, Build Tools e Platform-Tools instalados           |
| **Kotlin & Compose**  | ✅ Configurado | Kotlin 2.x + Jetpack Compose (Material 3)                               |
| **Device / Emulator** | ✅ Configurado | Emulador Android Virtual Device (AVD) e dispositivo físico configurados |
| **Git & GitHub**      | ✅ Configurado | Fork realizado, remotes origin e upstream configurados                  |

---

## Acceptance Criteria Validation

- [x] **AC-01** — Team members are identified (Matheus Gabriel & Paulo Vitor).
- [x] **AC-02** — Git is correctly configured (`user.name`, `user.email`, credential helper).
- [x] **AC-03** — The course repository has been forked (`MatheusGabriel-25/mobile-development-2026-2`).
- [x] **AC-04** — A Sprint branch has been created (`team-05-sprint-00`).
- [x] **AC-05** — Android Studio is installed.
- [x] **AC-06** — Android SDK is configured.
- [x] **AC-07** — An Android Emulator or physical device is available.
- [x] **AC-08** — An Android application builds successfully (`com.team05.sarc`).
- [x] **AC-09** — The application runs without crashing.
- [x] **AC-10** — At least one commit has been created.
- [x] **AC-11** — A Pull Request has been submitted (PR #7 merged into upstream/main).

---

## Difficulties Encountered & Resolutions

1. **Configuração de Remotes Git:**
   - *Desafio:* Sincronizar o fork pessoal (`origin`) com o repositório principal da disciplina (`upstream`).
   - *Solução:* Configuração correta de múltiplos remotes no Git e definição do tracking branch na branch de trabalho.

2. **Geração do Projeto Inicial no Android Studio:**
   - *Desafio:* Configuração inicial do template Kotlin + Jetpack Compose com suporte a Material 3.
   - *Solução:* Inicialização do módulo `com.team05.sarc` e validação da compilação inicial e execução no emulador.

---

## AI Usage

- **Tool:** Google Antigravity (Gemini).
- **Purpose:** Apoio na estruturação da documentação formal e auditoria de conformidade com os critérios do curso.
- **Generated Content:** Rascunho inicial do relatório de ambiente e mapeamento de critérios de aceitação.
- **Human Changes:** Validação de ambiente, revisão dos nomes dos integrantes e conferência técnica dos requisitos.

---

## Execution Evidence

A aplicação inicial foi executada com sucesso no ambiente Android Studio, validando a compilação do pacote `com.team05.sarc` e o ciclo de vida da `MainActivity` com Jetpack Compose.
