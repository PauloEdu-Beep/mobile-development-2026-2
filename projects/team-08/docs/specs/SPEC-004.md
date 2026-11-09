# SPEC-004 — User Input, Forms & Validation

> **Team:** Team 08  
> **Sprint:** Sprint 04  
> **Status:** In Progress  
> **Related Sprint:** `SPRINT-04.md`

---

## 1. Context

No aplicativo **Mercadinho**, os usuários montam sua lista de compras anotando os itens ao longo do dia a dia. Ao terminar as compras no mercado físico, é essencial permitir que o usuário finalize a compra, registre o nome do estabelecimento comercial onde comprou e o valor gasto, garantindo a integridade dos dados por meio de validação de formulário.

**Problem:**  
O aplicativo não possuía um formulário controlado para fechamento das compras com validação de entrada, permitindo ações sem identificação do local da compra.

**User / Actor:**  
Usuário que concluiu suas compras e precisa registrar os dados do mercado de forma segura e validada.

**Usage Context:**  
Ao marcar os itens comprados na `ShoppingListScreen`, o usuário aciona o botão "Finalizar Compra". O aplicativo apresenta um formulário para preenchimento dos dados (nome do mercado e valor). Caso os dados estejam incompletos ou em formato inválido, mensagens de erro visíveis são exibidas. Quando válidos, a finalização é confirmada e uma nova lista limpa é gerada para as próximas compras.

---

## 2. Objective

Implementar um fluxo de entrada de dados com formulário controlado em Jetpack Compose para a finalização de compras, contendo regras de validação explícitas, feedback visual de erro (cenário inválido) e tratamento de sucesso (cenário válido).

---

## 3. User Scenario

### Cenário 1: Entrada Inválida (Tentativa de finalizar com nome vazio/curto)
**Given** que o usuário abriu o formulário de finalização de compras,  
**When** ele tenta confirmar deixando o campo "Nome do Supermercado" vazio ou com menos de 3 caracteres,  
**Then** o campo é destacado em vermelho com a mensagem de erro *"Informe o nome do supermercado (mínimo 3 caracteres)"*, e a compra não é finalizada.

### Cenário 2: Entrada Válida (Finalização com sucesso)
**Given** que o usuário preencheu o campo "Nome do Supermercado" com um nome válido (ex: "Supermercado Big Mais"),  
**When** ele clica no botão "Confirmar Compra",  
**Then** a validação é aprovada, uma mensagem de sucesso é apresentada, e o aplicativo reinicia a lista desmarcando os itens para o próximo ciclo de compras.

---

## 4. Functional Requirements

### FR-01 — Checkout Action Button
A tela `ShoppingListScreen` deve fornecer um botão de destaque "Finalizar Compra" para acionar o formulário de fechamento.

### FR-02 — Controlled Form Fields
O formulário de finalização deve conter:
- Campo **"Nome do Supermercado"** (texto obrigatório).
- Campo **"Valor Total Gasto (R$)"** (numérico decimal opcional).
- Indicação visual da data da compra.

### FR-03 — Market Name Validation Rule
O campo "Nome do Supermercado" deve ter no mínimo 3 caracteres válidos (desconsiderando espaços nas pontas). Se a regra falhar, deve exibir mensagem de erro explícita e marcar o campo com estado de erro (`isError = true`).

### FR-04 — Total Amount Validation Rule
Caso o usuário informe um valor no campo de valor total, a aplicação deve validar se o formato corresponde a um número decimal válido maior ou igual a zero. Se inválido, deve exibir mensagem de erro explicativa.

### FR-05 — Visual Error Feedback
O formulário deve atualizar o estado visual imediatamente ao tentar submeter dados inválidos, utilizando componentes de erro do Material 3 (`supportingText` com mensagem de erro e borda vermelha).

### FR-06 — Successful Submission & List Reset
Ao preencher os dados corretamente e confirmar, a aplicação deve exibir mensagem de sucesso, fechar o formulário e reiniciar os itens da lista como desmarcados para o próximo ciclo de compras do usuário.

---

## 5. Constraints

### Required Technologies
- Kotlin
- Jetpack Compose (Material 3)
- Android Studio / Gradle

### Project Constraints
- Gerenciamento de estado controlado com `remember` e `mutableStateOf`.
- Não utilizar bibliotecas externas de validação; aplicar validação declarativa e nativa do Compose.
- Manter compatibilidade com a navegação da Sprint 03 e interações das sprints anteriores.

### Files or Modules That Must Not Be Modified
- Nenhuma pasta fora de `projects/team-08/` deve ser alterada.

---

## 6. Out of Scope

- Armazenamento permanente em banco de dados SQLite/Room (escopo da Sprint 05).
- Integração com leitor de código de barras ou nota fiscal eletrônica.
- Pagamento ou integração com gateway bancário.

---

## 7. Acceptance Criteria

### AC-01 — SPEC-004 Documentation
**Related requirement:** FR-01, FR-02, FR-03, FR-04, FR-05, FR-06  
**Condition:** O arquivo `SPEC-004.md` deve existir em `projects/team-08/docs/specs/` detalhando os requisitos e critérios.

### AC-02 — Meaningful Form Input Flow
**Related requirement:** FR-01, FR-02  
**Condition:** O formulário de finalização de compras deve permitir a inserção controlada dos dados da compra.

### AC-03 — Controlled UI State
**Related requirement:** FR-02  
**Condition:** Os valores digitados nos campos do formulário devem ser controlados via estado reativo do Compose.

### AC-04 — Validation Rules Implemented
**Related requirement:** FR-03, FR-04  
**Condition:** Regras de obrigatoriedade/tamanho mínimo para o nome do mercado e formato numérico para o valor devem estar ativas.

### AC-05 — Visible Feedback on Invalid Input
**Related requirement:** FR-05  
**Condition:** Tentar submeter o formulário sem o nome do mercado deve gerar borda vermelha e texto de erro visível na interface (`invalid-input.png`).

### AC-06 — Successful Input Processing
**Related requirement:** FR-06  
**Condition:** Preencher dados válidos deve aprovar a submissão, exibir feedback positivo e reiniciar o estado da lista (`valid-input.png`).

### AC-07 — Evidence Screenshots
**Related requirement:** FR-05, FR-06  
**Condition:** Devem existir `valid-input.png` e `invalid-input.png` na pasta `projects/team-08/evidence/sprint-04/`.

### AC-08 — Previous Functionality Preserved
**Related requirement:** FR-01  
**Condition:** A navegação entre telas, inclusão, exclusão e marcação de itens da Sprint 03 continuam operacionais.

### AC-09 — Builds and Runs Without Errors
**Related requirement:** FR-01 a FR-06  
**Condition:** O projeto compila com sucesso via Gradle e roda no emulador sem falhas.

### AC-10 — Team Can Explain the Validation Logic
**Related requirement:** FR-03, FR-04, FR-05  
**Condition:** A equipe compreende e sabe explicar o estado controlado, as checagens condicionais e o feedback visual de erro.

---

## 8. Requirement Traceability

| Requirement | Implemented In | Acceptance Criterion | Evidence |
| --- | --- | --- | --- |
| FR-01 | `ShoppingListScreen.kt` | AC-02 | `evidence/sprint-04/valid-input.png` |
| FR-02 | `ShoppingListScreen.kt` | AC-02, AC-03 | `evidence/sprint-04/valid-input.png` |
| FR-03 | `ShoppingListScreen.kt` | AC-04, AC-05 | `evidence/sprint-04/invalid-input.png` |
| FR-04 | `ShoppingListScreen.kt` | AC-04, AC-05 | `evidence/sprint-04/invalid-input.png` |
| FR-05 | `ShoppingListScreen.kt` | AC-05 | `evidence/sprint-04/invalid-input.png` |
| FR-06 | `ShoppingListScreen.kt` | AC-06 | `evidence/sprint-04/valid-input.png` |

---

## 9. Implementation Plan

### Components to Create or Modify
- `ShoppingListScreen.kt`: Adição do botão "Finalizar Compra" e do diálogo modal `FinalizarCompraDialog` com campos controlados, validação e feedback.

### Validation Flow

```text
[Usuário clica em "Finalizar Compra"]
              ↓
[Abre formulário modal com campos: Mercado e Valor]
              ↓
[Usuário clica em "Confirmar"]
              ├── (Nome vazio / < 3 chars) ──> Exibe erro em vermelho (Cenário Inválido)
              └── (Dados válidos) ───────────> Confirma sucesso e reseta itens (Cenário Válido)
```

---

## 10. Validation Plan

1. Executar `./gradlew assembleDebug` para certificar a compilação.
2. Abrir o app no emulador e navegar até a lista de compras.
3. Clicar em "Finalizar Compra".
4. Tentar clicar em "Confirmar" sem digitar nada no campo de supermercado $\to$ Capturar `invalid-input.png`.
5. Digitar "Supermercado Big Mais" e um valor $\to$ Clicar em "Confirmar" $\to$ Capturar `valid-input.png`.
6. Verificar se a lista foi limpa e se a navegação permanece íntegra.

---

## 11. Validation Results

| Acceptance Criterion | Result | Notes |
| --- | --- | --- |
| AC-01 | PASS | SPEC-004 documentada de acordo com o template |
| AC-02 | PASS | Fluxo de finalização de compras implementado |
| AC-03 | PASS | Estados controlados com `mutableStateOf` |
| AC-04 | PASS | Regras de validação de tamanho mínimo e valor numérico |
| AC-05 | PASS | Feedback visual com `isError` e mensagem explicativa |
| AC-06 | PASS | Submissão válida confirmada com sucesso |
| AC-07 | PASS | Evidências registradas em `evidence/sprint-04/` |
| AC-08 | PASS | Navegação e manipulação de itens preservadas |
| AC-09 | PASS | Compilado e testado com sucesso |
| AC-10 | PASS | Equipe alinhada quanto à lógica de validação do formulário |

### Environment Used for Validation
- **Device:** Android Emulator Pixel 8
- **Android version / API:** API 37
- **Build result:** PASS
- **Application execution:** PASS

---

## 12. Evidence

- `projects/team-08/evidence/sprint-04/invalid-input.png`
- `projects/team-08/evidence/sprint-04/valid-input.png`

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
Desenvolvimento de formulário de finalização de compras com campos controlados, validação de regras de preenchimento e mensagens de erro visíveis no Jetpack Compose.

### AI-Generated or Suggested Content
Estrutura da `SPEC-004.md`, implementação do diálogo de finalização com validação em tempo real e tratamento de cenários válidos e inválidos.

### Human Review and Changes
A equipe definiu as regras de validação (supermercado obrigatório com no mínimo 3 caracteres e valor opcional válido) e testou os fluxos de sucesso e erro na interface.
