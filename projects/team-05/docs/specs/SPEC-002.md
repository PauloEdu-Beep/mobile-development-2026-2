# SPEC-002 — Modais Institucionais Interativos (Guia de Cadastro e Prioridades Expansíveis)

> **Team:** Team 05  
> **Sprint:** Sprint 02 — State & User Interaction  
> **Status:** Completed  
> **Related Sprint:** `SPRINT-02.md`  
> **Base:** `SPEC-001.md` (tela de login com os modais "Sobre o SARC" e "Solicitar Cadastro")

---

## 1. Context

**Problem:**

Na Sprint 01, os modais "Sobre o SARC e Prioridades" e "Solicitação de Cadastro" já abrem e fecham por estado (`showAboutDialog` e `showRegisterDialog`), mas o **conteúdo** deles é **estático**:

- o modal **Sobre o SARC** mostra as **4 faixas de prioridade abertas ao mesmo tempo**, com textos longos; o professor precisa rolar e ler tudo para encontrar o nível que procura;
- o modal **Solicitação de Cadastro** é um **bloco único de texto**, sem sequência clara, e termina com um termo interno da equipe de desenvolvimento ("próximas sprints"), que o usuário final não entende;
- os dois modais usam o fundo padrão do Material 3 (cinza-lilás claro `#ECE6F0`), fora da identidade visual da escola;
- os nomes dos níveis nos cartões de prioridade são escritos na própria cor do nível, com contraste abaixo do mínimo recomendado (entre 1,5:1 e 3,8:1; o mínimo WCAG é 4,5:1).

**User / Actor:**

- **Professores novos** (ainda sem acesso), que tocam em "Solicitar Cadastro";
- **Professores, monitores e gestão**, que consultam "Sobre o SARC e Prioridades" para lembrar quando usar cada nível.

**Usage Context:**

Na tela inicial de login, antes de entrar no sistema: no primeiro contato com o aplicativo (cadastro) ou quando há dúvida sobre qual nível de prioridade usar em uma ocorrência (Sobre o SARC).

---

## 2. Objective

O objetivo desta funcionalidade é tornar os dois modais da tela inicial **interativos e controlados por estado**: o modal de cadastro passa a ser um **guia em 3 etapas navegáveis** com indicador de progresso, e o modal de prioridades passa a exibir **cartões recolhidos que expandem ao toque**, um por vez. Assim, o usuário encontra a informação de forma mais rápida e organizada, e a interface **reage visivelmente** a cada ação.

---

## 3. User Scenario

**Cenário A — Guia de cadastro**

**Given** que o professor tocou em "Solicitar Cadastro" e o modal abriu na **Etapa 1 de 3**,  
**When** ele toca em **"Próximo"**,  
**Then** o modal passa a exibir a **Etapa 2 de 3**, a barra de progresso avança e o botão **"Voltar"** aparece.

**Cenário B — Prioridades expansíveis**

**Given** que o professor abriu "Sobre o SARC e Prioridades" e os 4 cartões estão **recolhidos**,  
**When** ele toca no cartão **"Nível 4 - Urgência Crítica"**,  
**Then** a descrição do Nível 4 aparece, a seta do cartão muda de **▼** para **▲** e os demais cartões continuam recolhidos.

---

## 4. Functional Requirements

### Modal "Solicitação de Cadastro" — guia em etapas

### FR-01 — Guia em etapas

O aplicativo deve exibir o conteúdo do modal de cadastro em **3 etapas**, uma por vez, sempre iniciando na **Etapa 1**.

### FR-02 — Navegação entre etapas

O aplicativo deve avançar uma etapa ao toque em **"Próximo"** e retornar uma etapa ao toque em **"Voltar"**. O botão "Voltar" não deve aparecer na Etapa 1.

### FR-03 — Indicador de progresso

O aplicativo deve exibir o texto **"Etapa X de 3"** e uma **barra de progresso** proporcional à etapa atual.

### FR-04 — Conclusão e reinício do guia

Na Etapa 3, o aplicativo deve substituir "Próximo" por **"Entendi"**, que fecha o modal. Ao reabrir o modal, o guia deve **recomeçar na Etapa 1**.

### Modal "Sobre o SARC e Prioridades" — cartões expansíveis

### FR-05 — Cartões de prioridade expansíveis

O aplicativo deve exibir os 4 cartões de prioridade **recolhidos** (somente a cor e o nome do nível) e **mostrar a descrição** de um nível ao toque em seu cartão.

### FR-06 — Um cartão aberto por vez

O aplicativo deve manter **no máximo um** cartão aberto: abrir outro cartão recolhe o anterior, e tocar no cartão aberto o recolhe.

### FR-07 — Indicação visual do cartão aberto

O aplicativo deve exibir uma **seta ▼/▲** conforme o cartão esteja recolhido ou aberto e **destacar a borda** do cartão aberto.

### Ajustes visuais e de linguagem nos dois modais

### FR-08 — Fundo dos modais na cor de superfície do SARC

O aplicativo deve exibir os dois modais com o **fundo branco** definido no tema do SARC (papel `surface` → `SarcSurfaceLight`, `#FFFFFF`), no lugar do cinza-lilás padrão do Material 3 (`#ECE6F0`).

### FR-09 — Nomes dos níveis legíveis

O aplicativo deve exibir os nomes dos 4 níveis de prioridade em **azul-marinho escuro** (`SarcNavyDark`), com contraste **≥ 4,5:1**, mantendo a cor de cada nível na **bolinha**, na **borda** e no **fundo** do cartão.

### FR-10 — Textos na linguagem do usuário

O aplicativo deve usar, nos modais, apenas **termos do usuário**, sem termos internos da equipe de desenvolvimento (como "sprint"), orientando o professor sobre **o que fazer** após solicitar o cadastro.

---

## 5. Constraints

### Required Technologies

- Kotlin 2.2.10
- Jetpack Compose com Material Design 3 (Compose BOM 2026.02.01)
- Android Studio

### Project Constraints

- `minSdk = 24`, `compileSdk = 37` e `targetSdk = 37` permanecem iguais.
- **Nenhuma dependência nova** deve ser adicionada: `AnimatedVisibility`, `LinearProgressIndicator` e os ícones de seta já estão disponíveis nas bibliotecas atuais.
- Os novos estados devem usar `remember { mutableStateOf(...) }`, conforme o tema da Sprint 02.
- Os novos estados devem ser criados **dentro de cada modal**, para que reiniciem automaticamente quando o modal é fechado.
- O guia de cadastro permanece **dentro do `AlertDialog` existente**: as etapas são conteúdo de um único modal, controlado pelo estado `registerStep`. **Nenhuma tela, rota ou arquivo novo** é criado.
- A implementação deve ficar restrita a `projects/team-05/app/`: a **lógica** muda somente no `WelcomeScreen.kt`. Os demais arquivos do app podem receber **apenas comentários didáticos**, sem alterar nenhuma linha de código.

### Non-Functional Requirements

- **NFR-01 — Área de toque:** cartões e botões com altura mínima de **48 dp**.
- **NFR-02 — Legibilidade:** textos dos modais com contraste **≥ 4,5:1** (WCAG) no modo claro.
- **NFR-03 — Resposta imediata:** cada mudança de estado deve atualizar a tela sem travamento perceptível.

### Files or Modules That Must Not Be Modified

- `AndroidManifest.xml`, `MainActivity.kt`, `Color.kt`, `Type.kt`, `Theme.kt` e os arquivos do Gradle: **nenhuma linha de código** pode ser alterada; apenas comentários didáticos são permitidos.
- Pastas de outros times (`projects/team-XX/`) e arquivos da raiz do repositório.

---

## 6. Out of Scope

The following items are outside the scope of this specification:

- **Formulário de cadastro** (nome, e-mail, celular, senha) e **validação** de campos — Sprint 04.
- **Navegação entre telas** (login funcional, tela de cadastro, painel). As etapas do guia são conteúdo de um único modal, controlado pelo estado `registerStep`; não há troca de tela, rotas, `NavHost` nem `NavController` — escopo da Sprint 03.
- **Envio e aprovação da solicitação** pelo aplicativo (tela de cadastro, aplicativo da Coordenação Militar, banco de dados, notificações) — Sprints futuras.
- Suporte completo ao **modo escuro** nas cores aplicadas diretamente na tela.
- Ajuste do **teclado virtual** (`imePadding`), ícone do aplicativo e testes automatizados.

---

## 7. Acceptance Criteria

### AC-01 — Estado inicial do guia

**Related requirement:** FR-01, FR-03

**Condition:**  
Ao abrir "Solicitar Cadastro", o modal mostra **"Etapa 1 de 3"**, a barra de progresso em aproximadamente 1/3, o título "Quem pode solicitar", o botão "Próximo" e **nenhum** botão "Voltar".

### AC-02 — Avançar etapa

**Related requirement:** FR-02, FR-03

**Condition:**  
Na Etapa 1, o toque em "Próximo" exibe a **Etapa 2**, com o texto "Etapa 2 de 3", a barra em aproximadamente 2/3 e o botão "Voltar" visível.

### AC-03 — Voltar etapa

**Related requirement:** FR-02

**Condition:**  
Na Etapa 2, o toque em "Voltar" retorna à **Etapa 1**, com o conteúdo correspondente.

### AC-04 — Concluir o guia

**Related requirement:** FR-04

**Condition:**  
Na Etapa 3, o botão principal mostra **"Entendi"**, e o toque nele fecha o modal, retornando à tela de login.

### AC-05 — Reinício do guia

**Related requirement:** FR-04

**Condition:**  
Após fechar o modal (por "Entendi", toque fora do modal ou botão Voltar do celular) e abri-lo novamente, o guia mostra a **Etapa 1**.

### AC-06 — Cartões recolhidos

**Related requirement:** FR-05

**Condition:**  
Ao abrir "Sobre o SARC e Prioridades", os 4 cartões mostram somente **cor, nome do nível e seta ▼**, sem descrição.

### AC-07 — Expandir cartão

**Related requirement:** FR-05, FR-07

**Condition:**  
O toque no cartão do Nível 4 exibe a **descrição** do Nível 4, a seta muda para **▲** e a borda do cartão fica destacada.

### AC-08 — Um cartão por vez

**Related requirement:** FR-06

**Condition:**  
Com o Nível 4 aberto, o toque no Nível 1 abre o Nível 1 e **recolhe** o Nível 4; um novo toque no Nível 1 deixa **todos os cartões recolhidos**.

### AC-09 — Fundo dos modais

**Related requirement:** FR-08

**Condition:**  
Os dois modais abrem com **fundo branco** (`#FFFFFF`), e não com o cinza-lilás padrão (`#ECE6F0`) registrado nas evidências da Sprint 01.

### AC-10 — Legibilidade dos níveis

**Related requirement:** FR-09

**Condition:**  
Os nomes dos 4 níveis aparecem em azul-marinho escuro, com contraste **≥ 4,5:1** sobre o fundo do cartão, e cada cartão mantém a cor do seu nível na bolinha, na borda e no fundo.

### AC-11 — Linguagem do usuário

**Related requirement:** FR-10

**Condition:**  
Nenhum texto dos modais contém a palavra "sprint", e a Etapa 3 orienta o professor sobre o que fazer após solicitar o cadastro.

### AC-12 — Etapas não são navegação

**Related requirement:** FR-01, FR-04

**Condition:**  
Com o guia em qualquer etapa, o botão Voltar do celular **fecha o modal** e o aplicativo permanece na tela de login; nenhuma nova tela é aberta.

### AC-13 — Não-regressão

**Related requirement:** FR-01 a FR-10

**Condition:**  
Digitar o e-mail, mascarar e mostrar a senha, tocar em "Entrar" e abrir e fechar os dois modais continuam funcionando como na Sprint 01.

### AC-14 — Estabilidade

**Related requirement:** FR-01 a FR-10

**Condition:**  
O aplicativo compila com `./gradlew assembleDebug`, executa no emulador e suporta **10 repetições** seguidas de cada interação sem travar ou fechar.

---

## 8. Requirement Traceability

| Requirement | Implemented In | Acceptance Criterion | Evidence |
| --- | --- | --- | --- |
| FR-01 | `WelcomeScreen.kt` L475–487 — estado `registerStep` e lista `etapas` (título e texto de cada etapa) | AC-01, AC-12 | `register-guide-step-1.png` |
| FR-02 | `WelcomeScreen.kt` L542–571 — `confirmButton` ("Próximo", `registerStep++`) e `dismissButton` ("Voltar", `registerStep--`, só a partir da etapa 2) | AC-02, AC-03 | `register-guide-step-2.png` |
| FR-03 | `WelcomeScreen.kt` L512–524 — texto "Etapa X de 3" e `LinearProgressIndicator` | AC-01, AC-02 | `register-guide-step-1.png`, `register-guide-step-2.png` |
| FR-04 | `WelcomeScreen.kt` L545–553 — "Entendi" fecha o modal; L475 — estado criado dentro do `if (showRegisterDialog)`, o que faz o guia reiniciar na etapa 1 | AC-04, AC-05 | `register-guide-step-3.png`, `register-guide-closed.png`, `register-guide-reopened.png` |
| FR-05 | `WelcomeScreen.kt` L348 — estado `expandedLevel`; L413–452 — os 4 `PriorityBadge`; L588–589 — `Card(onClick)`; L635–642 — `AnimatedVisibility` | AC-06, AC-07 | `before-interaction.png`, `after-interaction.png` |
| FR-06 | `WelcomeScreen.kt` L352–354 — regra `alternarNivel` (abre um e recolhe o anterior) | AC-08 | `about-priority-switch.png`, `about-priority-collapsed-again.png` |
| FR-07 | `WelcomeScreen.kt` L593–597 — borda de 2 dp quando aberto; L628–632 — seta ▼/▲ | AC-07 | `after-interaction.png` |
| FR-08 | `WelcomeScreen.kt` L358 e L491 — `containerColor = surface` nos dois `AlertDialog` | AC-09 | `before-interaction.png`, `register-guide-step-1.png` |
| FR-09 | `WelcomeScreen.kt` L620–626 — nome do nível em `SarcNavyDark` | AC-10 | `before-interaction.png` |
| FR-10 | `WelcomeScreen.kt` L478–485 — textos das 3 etapas; L405 — instrução "Toque em um nível…" | AC-11 | `register-guide-step-3.png` |

---

## 9. Implementation Plan

### Components to Create or Modify

Código alterado somente em `projects/team-05/app/app/src/main/java/com/team05/sarc/ui/screens/WelcomeScreen.kt` (os demais arquivos do app recebem apenas comentários didáticos):

1. **Modal "Solicitação de Cadastro"**
   - lista com as 3 etapas (título + texto de cada uma);
   - texto "Etapa X de 3" e `LinearProgressIndicator` com progresso `registerStep / 3`;
   - `dismissButton` "Voltar" (exibido a partir da Etapa 2);
   - `confirmButton` "Próximo" (Etapas 1 e 2) ou "Entendi" (Etapa 3);
   - `containerColor = MaterialTheme.colorScheme.surface`.
2. **Modal "Sobre o SARC e Prioridades"**
   - linha de instrução: *"Toque em um nível para ver quando utilizá-lo."*;
   - cada `PriorityBadge` recebe `expanded` e `onClick`;
   - `containerColor = MaterialTheme.colorScheme.surface`.
3. **`PriorityBadge`**
   - cartão clicável (`Card(onClick = ...)`, que já traz o efeito de toque do Material 3), com altura mínima de 48 dp;
   - seta ▼/▲ (`Icons.Default.KeyboardArrowDown` / `KeyboardArrowUp`);
   - descrição exibida com `AnimatedVisibility` somente quando `expanded` for verdadeiro;
   - borda de 2 dp com a cor cheia do nível quando aberto;
   - título na cor `SarcNavyDark`; a cor do nível permanece na bolinha, na borda e no fundo.

### Expected Interaction Flow

```text
CADASTRO
[registerStep = 1] → toque em "Próximo" → [registerStep = 2]
      → recomposição: Etapa 2, "Etapa 2 de 3", barra 2/3, botão "Voltar" visível
[registerStep = 3] → toque em "Entendi" → [showRegisterDialog = false] → modal sai da tela
reabrir o modal → registerStep é criado novamente com o valor 1

SOBRE O SARC
[expandedLevel = null] → toque no Nível 4 → [expandedLevel = 4]
      → recomposição: descrição do Nível 4 visível, seta ▲, borda destacada
[expandedLevel = 4]    → toque no Nível 1 → [expandedLevel = 1] → Nível 4 recolhe, Nível 1 abre
[expandedLevel = 1]    → toque no Nível 1 → [expandedLevel = null] → todos recolhidos
```

### Data or State Required

| Estado | Tipo | Valor inicial | Onde é criado | Muda quando |
| --- | --- | --- | --- | --- |
| `registerStep` | `Int` (1 a 3) | `1` | dentro de `if (showRegisterDialog)` | toque em "Próximo" (+1) ou "Voltar" (−1) |
| `expandedLevel` | `Int?` (1 a 4 ou `null`) | `null` (nenhum aberto) | dentro de `if (showAboutDialog)` | toque em um cartão: abre o nível tocado ou volta a `null` se ele já estava aberto |

Os cinco estados da Sprint 01 (`email`, `password`, `passwordVisible`, `showAboutDialog`, `showRegisterDialog`) permanecem sem alteração.

### Conteúdo das etapas do guia de cadastro

| Etapa | Título | Texto |
| --- | --- | --- |
| 1 | Quem pode solicitar | "O acesso ao SARC é restrito a servidores e docentes credenciados da Escola Estadual Cívico-Militar Maria de Lima Cadidé: professores, monitores da Coordenação Militar e equipe gestora." |
| 2 | Como funciona a aprovação | "Você preenche seus dados na solicitação de cadastro e o pedido é enviado à Coordenação Militar, que o analisa pelo próprio SARC. Somente após essa homologação o seu acesso é liberado." |
| 3 | O que fazer depois | "Após enviar sua solicitação, aguarde a aprovação da Coordenação Militar. Se preferir, procure a Coordenação para informar que já fez o pedido e está aguardando a liberação. O formulário de solicitação estará disponível em breve no aplicativo." |

---

## 10. Validation Plan

The team must:

1. Compilar o projeto com `./gradlew assembleDebug`.
2. Executar o aplicativo no emulador Android Studio Pixel 8 (API 37).
3. Registrar a tela inicial (`initial-screen.png`).
4. **Modal Sobre:** abrir o modal com os cartões recolhidos (`before-interaction.png`); tocar no Nível 4 (`after-interaction.png`); tocar no Nível 1 (`about-priority-switch.png`); tocar novamente no Nível 1 (`about-priority-collapsed-again.png`); tocar em "Entendido" e registrar o retorno à tela inicial (`about-dialog-closed.png`).
5. **Modal Cadastro:** abrir o modal (`register-guide-step-1.png`); tocar em "Próximo" (`register-guide-step-2.png`); tocar em "Voltar" e conferir a Etapa 1; avançar até a Etapa 3 (`register-guide-step-3.png`); tocar em "Entendi" e registrar o retorno à tela inicial (`register-guide-closed.png`); reabrir o modal e conferir a Etapa 1 (`register-guide-reopened.png`).
6. Fechar cada modal pelas três formas (botão, toque fora e botão Voltar do celular), com o guia em etapas diferentes.
7. Repetir cada interação 10 vezes seguidas.
8. Executar o teste de não-regressão da Sprint 01 (e-mail, senha, ícone de visibilidade, "Entrar").
9. Registrar os resultados na seção 11.

---

## 11. Validation Results

| Acceptance Criterion | Result | Notes |
| --- | --- | --- |
| AC-01 | PASS | Guia abre em "Etapa 1 de 3", barra em 1/3, apenas o botão "Próximo" (sem "Voltar") |
| AC-02 | PASS | "Próximo" leva à "Etapa 2 de 3", barra em 2/3 e botão "Voltar" visível |
| AC-03 | PASS | "Voltar" na etapa 2 retorna à etapa 1 |
| AC-04 | PASS | Na etapa 3 o botão vira "Entendi"; ao tocar, o modal fecha e a tela de login volta |
| AC-05 | PASS | Ao reabrir, o guia recomeça na etapa 1 (testado após "Entendi" e após tocar fora do modal na etapa 3) |
| AC-06 | PASS | Modal Sobre abre com os 4 cartões recolhidos e seta ▼ |
| AC-07 | PASS | Toque no Nível 4: descrição aparece, seta vira ▲ e a borda fica destacada |
| AC-08 | PASS | Toque no Nível 1 recolhe o Nível 4; novo toque no Nível 1 recolhe todos |
| AC-09 | PASS | Cor medida no print: fundo dos dois modais `#FFFFFF` (antes `#ECE6F0`) |
| AC-10 | PASS | Nomes dos níveis em `SarcNavyDark`, contraste calculado entre 11:1 e 15:1 nos 4 fundos (mínimo 4,5:1) |
| AC-11 | PASS | Nenhum texto da tela usa "sprint" ou jargão interno (a palavra só aparece em comentários do código) |
| AC-12 | PASS | O botão Voltar do celular fecha o modal na etapa 2 e o app permanece na mesma tela |
| AC-13 | PASS | Digitação de e-mail e senha, máscara da senha, botão do olho (mostrar/ocultar) e botão "Entrar" funcionam como na Sprint 01 |
| AC-14 | PASS | Build sem erros; 10 repetições seguidas de abrir/fechar os dois modais sem travamentos e sem crash no logcat |

### Environment Used for Validation

- **Device:** Android Studio Emulator — Pixel 8
- **Android version / API:** API 37
- **Build result:** PASS (`./gradlew assembleDebug` — BUILD SUCCESSFUL)
- **Application execution:** PASS (APK instalado e testado no emulador Pixel 8, API 37)

---

## 12. Evidence

Required evidence (em `projects/team-05/evidence/sprint-02/`):

| Arquivo | O que mostra | Prova |
| --- | --- | --- |
| `initial-screen.png` | tela inicial de login | ponto de partida e não-regressão |
| **`before-interaction.png`** (obrigatório) | modal Sobre aberto, 4 cartões recolhidos, fundo branco | estado inicial (`expandedLevel = null`) |
| **`after-interaction.png`** (obrigatório) | cartão do Nível 4 aberto, seta ▲, borda destacada | estado após o toque (`expandedLevel = 4`) |
| `about-priority-switch.png` | Nível 1 aberto e Nível 4 recolhido | um cartão por vez |
| `about-priority-collapsed-again.png` | todos os cartões recolhidos após novo toque no Nível 1 | recolher o cartão aberto |
| `about-dialog-closed.png` | tela inicial após "Entendido" | saída do modal Sobre |
| `register-guide-step-1.png` | guia na Etapa 1, sem "Voltar" | estado inicial (`registerStep = 1`) |
| `register-guide-step-2.png` | guia na Etapa 2, barra 2/3, "Voltar" visível | avanço de etapa |
| `register-guide-step-3.png` | guia na Etapa 3, botão "Entendi" | conclusão e linguagem do usuário |
| `register-guide-closed.png` | tela inicial após "Entendi" | saída do modal Cadastro |
| `register-guide-reopened.png` | guia reaberto na Etapa 1 | reinício do guia |

---

## 13. AI-Assisted Development

AI tools may be used, but the team remains responsible for the final solution.

### AI Tool(s)

- **Tool:** Claude (Claude Code) e Google Antigravity (Gemini)
- **Model/version, if known:** Claude Opus 5.5 e Gemini 3.8 Flash (modo de raciocínio alto)

### How AI Was Used

- [x] Understanding the requirement
- [x] Refining the specification
- [x] Generating implementation suggestions
- [ ] Explaining code
- [ ] Debugging
- [ ] Refactoring
- [ ] Generating test ideas
- [x] Reviewing acceptance criteria
- [x] Other: captura das evidências (prints) no emulador via ADB

### Prompt or Request Summary

A equipe pediu apoio para analisar o projeto e a Sprint 01, estudar o código linha por linha e planejar a Sprint 02 a partir da orientação do professor: aprimorar as interações dos modais já existentes, sem formulário nem validação.

### AI-Generated or Suggested Content

A IA sugeriu transformar o modal de cadastro em um guia em 3 etapas e os cartões de prioridade em cartões expansíveis, propôs a estrutura desta especificação (FRs, ACs, estados e plano de validação) e identificou, por medição, o fundo padrão dos modais (`#ECE6F0`) e o contraste insuficiente dos nomes dos níveis. Na implementação, a IA gerou o código dos estados `expandedLevel` e `registerStep` no `WelcomeScreen.kt`, com comentários explicativos, e realizou as capturas de tela das evidências no emulador.

### Human Review and Changes

A equipe escolheu os dois modais como escopo da Sprint 02, definiu que a aprovação de cadastro é feita pela Coordenação Militar pelo próprio aplicativo, ajustou os textos das etapas 2 e 3 conforme o fluxo real planejado para o SARC, pediu a separação dos ajustes visuais em FR-08, FR-09 e FR-10, garantiu que as etapas não se confundam com navegação (escopo da Sprint 03) e ampliou a lista de evidências. Os testes de validação dos critérios de aceitação foram realizados manualmente pela equipe no emulador.

### AI Validation

The team confirms that:

- [x] AI-generated content was reviewed before being used.
- [x] The team understands the submitted implementation.
- [x] The implementation was built and executed.
- [x] Acceptance criteria were validated manually.
- [x] No feature outside the Sprint scope was added only because an AI tool suggested it.

---

## 14. Suggested Prompt for AI Assistance

```text
I am developing an Android application using Kotlin and Jetpack Compose.

Current Sprint:
SPRINT-02 — State & User Interaction

Feature specification:
SPEC-002 — Interactive institutional dialogs (3-step register guide with
`registerStep: Int` and expandable priority cards with `expandedLevel: Int?`).

Current implementation:
WelcomeScreen.kt — two AlertDialogs controlled by showAboutDialog and
showRegisterDialog; PriorityBadge draws each priority card.

Task:
[DESCRIBE ONE SPECIFIC TASK]

Constraints:
Logic changes only in WelcomeScreen.kt (other files: comments only); no new dependencies; states with remember { mutableStateOf() }
created inside each dialog; no new screens, routes or navigation.

Out of scope:
Register form, field validation, navigation (Sprint 03/04), persistence.

Please:
1. explain the proposed solution before providing code;
2. implement only what is necessary for this specification;
3. identify which functional requirement each change addresses;
4. explain unfamiliar Compose/Kotlin concepts;
5. describe how I can validate each acceptance criterion;
6. do not add features outside the current Sprint.
```

---

## 15. Deliverables

The following artifacts must be included in the team's Sprint submission:

- [x] `projects/team-05/app/` — `WelcomeScreen.kt` atualizado
- [x] `projects/team-05/docs/specs/SPEC-002.md` — esta especificação
- [x] `projects/team-05/SPRINT-02.md` — relatório da Sprint 02 atualizado
- [x] `projects/team-05/evidence/sprint-02/before-interaction.png`
- [x] `projects/team-05/evidence/sprint-02/after-interaction.png`
- [x] Evidências adicionais listadas na seção 12

---

## 16. Specification Status

- [x] Context and objective are clear.
- [x] Functional requirements are complete.
- [x] Constraints are documented.
- [x] Out-of-scope items are documented.
- [x] Acceptance criteria are measurable.
- [x] Requirement traceability is complete.
- [x] Implementation satisfies the specification.
- [x] Validation results are documented.
- [x] Evidence is included.
- [x] AI usage is documented when applicable.
- [x] Every team member can explain the implemented feature.

---

## Final Check

> **Which requirement does this code implement, where is it implemented, and what evidence proves that the requirement was satisfied?**
