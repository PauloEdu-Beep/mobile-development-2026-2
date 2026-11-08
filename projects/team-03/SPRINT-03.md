# SPRINT 03 — Navigation & Multiple Screens

> **Disciplina:** Desenvolvimento de Software Móvel (2026.2)  
> **Equipe:** Team 03  
> **Integrantes:** Célia Hiromi (@hiromilly) e Geovanna Gaspar (@gegwspar)  
> **Ciclo de Desenvolvimento:** `SPEC → BUILD → VALIDATE → EXPLAIN → COMMIT`  
> **Prazo Oficial:** 05/10/2026  

---

## 1. Telas Definidas

Nesta Sprint 03, o **Wiflux** evoluiu de um aplicativo de tela única para uma arquitetura com múltiplas telas navegáveis no pacote `com.team03.wifi`:

- **HomeScreen (`ROUTE_HOME = "home"`):** Tela principal de diagnóstico e verificação de sinal Wi-Fi (`HomeScreen.kt`). Contém o cabeçalho **Wiflux**, o dial vetorial em `Canvas` (`RadarView`) com 60 marcações e arco colorido proporcional ao nível em dBm, o chip de status qualitativo, o botão de ação primária **"▶ Iniciar Verificação"** e o botão condicional de navegação **"Ver Detalhes"**.
- **DetailsScreen (`ROUTE_DETAILS = "details/{status}"`):** Tela secundária (`DetailsScreen.kt`) focada no detalhamento técnico e orientador do resultado. Exibe o título **"Detalhes da Verificação"**, um card com o **Status** da conexão, a **Intensidade** numérica em dBm (`DetailRow`) e uma **Recomendação** textual prática de usabilidade (`when (status)`), além do botão **"← Voltar"** para retornar ao painel principal.

---

## 2. Por que essas Duas Telas Existem

A divisão entre **HomeScreen** e **DetailsScreen** atende diretamente à proposta de valor do **Wiflux**:
- A **HomeScreen** prioriza a agilidade: o usuário realiza a verificação no cômodo e obtém uma resposta visual imediata (dial colorido, valor em dBm e classificação qualitativa).
- A **DetailsScreen** fornece contexto adicional e orientações práticas de rede (como informar se o local é ótimo para trabalhar e fazer chamadas de vídeo ou se requer aproximar-se do roteador/usar repetidor) sem poluir visualmente a tela principal. O usuário acessa essas informações aprofundadas quando desejar, por meio do botão **"Ver Detalhes"**.

---

## 3. Rota que Conecta as Telas

A transição entre as telas ocorre por meio das constantes de rota definidas em `MainActivity.kt`:
```kotlin
private const val ROUTE_HOME = "home"
private const val ROUTE_DETAILS = "details/{status}"
```
Ao tocar em **"Ver Detalhes"** na `HomeScreen`, o callback `onSeeDetails` é acionado e a `MainActivity` invoca:
```kotlin
navController.navigate("details/${status.name}")
```
O parâmetro `${status.name}` transporta o nome da constante do enum (`STRONG`, `MEDIUM` ou `WEAK`) sorteada na tela inicial. No destino, o argumento tipado (`navArgument("status") { type = NavType.StringType }`) é recuperado do `backStackEntry` e convertido via `SignalStatus.valueOf(statusName)`, garantindo que a `DetailsScreen` renderize exatamente o status, o valor em dBm, a cor e a recomendação da verificação realizada.

---

## 4. Onde a Navegação é Disparada

1. **Navegação para Frente (`HomeScreen` → `DetailsScreen`):**
   - Disparada no evento `onClick = { onSeeDetails(signalStatus) }` do botão **"Ver Detalhes"** em `HomeScreen.kt`.
   - O botão é exibido somente após uma medição ativa (`if (signalStatus != SignalStatus.NONE)`).
   - O `NavHost` em `MainActivity.kt` executa `navController.navigate("details/${status.name}")`.

2. **Navegação de Retorno (`DetailsScreen` → `HomeScreen`):**
   - Disparada no evento `onClick = onBack` do botão **"← Voltar"** em `DetailsScreen.kt`.
   - O `NavHost` em `MainActivity.kt` executa `navController.popBackStack()`, desempilhando a tela de detalhes e restaurando a tela inicial.
   - O botão físico ou gesto nativo de voltar do sistema Android também opera de maneira idêntica graças ao gerenciamento automático da pilha (*back stack*) pelo `NavHost`.

---

## 5. Validação do Retorno e Preservação de Estado

O estado da medição de sinal em `HomeScreen.kt` é preservado através de `rememberSaveable`:
```kotlin
var signalStatus by rememberSaveable { mutableStateOf(SignalStatus.NONE) }
```
Dessa forma, ao navegar para a `DetailsScreen` e posteriormente retornar à `HomeScreen` (seja pelo botão **"← Voltar"** ou pelo gesto do sistema), o resultado da medição (`-45 dBm`), o arco do dial e o chip de status (`"Sinal Forte"`) permanecem intactos, cumprindo os critérios de navegação e regressão.

---

## 6. Regressão e Continuidade das Sprints 01 e 02

- **Sprint 01:** A identidade visual, o nome do produto (**Wiflux**), o subtítulo `SIGNAL DIAGNOSTICS`, o slogan descritivo (*"Mapeie a rede. Conecte no melhor ponto."*) e o botão primário foram preservados.
- **Sprint 02:** O gerenciamento reativo de estado, o sorteio entre os níveis de sinal (`STRONG`, `MEDIUM`, `WEAK`), o dial vetorial desenhado em `Canvas` (`RadarView`) e o chip informativo continuam plenamente operacionais na `HomeScreen`.
- **Modularização:** O código foi separado em arquivos independentes no pacote `com.team03.wifi` (`SignalStatus.kt`, `HomeScreen.kt`, `DetailsScreen.kt` e `MainActivity.kt`), atendendo diretamente às diretrizes de separação de UI em Composables solicitadas pelo professor.

---

## 7. Rastreabilidade com a SPEC-003

A especificação técnica completa encontra-se documentada em:
- [`docs/specs/SPEC-003.md`](docs/specs/SPEC-003.md)

| Requisito Funcional | Critério de Aceitação | Implementação (`com.team03.wifi`) | Validação |
| :--- | :--- | :--- | :--- |
| **FR-01** (Botão "Ver Detalhes" condicional) | **AC-02, AC-07** | `if (signalStatus != SignalStatus.NONE)` em `HomeScreen.kt` | Validado em `evidence/sprint-03/screen-a.png` |
| **FR-02** (Navegação com parâmetro de rota) | **AC-03, AC-04** | `navController.navigate("details/${status.name}")` em `MainActivity.kt` | Validado na transição `HomeScreen` → `DetailsScreen` |
| **FR-03** (Exibição de diagnóstico completo) | **AC-05** | Card com `DetailRow` e `recommendation` em `DetailsScreen.kt` | Validado em `evidence/sprint-03/screen-b.png` |
| **FR-04** (Botão de retorno à tela inicial) | **AC-06, AC-07** | `navController.popBackStack()` em `MainActivity.kt` / `DetailsScreen.kt` | Validado no retorno com estado mantido |

---

## 8. Evidências de Validação

As evidências em alta resolução foram organizadas na pasta oficial da Sprint (`evidence/sprint-03/`) conforme a estrutura exigida pelo professor:

### 1. Tela A — `HomeScreen` com Verificação Realizada (`screen-a.png`)
Exibição do dial iluminado (`-45 dBm`), chip de status (`"Sinal Forte"`) e o botão **"Ver Detalhes"** habilitado:

![HomeScreen](evidence/sprint-03/screen-a.png)

### 2. Tela B — `DetailsScreen` com Diagnóstico Completo (`screen-b.png`)
Exibição do card detalhado contendo Status (`Sinal Forte`), Intensidade (`-45 dBm`), Recomendação textual (*"Ótimo local para trabalhar ou fazer chamadas de vídeo."*) e o botão **"← Voltar"**:

![DetailsScreen](evidence/sprint-03/screen-b.png)

---

## 9. Registro de Uso de Inteligência Artificial (LLM)

Em conformidade com a política de integridade da disciplina:

| Item | Team response |
| :--- | :--- |
| **LLM/tool used** | Claude & Gemini (Antigravity) |
| **Task supported by the LLM** | Auxílio na separação modular dos componentes Compose (`HomeScreen.kt`, `DetailsScreen.kt`, `SignalStatus.kt`), configuração da rota parametrizada `details/{status}` com `navArgument` no `NavHost` e revisão da `SPEC-003.md`. |
| **Main suggestion received** | Utilizar passagem de argumento na rota (`details/${status.name}`) com `rememberNavController()` e `rememberSaveable` para manter o estado da verificação ao retornar via `popBackStack()`. |
| **What the team changed manually** | Implementação das telas `HomeScreen` e `DetailsScreen` no pacote `com.team03.wifi`, definição das recomendações práticas em português para cada faixa de sinal e validação manual no emulador Android. |
| **How the result was validated** | Compilação via Gradle (`./gradlew assembleDebug` com `BUILD SUCCESSFUL`), execução no emulador Android validando o fluxo `Launch → HomeScreen → Iniciar Verificação → Ver Detalhes → DetailsScreen → Voltar → HomeScreen` e captura das evidências `screen-a.png` e `screen-b.png`. |

---

## 10. Critérios de Aceitação da Sprint 03

- [x] **AC-01** — `SPEC-003.md` existe e está devidamente preenchida na pasta `docs/specs/`.
- [x] **AC-02** — Existem pelo menos duas telas com responsabilidades claras (`HomeScreen` e `DetailsScreen`).
- [x] **AC-03** — O Navigation Compose (`NavHost` e `NavController`) foi implementado com sucesso.
- [x] **AC-04** — Ação do usuário no botão "Ver Detalhes" navega para a tela de destino.
- [x] **AC-05** — O conteúdo da tela de detalhes reflete o status obtido na medição inicial (`-45 dBm` / `Sinal Forte`).
- [x] **AC-06** — O botão "← Voltar" e o gesto do sistema retornam à tela inicial corretamente.
- [x] **AC-07** — Funcionalidades das Sprints 01 e 02 (dial, chip, verificação reativa) permanecem operacionais e o estado é preservado.
- [x] **AC-08** — O aplicativo compila e executa sem falhas ou crashes (`BUILD SUCCESSFUL`).
- [x] **AC-09** — Evidências visuais (`screen-a.png` e `screen-b.png`) salvas na pasta `evidence/sprint-03/`.
- [x] **AC-10** — A equipe compreende e está apta a explicar todo o grafo e ciclo de navegação.

---

## 11. Informações de Submissão Git

- **Branch da Sprint:** `team-03/sprint-03`
- **Padrão Semântico de Commits:**
  - `docs: define SPEC-003 and navigation requirements`
  - `feat: implement multi-screen navigation with Navigation Compose`
  - `docs: add sprint 03 report and navigation evidence`
- **Título do Pull Request:** `[Sprint 03] Team 03 — Navigation & Multiple Screens`
- **Repositório Base:** `brenofeliix/mobile-development-2026-2` (branch `main`)
- **Repositório de Origem (Fork):** `hiromilly/mobile-development-2026-2` (branch `team-03/sprint-03`)

---

## 12. Checklist de Definição de Pronto (Definition of Done)

- [x] `SPEC-003.md` completa e consistente.
- [x] Pelo menos duas telas navegáveis implementadas (`HomeScreen` e `DetailsScreen`).
- [x] Navegação de ida e volta operando com integridade de estado.
- [x] Funcionalidades anteriores (Sprints 01 e 02) preservadas sem regressão.
- [x] Aplicação compilada com sucesso no Gradle (`BUILD SUCCESSFUL`).
- [x] Evidências salvas em `projects/team-03/evidence/sprint-03/screen-a.png` e `screen-b.png`.
- [x] Relatório da Sprint completo com documentação do uso de IA.
- [x] Equipe pronta para apresentar e explicar o código ao professor.
