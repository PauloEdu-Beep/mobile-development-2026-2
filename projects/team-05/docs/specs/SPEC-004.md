# SPEC-004: Formulário de Novo Acionamento com Validação

> **Team:** Team 05  
> **Sprint:** Sprint 04 (User Input, Forms & Validation)  
> **Status:** Validated  
> **Related Sprint:** `SPRINT-04.md`  
> **Base:** `SPEC-003.md` (navegação Login e Painel do Docente)

---

## 1. Context

**Problem:**

Hoje o botão "Novo acionamento" do Painel do Docente só mostra um aviso ("O formulário de acionamento chega na Sprint 04"). O professor ainda não consegue dizer o que está acontecendo nem em qual turma e sala.

**User / Actor:**

O **docente** da Escola Cadidé.

**Usage Context:**

Em sala de aula, com pressa. O formulário usa só toques para escolher nível, turma e sala, sem o professor precisar pensar em palavras-chave. O texto da situação é opcional.

---

## 2. Objective

O objetivo desta funcionalidade é criar o formulário "Novo acionamento", com campos controlados por estado e validação com aviso visível, que monta a mensagem do protocolo. Nada é salvo nesta Sprint (salvar é a Sprint 05).

---

## 3. User Scenario

**Given** que o professor está no Painel do Docente,  
**When** ele toca em "Novo acionamento", escolhe o nível, a turma e a sala e toca em "Enviar",  
**Then** o aplicativo mostra um aviso com a mensagem pronta, e o "OK" volta ao Painel.

**Given** que o professor está no formulário com algum campo obrigatório vazio,  
**When** ele toca em "Enviar",  
**Then** cada campo com problema mostra um aviso vermelho e nada é enviado.

---

## 4. Functional Requirements

### FR-01: Abrir e voltar

Ao tocar em "Novo acionamento" no Painel, o aplicativo deve abrir a rota `novo-acionamento` (no lugar do aviso da Sprint 03). O botão "Cancelar" e o botão voltar do Android devem voltar ao Painel sem enviar nada.

### FR-02: Campos controlados

O formulário deve ter 4 campos, cada um guardado em `rememberSaveable`:

| Campo | Tipo | Regra |
| --- | --- | --- |
| Nível | 4 botões de escolha com o nome completo e a cor da faixa (Nível 1 - Apoio Pedagógico, Nível 2 - Atenção Disciplinar, Nível 3 - Prioridade Operacional, Nível 4 - Urgência Crítica) | obrigatório |
| Turma | botões de escolha (6º A, 6º B, 7º A, 7º B, 8º A, 8º B, 9º A, 9º B; lista de exemplo) | obrigatório |
| Sala | botões de escolha (01 a 08; lista de exemplo) | obrigatório |
| Situação | texto livre com contador | opcional, até 100 caracteres |

### FR-03: Validação e erros

Ao tocar em "Enviar", o aplicativo deve conferir todas as regras em uma única função. Avisos em vermelho: "Escolha o nível", "Escolha a turma", "Escolha a sala" e "Máximo de 100 caracteres" (situação com 101 ou mais caracteres). Havendo erro, nada é enviado. Cada aviso some quando o campo é corrigido. O campo de texto deixa digitar além de 100 para o erro poder aparecer.

### FR-04: Envio aceito

Com os campos válidos, o aplicativo deve mostrar um aviso com a mensagem `[NÍVEL] | [TURMA] | Sala [SALA] | [SITUAÇÃO]`. Situação vazia ou só com espaços vira "Solicito apoio.". O "OK" volta ao Painel, que continua mostrando "Nenhum acionamento ainda".

Exemplo: `Nível 2 - Atenção Disciplinar | 8º A | Sala 03 | Solicito apoio com aluno.`

---

## 5. Constraints

### Required Technologies

- Kotlin
- Jetpack Compose (Material 3)
- Android Studio

### Project Constraints

- `minSdk` continua 24.
- Nenhuma biblioteca nova.
- Todo o trabalho fica dentro de `projects/team-05/`.
- A tela nova só desenha e avisa; quem navega é o `MainActivity`.

### Files or Modules That Must Not Be Modified

- Login (`WelcomeScreen.kt`) e tema (`ui/theme/`).

---

## 6. Out of Scope

- Salvar os acionamentos e preencher "Meus acionamentos" (Sprint 05).
- Notificação ou envio real da mensagem (Sprint 06).
- Login real, perfis de Monitor e Gestão.
- Lista real de turmas e salas (as listas são de exemplo até o Paulo confirmar).

---

## 7. Acceptance Criteria

### AC-01: Abrir e voltar

**Related requirement:** FR-01

**Condition:**  
Painel → "Novo acionamento" abre o formulário com os 4 campos e "Enviar". "Cancelar" e o voltar do Android levam ao Painel sem enviar.

### AC-02: Entrada inválida

**Related requirement:** FR-02, FR-03

**Condition:**  
Com tudo vazio, "Enviar" mostra 3 avisos vermelhos (nível, turma, sala) e nada é enviado. Situação com 101 caracteres mostra "Máximo de 100 caracteres"; com exatamente 100 é aceita. Evidência: `invalid-input.png`.

### AC-03: Entrada válida

**Related requirement:** FR-04

**Condition:**  
Com nível, turma e sala escolhidos, "Enviar" mostra o aviso com a mensagem do protocolo, e o "OK" volta ao Painel com a lista vazia. Evidência: `valid-input.png`.

### AC-04: Não-regressão

**Related requirement:** todos

**Condition:**  
Login, os 2 modais da Sprint 02, Entrar e Sair e o voltar do Android no Painel continuam funcionando.

### AC-05: Compila e é estável

**Related requirement:** todos

**Condition:**  
`./gradlew assembleDebug` termina sem erro, e 10 envios seguidos não geram `FATAL EXCEPTION` no logcat.

---

## 8. Requirement Traceability

| Requirement | Implemented In | Acceptance Criterion | Evidence |
| --- | --- | --- | --- |
| FR-01 | `MainActivity.kt` L68 (`navigate("novo-acionamento")`), L73-75 (`composable("novo-acionamento")` + `popBackStack()`); `HomeDocenteScreen.kt` L53, L72-77 (botão "Novo acionamento"); `NovoAcionamentoScreen.kt` L94 (`imePadding()`) e L139 (botão "Cancelar") | AC-01 | `evidence/sprint-04/valid-input.png` |
| FR-02 | `NovoAcionamentoScreen.kt` L51-58 (listas `niveis`, `turmas`, `salas`); L81-84 (4 estados `rememberSaveable { mutableStateOf("") }`); L108-116 (`FilterChip` dos níveis), L121 e L125 (`Escolhas` de turma e sala), L129-136 (`OutlinedTextField` da situação) | AC-02, AC-03 | `evidence/sprint-04/invalid-input.png`, `evidence/sprint-04/valid-input.png` |
| FR-03 | `NovoAcionamentoScreen.kt` L61-63 (`class Erros`), L66-71 (função única `validar`), L85 (`tentouEnviar`), L88 (`val erros = validar(...)`), L117/L122/L126/L175 (`Aviso`) e L133-134 (`isError` + `supportingText` com contador `${situacao.length}/100`) | AC-02 | `evidence/sprint-04/invalid-input.png` |
| FR-04 | `NovoAcionamentoScreen.kt` L86 (`aceito`), L141-144 (botão "Enviar" só aceita quando `!erros.temErro`) e L151-159 (`AlertDialog` com `"$nivel \| $turma \| Sala $sala \| $texto"` e botão "OK") | AC-03 | `evidence/sprint-04/valid-input.png` |

Cobertura dos 10 critérios do professor: AC-01 (SPEC existe) e AC-02 (fluxo com sentido) por este arquivo e FR-01; AC-03 (estado) por FR-02; AC-04 e AC-05 (regra e erro visível) por FR-03; AC-06 (válido aceito) por FR-04; AC-07 (evidências) pelos 2 prints; AC-08 (anterior funciona) pelo nosso AC-04; AC-09 (compila e roda) pelo nosso AC-05; AC-10 (explicar a validação) pela função única de validação.

---

## 9. Implementation Plan

### Components to Create or Modify

- `ui/screens/NovoAcionamentoScreen.kt` (novo): os 4 estados, a função de validação, uma função de botões de escolha e o aviso de sucesso.
- `MainActivity.kt`: nova rota `novo-acionamento`.
- `ui/screens/HomeDocenteScreen.kt`: o botão passa a navegar e o aviso da Sprint 03 sai.

### Expected Interaction Flow

```text
Painel
  ↓ toca em "Novo acionamento"
Formulário (campos vazios)
  ↓ toca em "Enviar"
Validação
  ↓ erro: avisos vermelhos        ↓ ok: aviso com a mensagem
  (fica no formulário)            ↓ toca em "OK"
                                  Painel
```

### Data or State Required

- `nivel`, `turma`, `sala` e `situacao` em `rememberSaveable { mutableStateOf("") }`.
- `tentouEnviar` (os avisos só aparecem depois do primeiro "Enviar").

---

## 10. Validation Plan

A equipe deve:

1. Compilar o projeto.
2. Rodar no emulador `Pixel_8_API_37`.
3. Executar os cenários inválido (tudo vazio e 101 caracteres) e válido (e o limite de 100).
4. Testar o voltar do Android no formulário e no Painel.
5. Rodar 10 envios seguidos e conferir o logcat.
6. Salvar `invalid-input.png` e `valid-input.png`, com o primeiro print feito logo depois de abrir o app.

---

## 11. Validation Results

| Acceptance Criterion | Result | Notes |
| --- | --- | --- |
| AC-01 | PASS | No Painel, tocar em "Novo acionamento" abre a rota `novo-acionamento` com os 4 campos e botões; "Cancelar" e o voltar do Android retornam ao Painel sem enviar. |
| AC-02 | PASS | Com nível, turma e sala vazios e situação com 101 caracteres, tocar em "Enviar" mostra os 4 avisos em vermelho (`Escolha o nível`, `Escolha a turma`, `Escolha a sala` e `101/100 - Máximo de 100 caracteres`) e bloqueia o envio (`invalid-input.png`). Com exatamente 100 caracteres, o campo é aceito sem erro. |
| AC-03 | PASS | Com Nível 2, turma 8º A, sala 03 e situação preenchida (25/100), tocar em "Enviar" exibe o modal "Acionamento pronto" com a mensagem `"Nível 2 - Atenção Disciplinar \| 8º A \| Sala 03 \| Solicito apoio com aluno."` (`valid-input.png`), e "OK" volta ao Painel mantendo "Nenhum acionamento ainda". |
| AC-04 | PASS | Login (`WelcomeScreen`), os 2 modais da Sprint 02 ("Sobre o SARC" e "Solicitação de Cadastro"), Entrar → Sair e o voltar do Android no Painel continuam funcionando normalmente. |
| AC-05 | PASS | `./gradlew assembleDebug` concluído com `BUILD SUCCESSFUL`; 10 envios seguidos testados no emulador sem fechar sozinho e com 0 `FATAL EXCEPTION` no logcat. |

### Environment Used for Validation

- **Device:** Android Studio Emulator, Pixel 8 (`Pixel_8_API_37`)
- **Android version / API:** Android API 37
- **Build result:** PASS (`./gradlew assembleDebug`: `BUILD SUCCESSFUL`)
- **Application execution:** PASS (APK instalado e validado no emulador Pixel 8, API 37)

---

## 12. Evidence

- `evidence/sprint-04/invalid-input.png`
- `evidence/sprint-04/valid-input.png`

---

## 13. AI-Assisted Development

### AI Tool(s)

- **Tool:** Claude Code (Anthropic) e Google Antigravity (Gemini)

### How AI Was Used

O trabalho seguiu um plano prévio em módulos construído e discutido antes de qualquer código. O Matheus e o Paulo definiram o funcionamento do formulário e iam orientando e aprovando cada etapa (especificação, código e testes), usando a IA para rascunhar a `SPEC-004`, implementar o formulário em Compose conforme o plano aprovado e apoiar na captura das evidências.

### Human Review and Changes

- **Decisão do Paulo:** substituir as 9 palavras-chave do protocolo pelos 4 níveis de urgência com nome completo e cor da faixa, para o professor em sala não precisar decorar palavras-chave.
- **Decisões e revisão do Matheus:** manter a situação opcional com limite máximo de 100 caracteres (deixando digitar além de 100 para o erro ficar visível), usar listas de exemplo para turma e sala, e concentrar todas as regras em uma única função `validar(...)` para facilitar a explicação.
- **Correção identificada pelo Matheus nos testes do emulador:** ao testar a digitação pelo teclado virtual do celular no campo "Situação", o Matheus identificou que o teclado cobria o campo de texto em vez de empurrar a tela para cima, e que o primeiro print de entrada inválida cortava os botões inferiores. A equipe corrigiu a tela adicionando `Modifier.imePadding()` na `NovoAcionamentoScreen.kt`, ajustou a configuração de teclado do emulador e gerou novamente a evidência `invalid-input.png`.

### AI Validation

- [x] O conteúdo gerado pela IA foi revisado antes de ser usado.
- [x] A equipe entende a implementação enviada.
- [x] A implementação foi compilada e executada.
- [x] Os critérios de aceite foram validados manualmente.
- [x] Nada fora do escopo da Sprint foi adicionado só porque a IA sugeriu.

---

## 14. Deliverables

- [x] Aplicativo Android atualizado
- [x] Esta SPEC completa
- [x] `SPRINT-04.md`
- [x] Evidências em `evidence/sprint-04/`

---

## 15. Specification Status

- [x] Contexto e objetivo claros.
- [x] Requisitos funcionais completos.
- [x] Restrições documentadas.
- [x] Itens fora de escopo documentados.
- [x] Critérios de aceite mensuráveis.
- [x] Rastreabilidade completa.
- [x] Implementação atende a SPEC.
- [x] Resultados de validação documentados.
- [x] Evidências incluídas.
- [x] Uso de IA documentado por completo.
- [x] Todos os membros sabem explicar a funcionalidade.
