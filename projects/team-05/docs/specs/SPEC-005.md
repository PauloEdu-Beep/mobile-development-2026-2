# SPEC-005: Histórico de Acionamentos Salvo no Celular

> **Team:** Team 05  
> **Sprint:** Sprint 05 (Local Persistence & Data)  
> **Status:** Validated  
> **Related Sprint:** `SPRINT-05.md`  
> **Base:** `SPEC-004.md` (formulário de Novo Acionamento com validação)

---

## 1. Context

**Problem:**

Hoje, quando o professor envia um acionamento, o aplicativo mostra a mensagem pronta e ela some. Se o app for fechado, não fica registro nenhum, e o Painel do Docente continua mostrando "Nenhum acionamento ainda".

**User / Actor:**

O **docente** da Escola Cadidé.

**Usage Context:**

Em sala de aula, o professor só precisa conferir se o acionamento foi enviado, mesmo que tenha fechado o app ou desligado o aparelho. Quando a situação se resolve, ele marca o acionamento como resolvido.

---

## 2. Objective

O objetivo desta funcionalidade é salvar cada acionamento enviado, com data e hora, em um banco de dados local (Room), mostrar esses registros na seção "Meus acionamentos" do Painel do Docente e permitir marcar cada um como resolvido, mantendo tudo depois de fechar e reabrir o aplicativo.

---

## 3. User Scenario

**Given** que o professor enviou um acionamento válido no formulário,  
**When** ele toca em "OK" e volta ao Painel,  
**Then** a mensagem aparece na lista "Meus acionamentos", com a data e a hora do envio, a mais nova no topo.

**Given** que existe um acionamento na lista,  
**When** o professor toca em "Resolvido" ao lado dele,  
**Then** o acionamento passa a mostrar "✓ Resolvido" e o botão some.

**Given** que existem acionamentos salvos,  
**When** o aplicativo é fechado por completo (ou o aparelho é desligado) e aberto de novo,  
**Then** depois do login, a lista do Painel continua mostrando os mesmos acionamentos, com a mesma data, hora e situação (resolvido ou não).

---

## 4. Functional Requirements

### FR-01: Modelo de dados

O aplicativo deve ter uma tabela Room `acionamentos` com 4 colunas:

| Coluna | Tipo | Regra |
| --- | --- | --- |
| `id` | Int | chave primária, gerada automaticamente |
| `mensagem` | String | a mensagem pronta do protocolo (Sprint 04) |
| `dataHora` | Long | o momento do envio, em milissegundos (`System.currentTimeMillis()`) |
| `resolvido` | Boolean | começa `false`; vira `true` quando o professor marca |

### FR-02: Salvar ao enviar

Ao tocar em "Enviar" com o formulário válido, o aplicativo deve gravar a mensagem `[NÍVEL] | [TURMA] | Sala [SALA] | [SITUAÇÃO]`, a data e hora atuais e `resolvido = false`. Formulário com erro não grava nada.

### FR-03: Ler e mostrar no Painel

O Painel do Docente deve listar os acionamentos salvos, do mais novo para o mais antigo. Cada item mostra a data e hora no formato `dd/MM/yyyy HH:mm` e a mensagem. Sem registros, continua mostrando "Nenhum acionamento ainda". A lista se atualiza sozinha quando um registro é salvo ou alterado.

### FR-04: Sobreviver ao reinício

Os registros devem continuar visíveis depois de fechar o aplicativo por completo (tirar da lista de apps recentes) e abrir de novo, e também depois de reiniciar o aparelho (no emulador, desligar e ligar de novo).

### FR-05: Marcar como resolvido

Cada acionamento ainda não resolvido mostra um botão "Resolvido". Ao tocar nele, o aplicativo grava `resolvido = true` no banco, e o item passa a mostrar "✓ Resolvido" no lugar do botão. A marcação também sobrevive ao reinício.

---

## 5. Constraints

### Required Technologies

- Kotlin
- Jetpack Compose (Material 3)
- Room (com o plugin KSP)
- Android Studio

### Project Constraints

- `minSdk` continua 24 (a data é formatada com `SimpleDateFormat`, que funciona no API 24).
- Bibliotecas novas: só o Room e o KSP.
- Nada sai do celular: sem internet e sem servidor.
- O banco não roda na linha principal da tela (sem `allowMainThreadQueries`).
- Todo o trabalho fica dentro de `projects/team-05/`.

### Files or Modules That Must Not Be Modified

- Login (`WelcomeScreen.kt`) e tema (`ui/theme/`).
- As regras de validação do formulário (`validar(...)` da Sprint 04).

---

## 6. Out of Scope

- Apagar ou editar o texto de um acionamento.
- Esconder os resolvidos e os mais antigos da lista principal e levá-los para um botão "Acionamentos feitos", deixando no Painel só os não atendidos (melhoria planejada para depois desta Sprint).
- Filtros, busca e tela de detalhes.
- Notificação ou envio para a Coordenação (Sprint 06).
- Login real e separação dos registros por professor.

---

## 7. Acceptance Criteria

### AC-01: Salvar e mostrar

**Related requirement:** FR-01, FR-02, FR-03

**Condition:**  
Enviar um acionamento válido e tocar em "OK" mostra, no topo de "Meus acionamentos", a data e hora do envio e a mensagem, com o botão "Resolvido". Evidência: `data-created.png`.

### AC-02: Marcar como resolvido

**Related requirement:** FR-05

**Condition:**  
Tocar em "Resolvido" troca o botão por "✓ Resolvido" só naquele item; os outros não mudam.

### AC-03: Sobrevive ao reinício

**Related requirement:** FR-04, FR-05

**Condition:**  
Com 2 ou mais acionamentos salvos (pelo menos 1 resolvido), fechar o app pela lista de recentes, abrir de novo e entrar: a lista continua igual, com as mesmas datas, horas e marcações. O mesmo vale depois de reiniciar o emulador. Evidência: `data-restored.png`.

### AC-04: Lista vazia e erro não grava

**Related requirement:** FR-02, FR-03

**Condition:**  
Sem registros, o Painel mostra "Nenhum acionamento ainda". Um formulário com erro não cria registro.

### AC-05: Não-regressão

**Related requirement:** todos

**Condition:**  
O login, os 2 modais da Sprint 02, Entrar e Sair, o voltar do Android e a validação do formulário da Sprint 04 continuam funcionando.

### AC-06: Compila e é estável

**Related requirement:** todos

**Condition:**  
`./gradlew assembleDebug` termina sem erro, e 10 envios seguidos não geram `FATAL EXCEPTION` no logcat.

---

## 8. Requirement Traceability

| Requirement | Implemented In | Acceptance Criterion | Evidence |
| --- | --- | --- | --- |
| FR-01 | `AcionamentoDatabase.kt` L15-21 (`AcionamentoEntity` com `id`, `mensagem`, `dataHora`, `resolvido`) e L40 (`@Database`) | AC-01 | `evidence/sprint-05/data-created.png` |
| FR-02 | `AcionamentoDatabase.kt` L27-28 (`salvar`); `NovoAcionamentoScreen.kt` L90 (`val mensagem`) e L145 (`onEnviar(mensagem)` só se `!erros.temErro`); `MainActivity.kt` L92-95 (`dao.salvar(...)` com `System.currentTimeMillis()`) | AC-01, AC-04 | `evidence/sprint-05/data-created.png` |
| FR-03 | `AcionamentoDatabase.kt` L31-32 (`listarTodos`, `Flow`); `MainActivity.kt` L75 (`collectAsState`); `HomeDocenteScreen.kt` L99 (aviso de lista vazia), L101 (`LazyColumn`), L141 (`formatoData`) e L145-153 (`AcionamentoItem`) | AC-01, AC-04 | `evidence/sprint-05/data-created.png` |
| FR-04 | `AcionamentoDatabase.kt` L49 (`databaseBuilder(..., "sarc.db")`: o Room grava em arquivo no aparelho) | AC-03 | `evidence/sprint-05/data-restored.png` |
| FR-05 | `AcionamentoDatabase.kt` L35-36 (`marcarResolvido`); `MainActivity.kt` L83; `HomeDocenteScreen.kt` L152-153 (botão "Resolvido" e "✓ Resolvido") | AC-02, AC-03 | `evidence/sprint-05/data-restored.png` |

Cobertura dos 10 critérios do professor: AC-01 (SPEC existe) por este arquivo; AC-02 (mecanismo adequado) pelo Room, porque o histórico é uma lista de registros e não uma preferência simples; AC-03 (dados modelados) por FR-01; AC-04 (salvar e alterar) por FR-02 e FR-05; AC-05 (ler e mostrar) por FR-03; AC-06 (sobrevive ao reinício) por FR-04; AC-07 (o anterior funciona) pelo nosso AC-05; AC-08 (compila) pelo nosso AC-06; AC-09 (evidências) pelos 2 prints; AC-10 (explicar o fluxo de dados) pela seção 9.

---

## 9. Implementation Plan

### Components to Create or Modify

- `data/AcionamentoDatabase.kt` (novo): a tabela (`AcionamentoEntity`), os comandos (`AcionamentoDao`: `salvar`, `listarTodos` e `marcarResolvido`) e o banco (`AcionamentoDatabase`).
- `MainActivity.kt`: abre o banco uma vez e o entrega às telas.
- `NovoAcionamentoScreen.kt`: o "Enviar" válido também chama `salvar`.
- `HomeDocenteScreen.kt`: troca o texto fixo pela lista vinda do banco, com data e hora e o botão "Resolvido".
- `gradle/libs.versions.toml`, `build.gradle.kts` e `app/build.gradle.kts`: o Room e o KSP.

### Expected Interaction Flow

```text
Formulário
  ↓ "Enviar" (válido)
salvar(mensagem, dataHora) → tabela acionamentos (arquivo sarc.db no aparelho)
  ↓ "OK"
Painel ← listarTodos() lê a tabela e mostra a lista
  ↓ toca em "Resolvido"
marcarResolvido(id) → a linha muda para resolvido = true → a lista se atualiza
  ↓ fecha e reabre o app (ou reinicia o aparelho)
Painel ← listarTodos() lê o mesmo arquivo → a lista continua igual
```

### Data or State Required

- `AcionamentoEntity(id, mensagem, dataHora, resolvido)`.
- `listarTodos()` devolve um `Flow<List<AcionamentoEntity>>`, e o Painel o lê com `collectAsState`.
- `salvar` e `marcarResolvido` são funções `suspend`, chamadas dentro de uma coroutine.

---

## 10. Validation Plan

A equipe deve:

1. Compilar o projeto.
2. Rodar no emulador `Pixel_8_API_37`, começando com o app sem dados.
3. Conferir "Nenhum acionamento ainda" e tentar enviar um formulário com erro (nada deve ser gravado).
4. Enviar 2 acionamentos válidos, conferir a lista com data e hora e marcar 1 como resolvido (`data-created.png`).
5. Fechar o app pela lista de recentes, abrir de novo, entrar e conferir a lista (`data-restored.png`).
6. Reiniciar o emulador e conferir a lista de novo.
7. Repetir os testes da Sprint 04 e rodar 10 envios seguidos conferindo o logcat.

---

## 11. Validation Results

| Acceptance Criterion | Result | Notes |
| --- | --- | --- |
| AC-01 | PASS | Com o app limpo, enviar "Nível 3 - Prioridade Operacional \| 7º B \| Sala 05 \| Aluno com celular" (texto digitado) e "Nível 1 - Apoio Pedagógico \| 9º A \| Sala 02 \| Solicito apoio." e tocar em "OK" mostra no topo de "Meus acionamentos" cada cartão com `09/11/2026 15:13`, a mensagem e o botão "Resolvido", o mais novo em cima (`data-created.png`, tirado às 3:14 do relógio do aparelho). |
| AC-02 | PASS | Tocar em "Resolvido" no cartão mais antigo trocou só o botão dele por "✓ Resolvido" (verde); o cartão mais novo continuou com o botão. |
| AC-03 | PASS | Com 2 acionamentos (1 resolvido), o app foi fechado por completo (`am force-stop`, processo inexistente) e reaberto às 3:17: a lista voltou igual, com os mesmos cartões de `15:13` e a mesma marcação (`data-restored.png`). Depois o emulador foi reiniciado (aparelho ligado há ~22 s) às 3:20 e a lista continuou igual (`data-restored-reboot.png`). Os 3 prints têm horários diferentes no relógio do aparelho, enquanto os cartões continuam marcando 15:13. |
| AC-04 | PASS | Sem registros, o Painel mostrou "Nenhum acionamento ainda". Com o formulário vazio, "Enviar" mostrou "Escolha o nível", "Escolha a turma" e "Escolha a sala"; ao voltar, o Painel continuou vazio (nada foi gravado). |
| AC-05 | PASS | "Sair" volta ao Login; os modais "Sobre o SARC" e "Solicitação de Cadastro" abrem; o voltar do Android fecha os modais e sai do Painel; a validação da Sprint 04 continua mostrando os avisos. |
| AC-06 | PASS | `./gradlew assembleDebug` terminou com `BUILD SUCCESSFUL`; 10 envios seguidos no emulador e todos os testes acima geraram 0 `FATAL EXCEPTION` no logcat. |

### Environment Used for Validation

- **Device:** Android Studio Emulator, Pixel 8 (`Pixel_8_API_37`)
- **Android version / API:** Android API 37
- **Build result:** PASS (`./gradlew assembleDebug`: `BUILD SUCCESSFUL`)
- **Application execution:** PASS (APK instalado e validado no emulador Pixel 8, API 37)

### Implementation Notes and Pending Items

**O que foi feito nesta Sprint (e testado no emulador):**

- Branch `team-05/sprint-05`, criada a partir da `team-05/sprint-04`, porque o formulário da Sprint 04 (PR #33) ainda não foi aceito pelo professor.
- Room 2.8.5 e KSP 2.3.13 adicionados ao Gradle (`libs.versions.toml`, `build.gradle.kts` e `app/build.gradle.kts`).
- Arquivo novo `data/AcionamentoDatabase.kt` com a tabela, os 3 comandos (`salvar`, `listarTodos`, `marcarResolvido`) e o banco `sarc.db`.
- `MainActivity.kt` abre o banco e liga as telas a ele; `NovoAcionamentoScreen.kt` salva no "Enviar" válido; `HomeDocenteScreen.kt` mostra a lista com data, hora e o botão "Resolvido".
- 3 prints de evidência com horários diferentes: depois de criar, depois de fechar o app e depois de reiniciar o aparelho.

**O que ficou de fora de propósito (Out of Scope):**

- Botão "Acionamentos feitos": os resolvidos e os mais antigos saem da lista principal e ficam num botão separado, deixando no Painel só os não atendidos. Planejado para depois desta Sprint.
- Apagar ou editar acionamentos, filtros, busca e tela de detalhes.

**Observações e pendências:**

- Nos cartões da lista, textos como "8º A" podem quebrar em duas linhas ("8º" / "A"). É só visual e não afeta nenhum critério.
- O Painel mostra a legenda dos níveis acima da lista, o que deixa pouco espaço para os cartões; a lista rola normalmente.
- A confirmação de que a equipe sabe explicar o fluxo de dados (AC-10 do professor e o último item da seção 15) é feita pelo Matheus e pelo Paulo depois de estudar `SPRINT-05.md`.
- Falta abrir o Pull Request `[Sprint 05] Team 05 — Local Persistence & Data` (só depois do OK do Matheus). Como o PR #33 da Sprint 04 ainda está aberto, o PR da Sprint 05 traz também os commits da Sprint 04 até o professor aceitá-lo.

---

## 12. Evidence

- `evidence/sprint-05/data-created.png`
- `evidence/sprint-05/data-restored.png` (depois de fechar o app por completo)
- `evidence/sprint-05/data-restored-reboot.png` (depois de reiniciar o aparelho)

---

## 13. AI-Assisted Development

### AI Tool(s)

- **Tool:** Claude Code (Anthropic)

### How AI Was Used

O trabalho seguiu um plano em módulos, aprovado pelo Matheus antes de cada etapa: primeiro um rascunho da `SPEC-005`, depois o rascunho do código e só então a implementação. A IA (Claude Code) escreveu a SPEC e o código do Room conforme o plano aprovado, compilou o app e executou os testes no emulador pelo `adb`, capturando as evidências.

### Human Review and Changes

- **Decisões do Matheus no rascunho da SPEC:** mostrar a data e a hora de cada acionamento; incluir o botão "Resolvido" para o professor marcar o que já foi atendido; deixar para depois desta Sprint a separação entre a lista principal (só os não atendidos) e um botão "Acionamentos feitos"; deixar claro que o histórico serve para o professor conferir se o acionamento foi enviado, mesmo com o aparelho desligado; exigir que a prova de persistência fosse feita fechando o app e reiniciando o aparelho, com prints de horários diferentes (os primeiros prints saíram idênticos e foram refeitos).

### AI Validation

- [x] O conteúdo gerado pela IA foi revisado antes de ser usado.
- [ ] A equipe entende a implementação enviada. *(o Matheus confirma depois de estudar o fluxo de dados)*
- [x] A implementação foi compilada e executada.
- [x] Os critérios de aceite foram validados manualmente.
- [x] Nada fora do escopo da Sprint foi adicionado só porque a IA sugeriu.

---

## 14. Deliverables

- [x] Aplicativo Android atualizado
- [x] Esta SPEC completa
- [ ] `SPRINT-05.md`
- [x] Evidências em `evidence/sprint-05/`

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
- [ ] Uso de IA documentado por completo.
- [ ] Todos os membros sabem explicar a funcionalidade.
