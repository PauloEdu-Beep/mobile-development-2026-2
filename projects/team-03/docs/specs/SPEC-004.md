# SPEC-004 — Nome do Cômodo (Entrada do Usuário, Formulário e Validação)

> **Equipe:** Team 03  
> **Integrantes:** Célia Hiromi (@hiromilly) e Geovanna Gaspar (@gegwspar)  
> **Sprint:** SPRINT 04 — User Input, Forms & Validation  
> **Status:** Validado  
> **Documento da Sprint:** [`SPRINT-04.md`](../../SPRINT-04.md)  

---

## 1. Contexto

**Problema:**  
O **Wiflux** mede a qualidade do sinal Wi-Fi em diferentes ambientes de uma residência ou escritório. Nas Sprints anteriores, o usuário podia iniciar a verificação de sinal imediatamente sem identificar qual local estava sendo avaliado. Para que a leitura tenha contexto útil, é necessário coletar o nome do cômodo (ex: `"Quarto"`, `"Sala"`, `"Escritório"`) e validar essa entrada antes de permitir a verificação, impedindo medições com campos em branco, apenas espaços ou textos curtos demais.

**Usuário / Ator:**  
Estudantes, trabalhadores remotos e usuários domésticos que desejam identificar o cômodo avaliado antes de verificar a intensidade do sinal Wi-Fi.

**Contexto de Uso:**  
Na tela principal (`HomeScreen`), o usuário preenche o campo de texto **"Nome do cômodo"** e toca em **"▶ Iniciar Verificação"**. Se o nome tiver menos de 3 caracteres úteis (desconsiderando espaços nas extremidades com `trim()`), o aplicativo bloqueia a verificação, destaca o campo em vermelho e exibe a mensagem de erro **"Digite pelo menos 3 letras"**. Quando o usuário informa um nome válido (como `"Quarto"`), a verificação é executada normalmente.

---

## 2. Objetivo

O objetivo desta funcionalidade é implementar um fluxo controlado de entrada de dados e validação de formulário no **Wiflux** utilizando Jetpack Compose (`OutlinedTextField` e estado reativo de UI), garantindo feedback visual claro para entradas inválidas e execução normal da verificação para entradas válidas, mantendo intactas a navegação e as funcionalidades das Sprints 01, 02 e 03.

---

## 3. Cenários do Usuário (User Scenarios)

### Cenário 1: Tentativa de Verificação com Entrada Inválida
**Dado** que o usuário está na `HomeScreen` e deixou o campo **"Nome do cômodo"** vazio (`""`), apenas com espaços (`"   "`) ou com menos de 3 caracteres úteis (`"ab"`),  
**Quando** tocar no botão **"▶ Iniciar Verificação"**,  
**Então** o aplicativo não deve realizar a verificação de sinal (mantendo o estado anterior), deve ativar o estado de erro no campo (`isError = true`, borda e rótulo vermelhos) e exibir a mensagem **"Digite pelo menos 3 letras"** abaixo do campo.

### Cenário 2: Limpeza Automática do Erro ao Digitar
**Dado** que o campo **"Nome do cômodo"** está em estado de erro exibindo **"Digite pelo menos 3 letras"**,  
**Quando** o usuário digitar ou editar qualquer caractere no campo (`onValueChange`),  
**Então** o estado de erro deve ser limpo imediatamente (`roomError = false`).

### Cenário 3: Verificação com Entrada Válida
**Dado** que o usuário digitou um nome válido com pelo menos 3 caracteres úteis (ex: `"Quarto"`),  
**Quando** tocar no botão **"▶ Iniciar Verificação"**,  
**Então** o erro permanece desativado (`roomError = false`), o nível de sinal é verificado e exibido no dial (ex: `-45 dBm` / `"Sinal Forte"`) e o botão **"Ver Detalhes"** é disponibilizado.

---

## 4. Requisitos Funcionais e Regras do Formulário

### Definição dos Campos e Regras de Validação

| Campo | Status | Componente Compose | Formato / Valores Permitidos | Regra de Validação | Feedback de Erro (Inválido) | Comportamento de Sucesso (Válido) |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Nome do cômodo** | **Obrigatório** | `OutlinedTextField` (`singleLine = true`) | Texto livre identificando o ambiente (ex: `"Quarto"`, `"Sala"`, `"Copa"`) | `roomName.trim().length >= 3` (mínimo de 3 caracteres úteis após remover espaços nas extremidades) | Borda e rótulo ficam vermelhos (`isError = true`), exibe `supportingText` **"Digite pelo menos 3 letras"** e bloqueia a verificação | Limpa o erro (`roomError = false`), sorteia e exibe o status de sinal no dial e habilita o botão **"Ver Detalhes"** |

### FR-01 — Campo Controlado "Nome do cômodo"
A `HomeScreen` deve apresentar um componente `OutlinedTextField` de linha única (`singleLine = true`) identificado pelo rótulo **"Nome do cômodo"**, cujo valor é controlado pela variável de estado reativa `roomName`.

### FR-02 — Regra de Validação Obrigatória
O campo **"Nome do cômodo"** é obrigatório e deve possuir no mínimo **3 caracteres úteis**, desconsiderando espaços em branco no início e no fim (`roomName.trim().length >= 3`).

### FR-03 — Bloqueio e Feedback Visual de Erro
Se `roomName.trim().length < 3` no momento do clique em **"▶ Iniciar Verificação"**:
- A variável de estado `roomError` deve receber `true`;
- O `OutlinedTextField` deve ativar `isError = roomError` e exibir `supportingText` com a mensagem **"Digite pelo menos 3 letras"**;
- A verificação de sinal não deve ser executada (o estado de sinal permanece inalterado).

### FR-04 — Aceitação de Entrada Válida e Limpeza ao Digitar
- Ao digitar no campo (`onValueChange`), `roomName` deve ser atualizado e `roomError` deve voltar imediatamente para `false`;
- Se `roomName.trim().length >= 3` ao clicar em **"▶ Iniciar Verificação"**, `roomError` deve ser `false` e a medição de sinal deve ser executada e exibida no dial normalmente.

---

## 5. Restrições Técnicas (Constraints)

### Tecnologias Obrigatórias
- Kotlin
- Jetpack Compose (Material 3)
- Navigation Compose (`androidx.navigation:navigation-compose:2.8.4`)
- Android Studio

### Restrições do Projeto
- **Estado Declarativo no Compose:** Uso de `rememberSaveable` e `mutableStateOf` para gerenciar `roomName` (`String`), `roomError` (`Boolean`) e `signalStatus` (`SignalStatus`), garantindo preservação do estado ao navegar para `DetailsScreen` e voltar.
- **Escopo da Sprint 04:** Toda a implementação deve permanecer dentro de `projects/team-03/`. Sem uso de APIs externas ou novas bibliotecas além das já presentes no projeto.

### Arquivos ou Módulos que Não Devem Ser Modificados Fora do Escopo
- Pastas de outras equipes (`projects/team-XX/`).
- `SignalStatus.kt` e `DetailsScreen.kt` mantêm sua estrutura da Sprint 03 sem quebras de compatibilidade.

---

## 6. Fora do Escopo (Out of Scope)

Os seguintes itens estão fora do escopo desta especificação:
- Persistência do histórico de cômodos em banco de dados local SQLite/Room (escopo da Sprint 05).
- Múltiplos formulários complexos ou configurações avançadas de roteador.
- Leitura de hardware Wi-Fi real via `WifiManager` (escopo da Sprint 06).

---

## 7. Critérios de Aceitação (Acceptance Criteria)

### AC-01 — Existência da SPEC-004
**Requisito relacionado:** Documentação da Sprint 04  
**Condição:** O documento `SPEC-004.md` existe em `projects/team-03/docs/specs/SPEC-004.md` definindo campos, obrigatoriedade, regras de validação, feedback de erro e comportamento esperado.

### AC-02 — Fluxo Significativo de Entrada do Usuário
**Requisito relacionado:** FR-01  
**Condição:** O aplicativo possui o campo **"Nome do cômodo"** na `HomeScreen`, contextualizando cada verificação de sinal Wi-Fi.

### AC-03 — Valores de Entrada Utilizam Estado de UI
**Requisito relacionado:** FR-01, FR-04  
**Condição:** O texto digitado (`roomName`) e o indicador de erro (`roomError`) são gerenciados via estado reativo do Compose (`rememberSaveable { mutableStateOf(...) }`).

### AC-04 — Regra de Validação Implementada
**Requisito relacionado:** FR-02  
**Condição:** A validação verifica `roomName.trim().length < 3` antes de iniciar a medição, rejeitando campos vazios, compostos apenas por espaços ou com menos de 3 letras.

### AC-05 — Entrada Inválida Produz Feedback Visível
**Requisito relacionado:** FR-03  
**Condição:** Quando a entrada é inválida e o usuário toca em **"▶ Iniciar Verificação"**, o campo fica destacado em vermelho (`isError = true`), exibe a mensagem **"Digite pelo menos 3 letras"** e impede a medição.

### AC-06 — Entrada Válida é Aceita Conforme a SPEC
**Requisito relacionado:** FR-04  
**Condição:** Quando o usuário informa 3 ou mais caracteres úteis (ex: `"Quarto"`) e toca em **"▶ Iniciar Verificação"**, nenhum erro é exibido, o dial atualiza com o sinal medido (ex: `-45 dBm` / `"Sinal Forte"`) e o botão **"Ver Detalhes"** aparece.

### AC-07 — Evidências Válida e Inválida Existem
**Requisito relacionado:** FR-03, FR-04  
**Condição:** As capturas de tela `invalid-input.png` e `valid-input.png` estão salvas em `projects/team-03/evidence/sprint-04/`.

### AC-08 — Funcionalidades Anteriores Continuam Funcionando
**Requisito relacionado:** FR-04  
**Condição:** O dial em `Canvas` (Sprints 01/02) e a navegação de ida e volta entre `HomeScreen` e `DetailsScreen` com preservação de estado (Sprint 03) continuam funcionando normalmente.

### AC-09 — Aplicativo Compila e Executa com Sucesso
**Requisito relacionado:** Todos (FR-01 a FR-04)  
**Condição:** O projeto compila via Gradle (`assembleDebug` `BUILD SUCCESSFUL`) e executa no emulador Android sem falhas.

### AC-10 — Equipe Apta a Explicar a Lógica de Validação
**Requisito relacionado:** Todos (FR-01 a FR-04)  
**Condição:** Todas as integrantes compreendem e sabem localizar e explicar o estado do formulário (`roomName`, `roomError`) e a condição `roomName.trim().length < 3` em `HomeScreen.kt`.

---

## 8. Rastreabilidade dos Requisitos (Requirement Traceability)

| Requisito | Implementado Em (`com.team03.wifi`) | Critério de Aceitação | Evidência |
| :--- | :--- | :--- | :--- |
| **FR-01** | `HomeScreen.kt` (`var roomName`, `OutlinedTextField`) | AC-02, AC-03 | `evidence/sprint-04/valid-input.png` |
| **FR-02** | `HomeScreen.kt` (`if (roomName.trim().length < 3)`) | AC-04 | `evidence/sprint-04/invalid-input.png` |
| **FR-03** | `HomeScreen.kt` (`isError = roomError`, `supportingText`) | AC-05, AC-07 | `evidence/sprint-04/invalid-input.png` |
| **FR-04** | `HomeScreen.kt` (`onValueChange`, `else { roomError = false; signalStatus = ... }`) | AC-06, AC-07, AC-08 | `evidence/sprint-04/valid-input.png` |

---

## 9. Plano de Implementação (Implementation Plan)

### Componentes Modificados
- `HomeScreen.kt` (`com.team03.wifi`):
  - Adição dos estados `roomName` e `roomError` com `rememberSaveable`;
  - Adição do componente `OutlinedTextField` estilizado no tema escuro do **Wiflux** com suporte a `isError` e `supportingText`;
  - Inclusão da condicional de validação `if (roomName.trim().length < 3)` no `onClick` do botão **"▶ Iniciar Verificação"**.

### Fluxo de Interação Esperado
```text
[Estado Inicial: roomName = "", roomError = false, signalStatus = NONE]
      ↓
[Usuário toca em "▶ Iniciar Verificação" com campo vazio ou < 3 letras]
      ↓
[Validação reprova → roomError = true → Campo vermelho + "Digite pelo menos 3 letras"]
      ↓
[Usuário digita "Quarto" → onValueChange limpa roomError = false]
      ↓
[Usuário toca em "▶ Iniciar Verificação" → Validação aprova (length >= 3)]
      ↓
[Estado Final: signalStatus atualizado (-45 dBm / Sinal Forte) + Botão "Ver Detalhes" visível]
```

### Dados e Estados Necessários
- `roomName: String` — texto atual do campo `"Nome do cômodo"`, preservado com `rememberSaveable`.
- `roomError: Boolean` — indica se a última tentativa de envio falhou na validação, preservado com `rememberSaveable`.
- `signalStatus: SignalStatus` — estado atual da medição de sinal Wi-Fi, preservado com `rememberSaveable`.

---

## 10. Plano de Validação (Validation Plan)

A equipe deve:
1. Compilar o projeto com sucesso via Gradle (`assembleDebug`).
2. Executar o aplicativo em um Emulador Android.
3. Testar o cenário inválido (campo vazio, `"ab"`, `"   "`) e verificar o bloqueio da leitura e a mensagem **"Digite pelo menos 3 letras"**.
4. Verificar se o erro desaparece imediatamente ao voltar a digitar no campo.
5. Testar o cenário válido digitando `"Quarto"` e acionando **"▶ Iniciar Verificação"**.
6. Testar a navegação para a tela de detalhes (**"Ver Detalhes"**) e o retorno (**"← Voltar"**) verificando se `"Quarto"` e o sinal medido permanecem na tela.
7. Coletar as evidências `invalid-input.png` e `valid-input.png` em `evidence/sprint-04/`.

---

## 11. Resultados da Validação (Validation Results)

| Critério de Aceitação | Resultado | Observações |
| :--- | :---: | :--- |
| **AC-01** | **PASS** | `SPEC-004.md` criada e preenchida com todas as regras de validação. |
| **AC-02** | **PASS** | Campo `"Nome do cômodo"` integrado ao fluxo de verificação na `HomeScreen`. |
| **AC-03** | **PASS** | Estados `roomName` e `roomError` declarados com `rememberSaveable { mutableStateOf(...) }`. |
| **AC-04** | **PASS** | Regra `roomName.trim().length < 3` implementada no botão `"▶ Iniciar Verificação"`. |
| **AC-05** | **PASS** | Borda/rótulo em vermelho (`isError = true`) e texto `"Digite pelo menos 3 letras"` exibidos. |
| **AC-06** | **PASS** | Entrada `"Quarto"` aceita, exibindo `-45 dBm` (`Sinal Forte`) e liberando `"Ver Detalhes"`. |
| **AC-07** | **PASS** | Capturas `invalid-input.png` e `valid-input.png` salvas em `evidence/sprint-04/`. |
| **AC-08** | **PASS** | Dial em `Canvas` e navegação `HomeScreen` ↔ `DetailsScreen` funcionando sem regressão. |
| **AC-09** | **PASS** | Build Gradle concluído com `BUILD SUCCESSFUL` e execução sem travamentos. |
| **AC-10** | **PASS** | Código enxuto, legível e revisado pelas integrantes da equipe. |

### Ambiente Utilizado na Validação
- **Dispositivo:** Emulador Android (Medium Phone / Pixel)
- **Versão do Android / API:** Android 14 (API 34 / compileSdk 36)
- **Resultado do Build:** PASS (`BUILD SUCCESSFUL`)
- **Execução do Aplicativo:** PASS

---

## 12. Evidências (Evidence)

Evidências exigidas armazenadas em `projects/team-03/evidence/sprint-04/`:
- `evidence/sprint-04/invalid-input.png`
- `evidence/sprint-04/valid-input.png`

| 1. Entrada Inválida (`invalid-input.png`) | 2. Entrada Válida (`valid-input.png`) |
| :---: | :---: |
| ![Entrada Inválida](../../evidence/sprint-04/invalid-input.png) | ![Entrada Válida](../../evidence/sprint-04/valid-input.png) |
| Campo em estado de erro com a mensagem `"Digite pelo menos 3 letras"` e medição bloqueada (`Aguardando`) | Campo preenchido com `"Quarto"`, verificação realizada (`-45 dBm` / `Sinal Forte`) e botão `"Ver Detalhes"` ativo |

---

## 13. Desenvolvimento com Apoio de IA (AI-Assisted Development)

### Ferramentas de IA
- **Ferramenta:** Claude e Gemini (Antigravity)

### Como a IA Foi Utilizada
- [x] Compreensão do requisito
- [x] Refinamento da especificação (`SPEC-004.md`)
- [x] Geração de sugestões de implementação
- [x] Explicação de código (`isError`, `supportingText`, `rememberSaveable`)
- [ ] Depuração (Debugging)
- [ ] Refatoração
- [x] Geração de ideias de teste (casos de borda com espaços em branco)
- [x] Revisão dos critérios de aceitação

### Resumo do Prompt / Solicitação
Foi solicitado apoio para implementar a validação controlada do campo **"Nome do cômodo"** em `HomeScreen.kt` usando `OutlinedTextField` do Material 3, exibindo erro visual caso o usuário tente iniciar a verificação com menos de 3 caracteres úteis e garantindo que o estado persista ao navegar para a `DetailsScreen`.

### Conteúdo Sugerido pela IA
A IA sugeriu criar duas variáveis de estado (`roomName` e `roomError`) com `rememberSaveable`, configurar os parâmetros `isError = roomError` e `supportingText` no `OutlinedTextField`, limpar `roomError = false` dentro do `onValueChange` e validar `roomName.trim().length < 3` no `onClick` do botão **"▶ Iniciar Verificação"**.

### Revisão Humana e Alterações da Equipe
A equipe revisou o código sugerido, ajustou as cores do `OutlinedTextFieldDefaults.colors(...)` para manter o contraste no tema escuro do **Wiflux** (`AccentCyan`, `TextGray`, `LineGray`), definiu a mensagem em português (`"Digite pelo menos 3 letras"`) e testou manualmente no emulador os cenários inválido e válido.

### Validação do Uso de IA
A equipe confirma que:
- [x] O conteúdo sugerido pela IA foi revisado antes de ser utilizado.
- [x] A equipe compreende integralmente a implementação entregue.
- [x] A implementação foi compilada e executada no emulador.
- [x] Os critérios de aceitação foram validados manualmente.
- [x] Nenhuma funcionalidade fora do escopo da Sprint 04 foi adicionada.

---

## 14. Prompt Sugerido para Apoio de IA

```text
We are developing an Android application with Kotlin and Jetpack Compose.
Current Sprint: SPRINT-04 (User Input, Forms & Validation).
Current requirement: FR-01 to FR-04 — Add a controlled OutlinedTextField "Nome do cômodo" in HomeScreen.kt that requires at least 3 non-whitespace characters before starting the Wi-Fi signal verification, displaying inline error feedback ("Digite pelo menos 3 letras") when invalid.
Current code/context: HomeScreen.kt with SignalStatus state and "Iniciar Verificação" Button.

Help us implement or review this requirement.
1. Explain the concept first.
2. Propose the smallest change that satisfies the requirement.
3. Explain every relevant part of the suggested code.
4. Identify possible errors or edge cases (e.g., blank spaces).
5. Tell us how to validate the result in Android Studio.
Do not add functionality outside the current Sprint scope.
```

---

## 15. Entregáveis (Deliverables)

- [x] Aplicativo Android atualizado (`projects/team-03/app/`)
- [x] Especificação `SPEC-004.md` completa (`projects/team-03/docs/specs/SPEC-004.md`)
- [x] Relatório `SPRINT-04.md` atualizado (`projects/team-03/SPRINT-04.md`)
- [x] Evidências de validação (`projects/team-03/evidence/sprint-04/invalid-input.png` e `valid-input.png`)

---

## 16. Status da Especificação (Specification Status)

- [x] Contexto e objetivo estão claros.
- [x] Requisitos funcionais estão completos.
- [x] Restrições técnicas estão documentadas.
- [x] Itens fora do escopo estão documentados.
- [x] Critérios de aceitação são mensuráveis.
- [x] Rastreabilidade de requisitos está completa.
- [x] Implementação satisfaz a especificação.
- [x] Resultados da validação estão documentados.
- [x] Evidências visuais estão incluídas.
- [x] Uso de IA está documentado.
- [x] Todas as integrantes da equipe sabem explicar a funcionalidade implementada.

---

## Verificação Final (Final Check)

> **Qual requisito este código implementa, onde ele está implementado e qual evidência comprova que o requisito foi atendido?**  
> Os requisitos **FR-01 a FR-04** (entrada controlada do nome do cômodo, validação de mínimo de 3 caracteres úteis com `trim()`, feedback visual de erro e desbloqueio da verificação para entradas válidas) estão implementados em `HomeScreen.kt` (linhas 42–43 e 114–158) e são comprovados pelas evidências `evidence/sprint-04/invalid-input.png` e `evidence/sprint-04/valid-input.png`.
