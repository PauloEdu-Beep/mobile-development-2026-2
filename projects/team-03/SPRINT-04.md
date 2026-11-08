# SPRINT 04 — User Input, Forms & Validation

> **Disciplina:** Desenvolvimento de Software Móvel (2026.2)  
> **Equipe:** Team 03  
> **Integrantes:** Célia Hiromi (@hiromilly) e Geovanna Gaspar (@gegwspar)  
> **Ciclo de Desenvolvimento:** `SPEC → BUILD → VALIDATE → EXPLAIN → COMMIT`  
> **Prazo Oficial:** 19/10/2026  

---

## 1. Campo e Fluxo de Entrada do Usuário

Nesta Sprint 04, o **Wiflux** passou a coletar a identificação do ambiente antes de iniciar o diagnóstico de rede:

| Campo | Tipo / Componente | Obrigatoriedade | Formato / Regra Permitida |
| :--- | :--- | :--- | :--- |
| **Nome do cômodo** | Texto (`OutlinedTextField`, linha única) | Obrigatório | Mínimo de **3 caracteres úteis** desconsiderando espaços nas extremidades (`roomName.trim().length >= 3`) |

---

## 2. Estado do Formulário e Regra de Validação

### Estado Controlado em `HomeScreen.kt` (`com.team03.wifi`)
O formulário utiliza duas variáveis de estado reativas lembradas com `rememberSaveable`:
```kotlin
var roomName by rememberSaveable { mutableStateOf("") }
var roomError by rememberSaveable { mutableStateOf(false) }
```

### Onde a Regra de Validação está Implementada
1. **No clique do botão `"▶ Iniciar Verificação"` (`HomeScreen.kt`):**
   ```kotlin
   Button(
       onClick = {
           if (roomName.trim().length < 3) {
               roomError = true
           } else {
               roomError = false
               signalStatus = SignalStatus.entries
                   .filter { it != SignalStatus.NONE }
                   .random(Random(System.nanoTime()))
           }
       }, ...
   )
   ```
   - `roomName.trim().length < 3` $\rightarrow$ **Inválido:** ativa `roomError = true` e **impede** a realização da verificação de sinal.
   - `roomName.trim().length >= 3` $\rightarrow$ **Válido:** garante `roomError = false` e executa a leitura de sinal normalmente.

2. **Na digitação do usuário (`onValueChange` do `OutlinedTextField` em `HomeScreen.kt`):**
   ```kotlin
   OutlinedTextField(
       value = roomName,
       onValueChange = {
           roomName = it
           roomError = false
       },
       label = { Text("Nome do cômodo") },
       isError = roomError,
       supportingText = {
           if (roomError) Text("Digite pelo menos 3 letras")
       },
       singleLine = true, ...
   )
   ```

---

## 3. Feedback Visual de Erro

Quando `roomError = true`:
- A propriedade `isError = roomError` do `OutlinedTextField` destaca a borda e o rótulo **"Nome do cômodo"** em vermelho;
- O slot `supportingText` exibe imediatamente a mensagem orientadora **"Digite pelo menos 3 letras"** abaixo do campo;
- Assim que o usuário volta a digitar qualquer caractere, `roomError` passa para `false` e o aviso de erro desaparece.

---

## 4. Cenários Testados

- **Cenários Inválidos:**
  - Campo vazio (`""`) + toque em **"▶ Iniciar Verificação"** $\rightarrow$ exibe `"Digite pelo menos 3 letras"`, mantém o dial em `"Aguardando"` e não habilita a navegação.
  - Texto curto (`"ab"`, 2 letras) + toque no botão $\rightarrow$ bloqueia a verificação e exibe o erro.
  - Apenas espaços em branco (`"   "`) + toque no botão $\rightarrow$ `trim()` reduz para `0` caracteres, bloqueando a verificação e exibindo o erro.
- **Cenário Válido:**
  - Digitar `"Quarto"` (6 caracteres) + toque em **"▶ Iniciar Verificação"** $\rightarrow$ entrada aceita sem erro, dial atualiza com a leitura (`-45 dBm` / `"Sinal Forte"`) e o botão **"Ver Detalhes"** aparece.
- **Regressão (Sprints 01, 02 e 03):**
  - Identidade visual **Wiflux** e dial em `Canvas` preservados;
  - Navegação para `DetailsScreen` ao clicar em **"Ver Detalhes"** e retorno para `HomeScreen` ao clicar em **"← Voltar"** continuam funcionando com preservação de estado.

---

## 5. Rastreabilidade com a SPEC-004

A especificação técnica formal encontra-se em:
- [`docs/specs/SPEC-004.md`](docs/specs/SPEC-004.md)

| Requisito Funcional | Critério de Aceitação | Implementação (`HomeScreen.kt`) | Evidência |
| :--- | :--- | :--- | :--- |
| **FR-01** (Campo "Nome do cômodo") | **AC-02, AC-03** | `OutlinedTextField` controlado por `roomName` | `evidence/sprint-04/valid-input.png` |
| **FR-02** (Mínimo de 3 caracteres com `trim()`) | **AC-04** | `if (roomName.trim().length < 3)` | `evidence/sprint-04/invalid-input.png` |
| **FR-03** (Erro visível e bloqueio da leitura) | **AC-05** | `isError = roomError` + `supportingText` (`"Digite pelo menos 3 letras"`) | `evidence/sprint-04/invalid-input.png` |
| **FR-04** (Entrada válida executa verificação) | **AC-06, AC-08** | `else { roomError = false; signalStatus = ... }` | `evidence/sprint-04/valid-input.png` |

---

## 6. Evidências de Validação

As capturas de tela em alta resolução dos cenários inválido e válido estão armazenadas em `projects/team-03/evidence/sprint-04/`:

### 1. Cenário Inválido (`invalid-input.png`)
Tentativa de iniciar a verificação sem atingir o mínimo de 3 caracteres: campo destacado em vermelho com a mensagem **"Digite pelo menos 3 letras"** e medição bloqueada (`Aguardando`):

![Entrada Inválida](evidence/sprint-04/invalid-input.png)

### 2. Cenário Válido (`valid-input.png`)
Campo preenchido com **"Quarto"**: entrada validada com sucesso, medição realizada (`-45 dBm` / `"Sinal Forte"`) e botão **"Ver Detalhes"** habilitado:

![Entrada Válida](evidence/sprint-04/valid-input.png)

---

## 7. Uso de Ferramentas de IA (LLM)

| Item | Team response |
| :--- | :--- |
| **LLM/tool used** | Claude & Gemini (Antigravity) |
| **Task supported by the LLM** | Auxílio na lógica de validação de estado do formulário (`roomName` e `roomError`), configuração dos parâmetros `isError` e `supportingText` do `OutlinedTextField` e revisão da `SPEC-004.md`. |
| **Main suggestion received** | Usar dois estados (`roomName` e `roomError`), validar `roomName.trim().length < 3` no `onClick` do botão **"Iniciar Verificação"** e limpar o erro no `onValueChange`. |
| **What the team changed manually** | Definição da regra de negócio obrigatória (não permitir campo em branco nem com menos de 3 caracteres úteis), estilização das cores do `OutlinedTextField` no tema escuro do **Wiflux** e execução dos testes no emulador. |
| **How the result was validated** | Testes manuais no emulador Android com campo vazio, texto curto (`"ab"`), espaços (`"   "`) e nome válido (`"Quarto"`), além de teste de regressão da navegação e compilação via Gradle (`assembleDebug` PASS). |

---

## 8. Critérios de Aceitação da Sprint 04

- [x] **AC-01** — `SPEC-004.md` existe em `docs/specs/SPEC-004.md`.
- [x] **AC-02** — Fluxo significativo de entrada de dados do usuário implementado (**"Nome do cômodo"**).
- [x] **AC-03** — Valores de entrada e erro gerenciados via estado de UI (`roomName` e `roomError`).
- [x] **AC-04** — Regra de validação implementada (`roomName.trim().length < 3`).
- [x] **AC-05** — Entrada inválida produz feedback visual claro (`isError = true` e texto `"Digite pelo menos 3 letras"`).
- [x] **AC-06** — Entrada válida (`"Quarto"`) é aceita conforme a especificação e dispara a verificação.
- [x] **AC-07** — Evidências dos cenários válido e inválido salvas em `evidence/sprint-04/valid-input.png` e `invalid-input.png`.
- [x] **AC-08** — Funcionalidades das Sprints anteriores (dial, verificação reativa e navegação entre telas) continuam funcionando.
- [x] **AC-09** — Aplicativo compila e executa com sucesso (`BUILD SUCCESSFUL`).
- [x] **AC-10** — Equipe apta a explicar a lógica de validação e o estado do formulário.

---

## 9. Checklist de Definição de Pronto (Definition of Done)

- [x] `SPEC-004.md` completa.
- [x] Fluxo de formulário e entrada do usuário implementado em `HomeScreen.kt`.
- [x] Validação implementada e testada nos cenários válido e inválido.
- [x] Evidências (`valid-input.png` e `invalid-input.png`) e uso de IA documentados.
- [x] Branch `team-03/sprint-04` pronta para submissão via Pull Request.
