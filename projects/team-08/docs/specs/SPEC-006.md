# SPEC-006 — External Integration (API de Receitas Brasileiras)

> **Team:** Team 08  
> **Sprint:** Sprint 06  
> **Status:** In Progress  
> **Related Sprint:** `SPRINT-06.md`

---

## 1. Context

Nas sprints anteriores, o aplicativo **Mercadinho** ofereceu aos usuários uma lista de compras intuitiva com persistência local em disco (`ShoppingRepository`) e registro das compras efetuadas. No entanto, o usuário muitas vezes vai ao supermercado sem saber exatamente o que cozinhar ou quais ingredientes específicos precisa comprar para preparar pratos típicos brasileiros.

**Problem:**  
O usuário precisa de ideias e inspirações de pratos do dia a dia da culinária brasileira (massas ao molho, feijoada, estrogonofe de frango, moqueca, pão de queijo, pudim) e perde tempo tendo que pesquisar receitas em outros lugares e digitar manualmente ingrediente por ingrediente na sua lista de compras.

**User / Actor:**  
Usuário do Mercadinho que busca sugestões de pratos culinários brasileiros e deseja adicionar rapidamente todos os ingredientes necessários à sua lista de compras com um único toque.

**Usage Context:**  
Ao planejar o cardápio da semana ou as compras do mercado, o usuário acessa a tela de "Receitas Brasileiras". O aplicativo conecta-se assincronamente à API REST pública de receitas brasileiras, exibindo fotos dos pratos prontos, categorias e listas completas de ingredientes com medidas nacionais, permitindo a importação direta para a lista de compras persistida.

---

## 2. Objective

Integrar o aplicativo a uma fonte de dados externa via REST API pública e gratuita de receitas brasileiras, consumindo dados assíncronos de pratos típicos, fotos e ingredientes, com tratamento robusto dos estados de carregamento (`Loading`), sucesso observável (`Success`) e falha ou indisponibilidade de rede (`Error / Offline`), sem a necessidade de chaves de API privadas.

---

## 3. User Scenario

### Cenário 1: Consulta de Receitas com Sucesso
**Given** que o usuário está com conexão à internet ativa,  
**When** ele acessa a tela de "Sugestões de Receitas" e seleciona uma categoria (ex: Massas),  
**Then** o aplicativo exibe o estado de carregamento e, em seguida, renderiza os cards das receitas com fotos obtidas via web, nome do prato e a lista de ingredientes com medidas.

### Cenário 2: Importação de Ingredientes para a Lista de Compras
**Given** que as receitas foram carregadas com sucesso na tela,  
**When** o usuário clica no botão "Adicionar Ingredientes à Lista",  
**Then** os ingredientes daquela receita são automaticamente inseridos no armazenamento persistente do Mercadinho e exibidos na tela principal de compras.

### Cenário 3: Falha de Conexão ou Modo Offline (Estado Alternativo)
**Given** que o dispositivo está sem conexão à internet ou o servidor está inacessível,  
**When** o usuário tenta buscar receitas ou ativa a simulação de falha de rede,  
**Then** o aplicativo intercepta a exceção e apresenta um estado alternativo amigável com mensagem de erro visual e o botão "Tentar Novamente".

---

## 4. Functional Requirements

### FR-01 — External REST API Client
A aplicação deve implementar um repositório de integração externa (`RecipeRepository`) que executa requisições HTTP GET assíncronas em segundo plano (`Dispatchers.IO`) para a API pública TheMealDB (`https://www.themealdb.com/api/json/v1/1/search.php?s=`).

### FR-02 — Recipe Data Modeling
Os dados retornados devem ser mapeados em um modelo estruturado (`RecipeItem`) contendo: `id`, `title`, `category`, `area`, `imageUrl` e a lista formatada de `ingredients` (com suas respectivas medidas).

### FR-03 — Asynchronous UI State Handling
A tela `RecipeSuggestionsScreen` deve gerenciar e refletir explicitamente os três estados da integração externa:
- `Loading`: Indicador de progresso circular (`CircularProgressIndicator`) enquanto a requisição HTTP estiver em andamento.
- `Success`: Renderização da lista de receitas com imagens carregadas via web (`AsyncImage` do Coil), títulos, categorias e ingredientes.
- `Error`: Card visual destacando a indisponibilidade do serviço/rede com opção de reexecutar a requisição (`Retry`).

### FR-04 — Remote Image Loading
As imagens das receitas fornecidas pela API externa devem ser carregadas de forma assíncrona utilizando a biblioteca Coil Compose (`AsyncImage`), com tratamento para exibição responsiva.

### FR-05 — One-Tap Ingredient Import
O usuário deve poder clicar em "Adicionar Ingredientes à Lista" em qualquer receita para persistir automaticamente os ingredientes na lista de compras local através do `ShoppingRepository`.

### FR-06 — Network Permission Configuration
O `AndroidManifest.xml` deve conter a permissão `<uses-permission android:name="android.permission.INTERNET" />` para permitir a comunicação externa do aplicativo.

---

## 5. Constraints

### Required Technologies
- Kotlin e Coroutines (`Dispatchers.IO`, `suspend`)
- Jetpack Compose e Material 3
- Coil Compose (`io.coil-kt:coil-compose`) para carregamento de imagens remotas
- API pública REST TheMealDB (Livre, sem autenticação)
- Android SDK / Gradle

### Project Constraints
- Toda a lógica de integração externa deve estar encapsulada em `data/RecipeRepository.kt`.
- **Zero segredos comprometidos:** Nenhum token, chave de API ou credencial privada deve constar no código-fonte (`AC-06`).
- Compatibilidade contínua com todas as funcionalidades desenvolvidas nas Sprints 01 a 05.

### Files or Modules That Must Not Be Modified
- Nenhuma pasta fora de `projects/team-08/` deve ser alterada.

---

## 6. Out of Scope

- Cadastro manual de novas receitas na nuvem pelos usuários.
- Edição remota de receitas existentes na base de dados do TheMealDB.
- Pagamentos ou compras automáticas em e-commerce de supermercados.

---

## 7. Acceptance Criteria

### AC-01 — SPEC-006 Documentation
**Related requirement:** FR-01 a FR-06  
**Condition:** O arquivo `SPEC-006.md` deve existir em `projects/team-08/docs/specs/` detalhando a integração externa e seus estados.

### AC-02 — Integration is Relevant to Product Goal
**Related requirement:** FR-01, FR-05  
**Condition:** A consulta de receitas culinárias fornece sugestões de ingredientes reais diretamente integrados à lista de compras do Mercadinho.

### AC-03 — App Can Trigger the Integration
**Related requirement:** FR-01, FR-06  
**Condition:** O usuário pode disparar a consulta externa ao navegar para a tela de receitas ou ao alternar entre categorias de pratos.

### AC-04 — Successful Result is Observable
**Related requirement:** FR-02, FR-03, FR-04  
**Condition:** Em caso de sucesso, o app renderiza os cards com fotos remotas, nomes das receitas e ingredientes (`integration-success.png`).

### AC-05 — Alternative / Error State Handled
**Related requirement:** FR-03  
**Condition:** Em caso de ausência de rede ou erro na requisição, a interface exibe feedback claro e botão de reintento (`integration-alternative-state.png`).

### AC-06 — No Private Secrets Committed
**Related requirement:** FR-01  
**Condition:** A integração utiliza endpoint público aberto que não requer chaves de API privadas ou senhas.

### AC-07 — Previous Functionality Remains Operational
**Related requirement:** Todas as sprints anteriores  
**Condition:** Telas de boas-vindas, lista de compras, persistência em disco e validação de finalização continuam funcionando perfeitamente.

### AC-08 — App Builds and Runs Successfully
**Related requirement:** FR-01 a FR-06  
**Condition:** Compilação com Gradle passa com sucesso (`BUILD SUCCESSFUL`).

### AC-09 — Success and Alternative Evidence Exists
**Related requirement:** AC-04, AC-05  
**Condition:** As capturas `integration-success.png` e `integration-alternative-state.png` estão salvas em `projects/team-08/evidence/sprint-06/`.

### AC-10 — Team Can Explain the Integration Flow
**Related requirement:** FR-01 a FR-05  
**Condition:** A equipe compreende o ciclo assíncrono (Coroutines/Dispatchers.IO), serialização JSON e a transição de estados de tela.

---

## 8. Requirement Traceability

| Requirement | Implemented In | Acceptance Criterion | Evidence |
| --- | --- | --- | --- |
| FR-01 | `data/RecipeRepository.kt` | AC-02, AC-03 | `evidence/sprint-06/integration-success.png` |
| FR-02 | `data/RecipeRepository.kt` | AC-04 | `evidence/sprint-06/integration-success.png` |
| FR-03 | `ui/screens/RecipeSuggestionsScreen.kt` | AC-04, AC-05 | `evidence/sprint-06/integration-success.png`, `integration-alternative-state.png` |
| FR-04 | `ui/screens/RecipeSuggestionsScreen.kt` | AC-04 | `evidence/sprint-06/integration-success.png` |
| FR-05 | `ui/screens/RecipeSuggestionsScreen.kt`, `ShoppingRepository.kt` | AC-02, AC-07 | `evidence/sprint-06/integration-success.png` |
| FR-06 | `AndroidManifest.xml` | AC-03 | `AndroidManifest.xml` |

---

## 9. Implementation Plan

### Components Created and Modified
- `AndroidManifest.xml`: Adição da permissão `android.permission.INTERNET`.
- `app/build.gradle.kts` e `gradle/libs.versions.toml`: Inclusão da biblioteca `io.coil-kt:coil-compose` para carregamento de imagens assíncronas.
- `data/RecipeRepository.kt`: Repositório assíncrono que consulta o TheMealDB, converte respostas JSON em objetos tipados e trata exceções de rede.
- `data/ShoppingRepository.kt`: Adição do método utilitário `addItemsFromRecipe` para inserção em lote dos ingredientes das receitas.
- `ui/screens/RecipeSuggestionsScreen.kt`: Tela dedicada contendo a visualização dos estados `Loading`, `Success` e `Error`, filtros de prato e botão para simulação de modo offline.
- `ui/navigation/AppNavHost.kt`: Registro da rota `recipe_suggestions` e navegação bidirecional.
- `ui/screens/WelcomeScreen.kt` e `ShoppingListScreen.kt`: Botões de acesso rápido às sugestões de receitas.

---

## 10. Validation Plan

1. Compilar o projeto com `./gradlew assembleDebug`.
2. Executar no emulador Android Studio.
3. Clicar em "Sugestões de Receitas" a partir da tela inicial ou da tela de lista de compras.
4. Aguardar o carregamento da API $\to$ Observar cards de receitas com fotos e ingredientes $\to$ Capturar `integration-success.png`.
5. Ativar o interruptor "Simular Sem Internet (Modo Offline)" ou desativar o Wi-Fi do emulador $\to$ Observar o card de erro com aviso de falha e botão de reintento $\to$ Capturar `integration-alternative-state.png`.
6. Clicar em "Adicionar Ingredientes à Lista" em uma das receitas e retornar à Lista de Compras $\to$ Verificar que os ingredientes foram persistidos em disco.

---

## 11. Validation Results

| Acceptance Criterion | Result | Notes |
| --- | --- | --- |
| AC-01 | PASS | SPEC-006 documentada com as 16 seções |
| AC-02 | PASS | Sugestões de receitas diretamente integradas à lista de compras |
| AC-03 | PASS | Disparo de requisição assíncrona funcionando perfeitamente |
| AC-04 | PASS | Imagens, títulos e ingredientes renderizados com sucesso |
| AC-05 | PASS | Estado de erro/offline tratado com feedback visual e botão de tentar novamente |
| AC-06 | PASS | API pública TheMealDB utilizada sem segredos ou tokens privados |
| AC-07 | PASS | Telas anteriores, persistência e validações íntegras |
| AC-08 | PASS | Compilação com Gradle passa com `BUILD SUCCESSFUL` |
| AC-09 | PASS | Evidências salvas em `projects/team-08/evidence/sprint-06/` |
| AC-10 | PASS | Equipe alinhada quanto à arquitetura de rede assíncrona |

### Environment Used for Validation
- **Device:** Android Emulator Pixel 8
- **Android version / API:** API 37
- **Build result:** PASS (Zero erros)
- **Application execution:** PASS

---

## 12. Evidence

- `projects/team-08/evidence/sprint-06/integration-success.png`
- `projects/team-08/evidence/sprint-06/integration-alternative-state.png`

---

## 13. AI-Assisted Development

### AI Tool(s)
- **Tool:** Google Antigravity / Gemini 3.8 Flash

### How AI Was Used
- [x] Understanding the requirement
- [x] Refining the specification
- [x] Architecture / design
- [x] Code implementation
- [x] Review / troubleshooting
- [x] Validation / testing strategy

---

## 14. Suggested Prompt for AI Assistance

```text
We are developing an Android application with Kotlin and Jetpack Compose.
Current Sprint: SPRINT-06.
Current requirement: Implement external REST API integration consuming recipes and ingredients from TheMealDB, with loading, success and error states.
Help us review the asynchronous network client and Compose state handling.
```

---

## 15. Deliverables

- `projects/team-08/app/`
- `projects/team-08/SPRINT-06.md`
- `projects/team-08/docs/specs/SPEC-006.md`
- `projects/team-08/evidence/sprint-06/integration-success.png`
- `projects/team-08/evidence/sprint-06/integration-alternative-state.png`

---

## 16. Specification Status

- **Status:** Validated
- **Approvers:** Team 08 (Ítalo Huan & Rayse Kawane)
- **Date:** 03/11/2026
