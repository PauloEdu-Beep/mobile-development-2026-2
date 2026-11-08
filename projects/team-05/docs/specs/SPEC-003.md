# SPEC-003 — Navegação Login → Painel do Docente

> **Team:** Team 05  
> **Sprint:** Sprint 03 — Navigation & Multiple Screens  
> **Status:** Validated  
> **Related Sprint:** `SPRINT-03.md`  
> **Base:** `SPEC-001.md` (tela de login) e `SPEC-002.md` (modais interativos)

---

## 1. Context

**Problem:**

Hoje o SARC tem **uma única tela**: o login (`WelcomeScreen`). O botão "Entrar" existe, mas não leva a lugar nenhum, então o professor não tem como chegar ao ponto principal do app: acionar a Coordenação Militar.

**User / Actor:**

O **docente** da Escola Cadidé, que entra no app para acionar a Coordenação Militar.

**Usage Context:**

Logo depois de abrir o aplicativo: o professor toca em "Entrar" e precisa ver o seu painel de trabalho. Quando termina, toca em "Sair" e volta ao login.

---

## 2. Objective

O objetivo desta funcionalidade é transformar o SARC em um aplicativo de **duas telas**, ligando o **Login** ao **Painel do Docente** com **Navigation Compose** (`NavController` + `NavHost`), com ida pelo botão "Entrar" e volta pelo botão "Sair".

---

## 3. User Scenario

**Given** que o professor abriu o aplicativo e está na tela de Login,  
**When** ele toca em **"Entrar"**,  
**Then** o aplicativo mostra o **Painel do Docente**.

**Given** que o professor está no Painel do Docente,  
**When** ele toca em **"Sair"**,  
**Then** o aplicativo volta para a tela de Login.

---

## 4. Functional Requirements

### FR-01 — Navegação com duas rotas

O aplicativo deve usar um `NavHost` controlado por um `NavController`, com **2 destinos**: `welcome` (Login) e `home` (Painel do Docente). O destino inicial é `welcome`.

### FR-02 — Entrar abre o painel

Ao tocar em **"Entrar"** na tela de Login, o aplicativo deve navegar para a rota `home`. O botão não valida e-mail nem senha nesta Sprint.

### FR-03 — Título do painel

O Painel do Docente deve exibir o título **"Painel do Docente"** e o subtítulo **"Acione a Coordenação em poucos toques"**.

### FR-04 — Legenda dos 4 níveis

O Painel do Docente deve exibir a **legenda dos 4 níveis de prioridade** (nome e cor de cada nível, somente para consulta), usando as cores do tema do SARC.

### FR-05 — Meus acionamentos vazia

O Painel do Docente deve exibir a seção **"Meus acionamentos"** **sem nenhum item**, com a mensagem **"Nenhum acionamento ainda"**. Não há exemplos nem dados de teste.

### FR-06 — Novo acionamento mostra aviso

Ao tocar em **"Novo acionamento"**, o aplicativo deve abrir um aviso com o texto **"O formulário de acionamento chega na Sprint 04"**, que fecha ao toque em um botão.

### FR-07 — Sair volta ao Login

Ao tocar em **"Sair"** no Painel do Docente, o aplicativo deve voltar à tela de Login.

---

## 5. Constraints

### Required Technologies

- Kotlin 2.2.10
- Jetpack Compose com Material Design 3 (Compose BOM 2026.02.01)
- `androidx.navigation:navigation-compose` (única dependência nova)
- Android Studio

### Project Constraints

- `minSdk = 24`, `compileSdk = 37` e `targetSdk = 37` permanecem iguais.
- A navegação usa **apenas** `NavHost`, `NavController` e `composable(...)` com as rotas `welcome` e `home`.
- Ao entrar, a rota `home` é colocada por cima do Login na pilha de telas; ao sair, `popBackStack()` tira o painel da pilha e o Login volta a aparecer.
- Cada tela fica em **arquivo próprio**: `WelcomeScreen.kt` (já existe) e `HomeDocenteScreen.kt` (novo).
- O estado do aviso "Novo acionamento" usa `remember { mutableStateOf(...) }`, como na Sprint 02.

### Non-Functional Requirements

- **NFR-01 — Área de toque:** botões com altura mínima de **48 dp**.
- **NFR-02 — Legibilidade:** textos com contraste **≥ 4,5:1** (WCAG) no modo claro.

### Files or Modules That Must Not Be Modified

- Os dois modais da Sprint 02 (lógica e textos), `Theme.kt`, `Color.kt`, `Type.kt` e `AndroidManifest.xml`.
- Pastas de outros times (`projects/team-XX/`) e arquivos da raiz do repositório.

**Arquivos que mudam:** `MainActivity.kt` (passa a conter o `NavHost`), `libs.versions.toml` e `app/build.gradle.kts` (dependência). `WelcomeScreen.kt` já recebe `onLoginClick`, então a princípio não muda.  
**Arquivo novo:** `ui/screens/HomeDocenteScreen.kt`.

---

## 6. Out of Scope

- Login real, validação de e-mail e senha, perfis de Monitor e Gestão.
- **Formulário de acionamento** e sua validação — Sprint 04.
- **Lista de acionamentos preenchida**, salvar dados e banco de dados — Sprints 04 e 05.
- Internet, Supabase e notificações — Sprint 06.
- Mudar os modais da Sprint 02, o tema, as cores ou a fonte.
- Animações de transição entre telas e suporte completo ao modo escuro.

---

## 7. Acceptance Criteria

### AC-01 — Duas rotas
**Given** o app aberto, **When** o código é inspecionado, **Then** existe um `NavHost` com as rotas `welcome` e `home`, e `welcome` é a primeira tela exibida.

### AC-02 — Entrar abre o painel
**Given** a tela de Login, **When** o professor toca em "Entrar" (com ou sem e-mail e senha), **Then** o Painel do Docente é exibido.

### AC-03 — Título do painel
**Given** o Painel do Docente aberto, **When** a tela é exibida, **Then** aparecem "Painel do Docente" e "Acione a Coordenação em poucos toques".

### AC-04 — Legenda dos níveis
**Given** o Painel do Docente aberto, **When** a tela é exibida, **Then** aparecem os 4 níveis, cada um com seu nome e sua cor do tema.

### AC-05 — Lista vazia
**Given** o Painel do Docente aberto, **When** a tela é exibida, **Then** "Meus acionamentos" mostra "Nenhum acionamento ainda" e nenhum item de lista.

### AC-06 — Aviso do novo acionamento
**Given** o Painel do Docente aberto, **When** o professor toca em "Novo acionamento", **Then** o aviso "O formulário de acionamento chega na Sprint 04" aparece e fecha ao toque no botão do aviso.

### AC-07 — Sair
**Given** o Painel do Docente aberto, **When** o professor toca em "Sair", **Then** a tela de Login é exibida.

### AC-08 — Não-regressão da Sprint 02
**Given** a tela de Login, **When** o professor abre "Sobre o SARC" e "Solicitar Cadastro", **Then** os modais funcionam como na Sprint 02 (cartões expansíveis e guia de 3 etapas).

### AC-09 — Estabilidade
**Given** o app instalado, **When** o professor repete 10 vezes o ciclo Entrar → Sair, **Then** o app compila (`./gradlew assembleDebug`), roda e não fecha sozinho.

### AC-10 — Cores dos níveis
**Given** o Painel do Docente aberto, **When** a legenda é exibida, **Then** as cores são as do tema (verde, amarelo, laranja e vermelho) e os nomes têm contraste ≥ 4,5:1.

---

## 8. Requirement Traceability

| Requirement | Implementação | AC | Evidência |
| --- | --- | --- | --- |
| FR-01 | `MainActivity.kt` L48 — `rememberNavController()`; L51 — `NavHost(startDestination = "welcome")`; L54 e L62 — `composable("welcome")` e `composable("home")` | AC-01 | `screen-a.png` |
| FR-02 | `MainActivity.kt` L57 — `onLoginClick` chama `navController.navigate("home")` | AC-02 | `screen-a.png`, `screen-b.png` |
| FR-03 | `HomeDocenteScreen.kt` L69–76 — título e subtítulo | AC-03 | `screen-b.png` |
| FR-04 | `HomeDocenteScreen.kt` L86–91 — 4 chamadas de `NivelLegenda`; L125 — função `NivelLegenda` (bolinha na cor do nível + nome em `SarcNavyDark`) | AC-04, AC-10 | `screen-b.png` |
| FR-05 | `HomeDocenteScreen.kt` L93–95 — "Meus acionamentos" e "Nenhum acionamento ainda" | AC-05 | `screen-b.png` |
| FR-06 | `HomeDocenteScreen.kt` L60 — estado `showAviso`; L78–84 — botão "Novo acionamento"; L108–117 — `AlertDialog` com "Entendi" | AC-06 | `home-aviso.png` |
| FR-07 | `HomeDocenteScreen.kt` L99–105 — botão "Sair"; `MainActivity.kt` L65 — `onSair` chama `navController.popBackStack()` | AC-07 | `home-sair.png` |

---

## 9. Implementation Plan

### Components to Create or Modify

1. **`libs.versions.toml` e `app/build.gradle.kts`:** adicionar `navigation-compose`.
2. **`MainActivity.kt`:** criar o `NavController` (`rememberNavController()`) e o `NavHost(startDestination = "welcome")`.
   - `composable("welcome")` → `WelcomeScreen(onLoginClick = { _, _ -> navController.navigate("home") })`
   - `composable("home")` → `HomeDocenteScreen(onSair = { navController.popBackStack() })`
3. **`HomeDocenteScreen.kt` (novo):** título e subtítulo, botão "Novo acionamento" com aviso, legenda dos 4 níveis, seção "Meus acionamentos" vazia e botão "Sair".

### Expected Interaction Flow

```text
Abrir o app → [welcome]
   → "Entrar" → navigate("home") → [welcome, home]  (o painel fica por cima do Login)
[home] → "Sair" → popBackStack() → [welcome]
[home] → "Novo acionamento" → showAviso = true → aviso → botão fecha → showAviso = false
```

### Data or State Required

| Estado | Tipo | Valor inicial | Onde é criado | Muda quando |
| --- | --- | --- | --- | --- |
| `navController` | `NavHostController` | rota `welcome` | `MainActivity.kt` | "Entrar" e "Sair" |
| `showAviso` | `Boolean` | `false` | `HomeDocenteScreen.kt` | toque em "Novo acionamento" ou no botão do aviso |

Os estados da Sprint 01 e da Sprint 02 permanecem sem alteração.

---

## 10. Validation Plan

The team must:

1. Compilar com `./gradlew assembleDebug`.
2. Rodar no emulador Pixel 8 (API 37).
3. Fluxo: abrir → Login → "Entrar" → Painel (`screen-a.png`, `screen-b.png`).
4. No Painel, tocar em "Sair" e confirmar que o Login aparece (`home-sair.png`).
5. Tocar em "Novo acionamento" e conferir o aviso (`home-aviso.png`).
6. Abrir os dois modais do Login e conferir que a Sprint 02 continua igual (`sprint-02-regression.png`).
7. Repetir Entrar → Sair 10 vezes e conferir o logcat (sem `FATAL EXCEPTION`).

---

## 11. Validation Results

| AC | Resultado | Como foi verificado |
| --- | --- | --- |
| AC-01 | PASS | Código: `NavHost` com as rotas `welcome` e `home`; ao abrir o app, a primeira tela é o Login |
| AC-02 | PASS | Toque em "Entrar" com os campos vazios → Painel do Docente exibido |
| AC-03 | PASS | "Painel do Docente" e "Acione a Coordenação em poucos toques" visíveis |
| AC-04 | PASS | Os 4 níveis aparecem, cada um com bolinha na cor do nível |
| AC-05 | PASS | "Meus acionamentos" mostra "Nenhum acionamento ainda", sem itens |
| AC-06 | PASS | "Novo acionamento" abre o aviso "O formulário de acionamento chega na Sprint 04"; "Entendi" fecha o aviso |
| AC-07 | PASS | "Sair" no Painel → tela de Login exibida |
| AC-08 | PASS | Modal "Sobre o SARC": cartão do Nível 4 expande com a descrição; modal de cadastro: "Etapa 1 de 3" → "Próximo" → "Etapa 2 de 3" com "Voltar" |
| AC-09 | PASS | `./gradlew assembleDebug` — BUILD SUCCESSFUL; 10 ciclos Entrar → Sair seguidos; 0 `FATAL EXCEPTION` no logcat |
| AC-10 | PASS | Cores vindas do tema (`PriorityGreen`, `PriorityYellow`, `PriorityOrange`, `PriorityRed`); nomes em `SarcNavyDark` (`#0F2042`) sobre o fundo `#F8F9FA`, contraste ≈ 15:1 |

### Environment Used for Validation

- **Device:** Android Studio Emulator — Pixel 8
- **Android version / API:** API 37
- **Build result:** PASS (`./gradlew assembleDebug` — BUILD SUCCESSFUL)
- **Application execution:** PASS (APK instalado e testado no emulador Pixel 8, API 37)

---

## 12. Evidence

Em `projects/team-05/evidence/sprint-03/`:

| Arquivo | O que mostra | Prova |
| --- | --- | --- |
| **`screen-a.png`** (obrigatório) | tela de Login (rota `welcome`) | tela de origem |
| **`screen-b.png`** (obrigatório) | Painel do Docente (rota `home`) | tela de destino e seu conteúdo |
| `home-aviso.png` | aviso de "Novo acionamento" aberto | FR-06 |
| `home-sair.png` | Login exibido depois de tocar em "Sair" | FR-07 (volta) |
| `sprint-02-regression.png` | modal "Sobre o SARC" com o Nível 4 aberto | não-regressão da Sprint 02 |

---

## 13. AI-Assisted Development

AI tools may be used, but the team remains responsible for the final solution.

### AI Tool(s)

- **Tool:** Claude (Claude Code)
- **Model/version, if known:** Claude Opus 5.5 e Claude Sonnet 5.5

### How AI Was Used

- [x] Understanding the requirement
- [x] Refining the specification
- [x] Generating implementation suggestions
- [x] Explaining code
- [ ] Debugging
- [ ] Refactoring
- [ ] Generating test ideas
- [x] Reviewing acceptance criteria
- [x] Other: instalação do ambiente (Android Studio, SDK e emulador) e captura das evidências via ADB

### Prompt or Request Summary

A equipe conversou com a IA para ler o `SPRINT-03.md` do curso e definir as telas, com base no protocolo de acionamento do Paulo e no `SPRINT-01.md`. A SPEC-003 foi montada em conjunto, a partir das decisões da equipe, e a navegação Login → Painel do Docente foi implementada com o mínimo de código.

### AI-Generated or Suggested Content

A IA propôs a estrutura desta especificação, o `NavHost` com as rotas `welcome` e `home`, a nova tela `HomeDocenteScreen.kt` e o plano de validação. Na primeira versão, sugeriu tirar o Login da pilha (`popUpTo`) para que o voltar do Android fechasse o app.

### Human Review and Changes

O Matheus propôs o fluxo e as telas e definiu o escopo (só o Painel do Docente, sem formulário nem banco), escolhendo com a IA o título, o aviso de "Novo acionamento", a lista vazia e a legenda dos níveis. Ao testar no emulador, rejeitou o comportamento do `popUpTo` (o app fechava e, ao reabrir, não voltava ao painel) e pediu a versão mais simples: só o botão "Sair" volta ao Login, com `popBackStack()`. Também pediu código no nível MVP, sem enfeites. Nesta Sprint a IA avançou o código, os testes e o relatório em sequência; o Matheus revisou e validou depois, e estuda o código para poder explicá-lo.

### AI Validation

The team confirms that:

- [x] AI-generated content was reviewed before being used.
- [x] The team understands the submitted implementation.
- [x] The implementation was built and executed.
- [x] Acceptance criteria were validated manually.
- [x] No feature outside the Sprint scope was added only because an AI tool suggested it.

---

## 14. Deliverables

- `projects/team-05/app/`
- `projects/team-05/SPRINT-03.md`
- `projects/team-05/docs/specs/SPEC-003.md`
- `projects/team-05/evidence/sprint-03/screen-a.png`
- `projects/team-05/evidence/sprint-03/screen-b.png`

---

## 15. Specification Status

Validated — FR-01 a FR-07 implementados; AC-01 a AC-10 em PASS no emulador Pixel 8 (API 37).
