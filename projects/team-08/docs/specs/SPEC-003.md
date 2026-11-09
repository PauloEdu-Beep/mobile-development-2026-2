# SPEC-003 — Navigation & Multiple Screens

> **Team:** Team 08  
> **Sprint:** Sprint 03  
> **Status:** In Progress  
> **Related Sprint:** `SPRINT-03.md`

---

## 1. Context

O aplicativo **Mercadinho** foi iniciado como um protótipo de tela única (Sprint 01 e 02). Para atender ao propósito do produto — permitir que o usuário anote e consulte sua lista de compras para não esquecer nada —, é indispensável navegar da tela inicial de apresentação para a tela da lista de compras e poder retornar.

**Problem:**  
O aplicativo mantinha todo o conteúdo centralizado em uma única tela inicial sem possibilidade de transição para a visualização da lista de compras.

**User / Actor:**  
Usuário do Mercadinho que deseja acessar sua lista de compras a partir da tela inicial e poder voltar quando desejar.

**Usage Context:**  
Ao abrir o app, o usuário visualiza a tela inicial e clica no botão principal para navegar até a tela com sua lista de compras. Na lista, ele pode marcar itens e voltar para a tela inicial utilizando o botão de retorno ou a navegação padrão do sistema.

---

## 2. Objective

O objetivo desta funcionalidade é implementar a navegação declarativa com Jetpack Compose Navigation entre pelo menos duas telas com propósitos distintos: `WelcomeScreen` (apresentação) e `ShoppingListScreen` (lista de compras com interação de estado preservada), suportando navegação de ida e de volta (back navigation).

---

## 3. User Scenario

**Given** que o usuário está na tela inicial (`WelcomeScreen`),  
**When** ele clica no botão "Ver Lista de Compras",  
**Then** o aplicativo navega para a tela `ShoppingListScreen` exibindo a lista de itens e a opção de retorno. Ao acionar o botão de voltar, o usuário retorna à `WelcomeScreen`.

---

## 4. Functional Requirements

### FR-01 — Navigation Controller and Host Setup
A aplicação deve configurar o `NavController` e `NavHost` do Jetpack Compose para gerenciar as rotas e o histórico de navegação.

### FR-02 — Welcome Screen Route
A rota inicial `"welcome"` deve exibir a tela `WelcomeScreen`, contendo o título do app, descrição e botão de navegação para a lista de compras.

### FR-03 — Shopping List Screen Route
A rota `"shopping_list"` deve exibir a tela `ShoppingListScreen`, contendo ícone de carrinho, título da tela, campo e botão para adicionar novos itens, lista com os itens anotados, opção de marcar como comprado e botão para excluir/apagar item.

### FR-04 — Forward Navigation
O acionamento do botão principal na `WelcomeScreen` deve navegar para a rota `"shopping_list"`.

### FR-05 — Back Navigation
A tela `ShoppingListScreen` deve permitir retornar à tela anterior tanto pelo botão de retorno na interface (TopBar/botão Voltar) quanto pelo botão/gesto nativo de voltar do Android, restaurando a pilha de navegação.

### FR-06 — State Interaction & Item Management
A tela `ShoppingListScreen` deve permitir que o usuário interaja com a lista dinamicamente: adicionar um novo item pelo campo de texto e botão "+", apagar um item pelo ícone de lixeira e marcar/desmarcar item como comprado com checkbox, mantendo a manipulação reativa de estado em Jetpack Compose.

---

## 5. Constraints

### Required Technologies
- Kotlin
- Jetpack Compose
- Jetpack Compose Navigation (`androidx.navigation:navigation-compose`)
- Android Studio / Gradle

### Project Constraints
- Minimum SDK: API 24
- Target SDK: API 36/37
- O código das telas deve ser separado em arquivos composables dedicados no pacote `ui`, evitando concentrar toda a interface em `MainActivity.kt`.
- Não utilizar bibliotecas externas adicionais além do Navigation Compose oficial.

### Files or Modules That Must Not Be Modified
- Nenhuma pasta fora de `projects/team-08/` deve ser alterada.

---

## 6. Out of Scope

- Persistência em banco de dados local (Room ou DataStore).
- Conexão com API remota ou backend.
- Autenticação e contas de usuário.
- Edição complexa ou deleção avançada de itens com animações de gestos.

---

## 7. Acceptance Criteria

### AC-01 — SPEC-003 Documentation
**Related requirement:** FR-01, FR-02, FR-03, FR-04, FR-05, FR-06  
**Condition:** O arquivo `SPEC-003.md` deve existir no diretório `projects/team-08/docs/specs/` detalhando requisitos, critérios e fluxo.

### AC-02 — Two Meaningful Screens
**Related requirement:** FR-02, FR-03  
**Condition:** O aplicativo deve possuir duas telas distintas com propósitos claros: `WelcomeScreen` e `ShoppingListScreen`.

### AC-03 — Navigation Compose Configured
**Related requirement:** FR-01  
**Condition:** A biblioteca `androidx.navigation:navigation-compose` deve ser importada e configurada com `rememberNavController()` e `NavHost`.

### AC-04 — Forward Navigation on User Action
**Related requirement:** FR-04  
**Condition:** Tocar no botão "Ver Lista de Compras" na `WelcomeScreen` deve direcionar o usuário para a `ShoppingListScreen`.

### AC-05 — Destination Content Visible
**Related requirement:** FR-03, FR-06  
**Condition:** A tela de destino deve exibir o título e a lista de itens com opções de interação de estado.

### AC-06 — Back Navigation Functional
**Related requirement:** FR-05  
**Condition:** O botão de retorno e a navegação do sistema devem retornar com sucesso da `ShoppingListScreen` para a `WelcomeScreen`.

### AC-07 — Previous Sprint Functionality Intact
**Related requirement:** FR-06  
**Condition:** A interação de estado e responsividade visual continuam operacionais.

### AC-08 — Builds and Runs Without Crashing
**Related requirement:** FR-01, FR-02, FR-03, FR-04, FR-05, FR-06  
**Condition:** A aplicação compila pelo Gradle e executa no emulador sem falhas.

### AC-09 — Navigation Evidence Provided
**Related requirement:** FR-02, FR-03, FR-04, FR-05  
**Condition:** Devem existir capturas de tela `screen-a.png` e `screen-b.png` em `projects/team-08/evidence/sprint-03/`.

### AC-10 — Team Can Explain the Navigation Graph
**Related requirement:** FR-01  
**Condition:** A equipe compreende e sabe explicar o `NavHost`, `NavController`, rotas e `popBackStack()`.

---

## 8. Requirement Traceability

| Requirement | Implemented In | Acceptance Criterion | Evidence |
| --- | --- | --- | --- |
| FR-01 | `AppNavHost.kt` | AC-03 | `evidence/sprint-03/screen-a.png` |
| FR-02 | `WelcomeScreen.kt` | AC-02, AC-04 | `evidence/sprint-03/screen-a.png` |
| FR-03 | `ShoppingListScreen.kt` | AC-02, AC-05 | `evidence/sprint-03/screen-b.png` |
| FR-04 | `AppNavHost.kt`, `WelcomeScreen.kt` | AC-04 | `evidence/sprint-03/screen-b.png` |
| FR-05 | `AppNavHost.kt`, `ShoppingListScreen.kt` | AC-06 | `evidence/sprint-03/screen-a.png` |
| FR-06 | `ShoppingListScreen.kt` | AC-07 | `evidence/sprint-03/screen-b.png` |

---

## 9. Implementation Plan

### Components to Create or Modify
- `ui/navigation/AppNavHost.kt`: Define as rotas (`"welcome"` e `"shopping_list"`) e o `NavHost`.
- `ui/screens/WelcomeScreen.kt`: Tela inicial com ação de navegação.
- `ui/screens/ShoppingListScreen.kt`: Tela de compras com lista de itens e botão de retorno.
- `MainActivity.kt`: Inicialização simples invocando o tema e o `AppNavHost`.

### Expected Interaction Flow

```text
[WelcomeScreen ("welcome")]
       ↓
[Clica em "Ver Lista de Compras"]
       ↓ (navController.navigate("shopping_list"))
[ShoppingListScreen ("shopping_list")]
       ↓
[Interage com itens da lista / Clica em "Voltar"]
       ↓ (navController.popBackStack())
[Retorna para WelcomeScreen]
```

### Data or State Required
- `navController` gerenciado pelo Navigation Compose.
- Lista de itens em memória na tela de compras com estado de seleção/conclusão de cada item (`remember`).

---

## 10. Validation Plan

1. Compilar o projeto via Gradle (`./gradlew assembleDebug`).
2. Executar no emulador Android Studio.
3. Verificar a exibição da `WelcomeScreen` (Screen A).
4. Clicar no botão para navegar até a `ShoppingListScreen` (Screen B).
5. Interagir com itens da lista (marcar/desmarcar).
6. Acionar o botão voltar e conferir se o app retorna para a `WelcomeScreen`.
7. Salvar evidências visuais das duas telas.

---

## 11. Validation Results

| Acceptance Criterion | Result | Notes |
| --- | --- | --- |
| AC-01 | PASS | SPEC-003 criado de acordo com o template |
| AC-02 | PASS | Duas telas implementadas (`WelcomeScreen` e `ShoppingListScreen`) |
| AC-03 | PASS | Navigation Compose integrado com rotas e NavHost |
| AC-04 | PASS | Botão navega para a lista |
| AC-05 | PASS | Lista e seus itens são exibidos corretamente |
| AC-06 | PASS | Retorno via botão e sistema operacional funcionando |
| AC-07 | PASS | Interação com estado preservada na lista |
| AC-08 | PASS | Compilado e executado com sucesso |
| AC-09 | PASS | Evidências `screen-a.png` e `screen-b.png` registradas |
| AC-10 | PASS | Equipe alinhada quanto ao funcionamento do grafo de navegação |

### Environment Used for Validation
- **Device:** Android Emulator Pixel 8
- **Android version / API:** API 37
- **Build result:** PASS
- **Application execution:** PASS

---

## 12. Evidence

- `projects/team-08/evidence/sprint-03/screen-a.png`
- `projects/team-08/evidence/sprint-03/screen-b.png`

---

## 13. AI-Assisted Development

### AI Tool(s)
- **Tool:** Google Antigravity / Gemini 3.8 Flash

### How AI Was Used
- [x] Understanding the requirement
- [x] Refining the specification
- [x] Generating implementation suggestions
- [x] Explaining code
- [x] Debugging
- [x] Refactoring
- [x] Generating test ideas
- [x] Reviewing acceptance criteria

### Prompt or Request Summary
Solicitado apoio para a transição do aplicativo de tela única para múltiplas telas com Jetpack Navigation Compose, modularização dos Composables e documentação conforme a SPEC da Sprint 03.

### AI-Generated or Suggested Content
Estrutura da especificação `SPEC-003.md`, configuração do `libs.versions.toml` com Navigation Compose, criação dos componentes `AppNavHost`, `WelcomeScreen` e `ShoppingListScreen`.

### Human Review and Changes
A equipe revisou os fluxos de rotas para assegurar que a identidade e propósito do app Mercadinho fossem mantidos, com navegação intuitiva e preservação da interação de estado.
