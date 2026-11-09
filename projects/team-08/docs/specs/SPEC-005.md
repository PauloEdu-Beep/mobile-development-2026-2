# SPEC-005 — Local Persistence & Data

> **Team:** Team 08  
> **Sprint:** Sprint 05  
> **Status:** In Progress  
> **Related Sprint:** `SPRINT-05.md`

---

## 1. Context

Nas sprints anteriores, o aplicativo **Mercadinho** implementou múltiplas telas, controle de lista de compras e formulário de finalização com validação. No entanto, os dados existiam apenas em memória (`remember`), fazendo com que qualquer item adicionado ou compra finalizada fosse perdido assim que o usuário fechasse o aplicativo.

**Problem:**  
A ausência de persistência local impedia que o usuário mantivesse sua lista de compras anotada entre diferentes utilizações do aplicativo, violando o propósito central do produto de ser um companheiro diário para compras.

**User / Actor:**  
Usuário do Mercadinho que adiciona itens, marca compras realizadas e espera que seus registros continuem salvos mesmo após reiniciar o celular ou fechar o app.

**Usage Context:**  
O usuário anota itens de compras ou finaliza uma compra com o nome do supermercado. O app salva os dados no armazenamento local permanente. Ao fechar completamente o aplicativo e abri-lo novamente, todos os itens e o histórico da última compra são restaurados automaticamente.

---

## 2. Objective

Implementar uma camada de persistência de dados local desacoplada da interface (`ShoppingRepository`), garantindo que itens da lista de compras (criação, alteração de estado e exclusão) e o registro da última compra finalizada sobrevivam ao fechamento e reinício do aplicativo.

---

## 3. User Scenario

### Cenário 1: Criação e Persistência de Dados
**Given** que o usuário está utilizando o Mercadinho,  
**When** ele adiciona um novo item à lista (ex: "Detergente Líquido") ou finaliza uma compra registrando o nome do mercado,  
**Then** os dados são gravados de forma permanente no armazenamento local do dispositivo.

### Cenário 2: Restauração de Dados após Reinício do App
**Given** que o usuário fechou completamente o aplicativo (processo encerrado),  
**When** ele inicia o Mercadinho novamente e abre a lista de compras,  
**Then** todos os itens previamente adicionados/marcados e as informações da última compra são recuperados do disco e apresentados na interface.

---

## 4. Functional Requirements

### FR-01 — Local Storage Mechanism
A aplicação deve implementar um repositório de persistência local (`ShoppingRepository`) responsável por ler e gravar dados no armazenamento permanente do dispositivo Android.

### FR-02 — Persistent Shopping Items Model
O modelo de dados de itens da lista (`ShoppingItem`) composto por `id`, `name` e `isChecked` deve ser serializado e gravado no disco sempre que houver adição, exclusão ou alteração de estado.

### FR-03 — Automatic Data Restoration on Launch
Ao iniciar a tela da lista de compras (`ShoppingListScreen`), a aplicação deve ler automaticamente os registros do armazenamento local e atualizar o estado da interface.

### FR-04 — Persistent Purchase History Record
Ao finalizar uma compra com o formulário validado, a aplicação deve salvar o registro de compra (`PurchaseRecord`) contendo o nome do supermercado, valor gasto e data.

### FR-05 — Visual History Display
A tela deve exibir um card discreto com os dados da última compra finalizada recuperada do armazenamento persistente, permitindo ao usuário conferir seu histórico recente.

---

## 5. Constraints

### Required Technologies
- Kotlin
- Jetpack Compose
- Android Local Storage Framework (`SharedPreferences` com serialização estruturada JSON)
- Android Studio / Gradle

### Project Constraints
- Separação clara de responsabilidades: a lógica de acesso a dados deve residir no pacote `data/` (`ShoppingRepository`), sem poluir os Composables de interface com código de I/O direto.
- Não introduzir dependências desnecessárias ou incompatíveis que quebrem a compilação.
- Compatibilidade contínua com as funcionalidades das Sprints 01, 02, 03 e 04.

### Files or Modules That Must Not Be Modified
- Nenhuma pasta fora de `projects/team-08/` deve ser alterada.

---

## 6. Out of Scope

- Sincronização em nuvem ou banco de dados remoto (Firebase, AWS, etc.).
- Compartilhamento de lista em tempo real entre diferentes celulares via rede.
- Exportação de relatórios em PDF ou planilhas externas.

---

## 7. Acceptance Criteria

### AC-01 — SPEC-005 Documentation
**Related requirement:** FR-01, FR-02, FR-03, FR-04, FR-05  
**Condition:** O arquivo `SPEC-005.md` deve existir em `projects/team-08/docs/specs/` detalhando o modelo de dados e critérios.

### AC-02 — Appropriate Persistence Mechanism Selected
**Related requirement:** FR-01  
**Condition:** Repositório de persistência implementado no pacote de dados utilizando armazenamento local permanente no dispositivo.

### AC-03 — Required Persistent Data Modeled
**Related requirement:** FR-02, FR-04  
**Condition:** Modelos `ShoppingItem` e `PurchaseRecord` estruturados para persistência.

### AC-04 — Data Can Be Saved and Modified
**Related requirement:** FR-02, FR-04  
**Condition:** Operações de adicionar, marcar, excluir e finalizar compra gravam no armazenamento local (`data-created.png`).

### AC-05 — Data Can Be Read and Displayed
**Related requirement:** FR-03, FR-05  
**Condition:** Dados salvos são lidos na inicialização da tela e renderizados na interface.

### AC-06 — Data Survives App Restart
**Related requirement:** FR-02, FR-03, FR-04  
**Condition:** Fechar o app pelo gerenciador de tarefas do Android e reabri-lo restaura integralmente os dados salvos (`data-restored.png`).

### AC-07 — Previous Functionality Remains Operational
**Related requirement:** FR-01 a FR-05  
**Condition:** Navegação entre telas, validação de formulário e interações de interface funcionam sem regressões.

### AC-08 — App Builds Successfully
**Related requirement:** FR-01 a FR-05  
**Condition:** Compilação com Gradle passa com sucesso (`BUILD SUCCESSFUL`).

### AC-09 — Persistence Evidence Exists
**Related requirement:** AC-04, AC-06  
**Condition:** Imagens `data-created.png` e `data-restored.png` presentes em `projects/team-08/evidence/sprint-05/`.

### AC-10 — Team Can Explain Data Flow
**Related requirement:** FR-01, FR-02, FR-03  
**Condition:** A equipe compreende e sabe explicar o ciclo de persistência e recuperação dos dados.

---

## 8. Requirement Traceability

| Requirement | Implemented In | Acceptance Criterion | Evidence |
| --- | --- | --- | --- |
| FR-01 | `ShoppingRepository.kt` | AC-02 | `evidence/sprint-05/data-created.png` |
| FR-02 | `ShoppingItem.kt`, `ShoppingRepository.kt` | AC-03, AC-04 | `evidence/sprint-05/data-created.png` |
| FR-03 | `ShoppingListScreen.kt`, `ShoppingRepository.kt` | AC-05, AC-06 | `evidence/sprint-05/data-restored.png` |
| FR-04 | `ShoppingRepository.kt`, `ShoppingListScreen.kt` | AC-04 | `evidence/sprint-05/data-created.png` |
| FR-05 | `ShoppingListScreen.kt` | AC-05, AC-06 | `evidence/sprint-05/data-restored.png` |

---

## 9. Implementation Plan

### Components to Create or Modify
- `data/ShoppingRepository.kt`: Responsável por encapsular operações de leitura e gravação no armazenamento local.
- `ui/screens/ShoppingListScreen.kt`: Integrado com o repositório para carregar os dados na montagem do Composable e disparar gravações a cada alteração do usuário.

### Data Flow

```text
[Usuário interage: Adiciona/Marca item ou Finaliza compra]
                       ↓
[Interface atualiza estado local Compose]
                       ↓
[ShoppingRepository grava dados no armazenamento do dispositivo]
                       ↓
─── [Usuário fecha o aplicativo / encerra processo] ───
                       ↓
[Usuário reabre o aplicativo]
                       ↓
[ShoppingRepository lê dados persistidos do disco]
                       ↓
[Interface exibe a lista e histórico intactos]
```

---

## 10. Validation Plan

1. Compilar o projeto com `./gradlew assembleDebug`.
2. Executar no emulador.
3. Adicionar um item novo (ex: "Detergente Líquido") e finalizar uma compra com nome do mercado $\to$ Capturar `data-created.png`.
4. Fechar o aplicativo pelo botão de visão geral de apps do Android (arrastando para fechar/encerrar o processo).
5. Reabrir o aplicativo Mercadinho e navegar até a lista de compras.
6. Verificar que o item e o histórico continuam presentes $\to$ Capturar `data-restored.png`.

---

## 11. Validation Results

| Acceptance Criterion | Result | Notes |
| --- | --- | --- |
| AC-01 | PASS | SPEC-005 documentada de acordo com o template oficial |
| AC-02 | PASS | Camada de persistência local implementada em `ShoppingRepository` |
| AC-03 | PASS | Modelos estruturados de dados persistidos |
| AC-04 | PASS | Operações de gravação persistentes validadas |
| AC-05 | PASS | Leitura e carregamento automático validados |
| AC-06 | PASS | Dados sobrevivem ao encerramento do processo e reinício |
| AC-07 | PASS | Navegação e validação de formulário preservadas |
| AC-08 | PASS | Compilado e executado com sucesso |
| AC-09 | PASS | Evidências registradas em `evidence/sprint-05/` |
| AC-10 | PASS | Equipe alinhada quanto à arquitetura de persistência |

### Environment Used for Validation
- **Device:** Android Emulator Pixel 8
- **Android version / API:** API 37
- **Build result:** PASS
- **Application execution:** PASS

---

## 12. Evidence

- `projects/team-08/evidence/sprint-05/data-created.png`
- `projects/team-08/evidence/sprint-05/data-restored.png`

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
- [x] Reviewing acceptance criteria

### Prompt or Request Summary
Modelagem da arquitetura de persistência local para a Sprint 05, elaboração da `SPEC-005.md` e implementação de repositório de dados para sobreviver ao reinício da aplicação.

### AI-Generated or Suggested Content
Estrutura da especificação `SPEC-005.md`, implementação do `ShoppingRepository` com serialização estruturada e integração reativa na `ShoppingListScreen`.

### Human Review and Changes
A equipe validou o ciclo de vida dos dados, assegurando que o app restaura a lista com precisão após o fechamento completo do processo no emulador Android.
