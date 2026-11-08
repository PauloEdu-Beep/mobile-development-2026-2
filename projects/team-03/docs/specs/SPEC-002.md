# SPEC-002 — Verificação de Sinal (Estado e Interação)

> **Equipe:** Team 03  
> **Integrantes:** Célia Hiromi (@hiromilly) e Geovanna Gaspar (@gegwspar)  
> **Sprint:** SPRINT 02 — State & User Interaction  
> **Status:** Validado  
> **Documento da Sprint:** [`SPRINT-02.md`](../../SPRINT-02.md)

---

## 1. Contexto

**Problema:**  
Na Sprint 01, o aplicativo **Wi-Fi Mapper** apresentou sua proposta de valor e layout inicial estático. Entretanto, para diagnosticar a qualidade da conexão em diferentes cômodos, o usuário precisa de um sistema reativo que responda ao seu comando e apresente a intensidade do sinal de forma compreensível e imediata.

**Usuário / Ator:**  
Usuários domésticos, estudantes e trabalhadores remotos que desejam testar a intensidade do sinal Wi-Fi no cômodo onde se encontram.

**Contexto de Uso:**  
O usuário abre o aplicativo, visualiza a tela de diagnóstico em estado inicial de espera e toca no botão principal para realizar a verificação do sinal no ambiente.

---

## 2. Objetivo

Implementar interação com gerenciamento de estado em **Jetpack Compose** (`remember` e `mutableStateOf`), onde o toque no botão "Iniciar Verificação" simula uma medição de sinal Wi-Fi, atualizando reativamente o valor numérico em dBm, a cor e abertura do setor no Radar em Canvas e o chip de status qualitativo.

---

## 3. Cenário do Usuário (User Scenario)

**Dado** que o usuário está com o aplicativo aberto na tela de verificação no estado inicial ("Aguardando" e "— DBM"),  
**Quando** tocar no botão "Iniciar Verificação",  
**Então** a interface deve atualizar imediatamente o estado, exibindo a nova intensidade em dBm no centro do radar, desenhando o setor circular colorido correspondente e atualizando o chip informativo para "Sinal Forte", "Sinal Médio" ou "Sinal Fraco".

---

## 4. Requisitos Funcionais

### FR-01 — Gerenciamento de Estado Reativo
A tela deve manter uma variável de estado gerenciada pelo Compose (`signalStatus`) baseada no enum `SignalStatus`, contendo rótulo, cor temática e valor numérico em dBm.

### FR-02 — Estado Inicial de Espera
Antes de qualquer interação, o estado inicial deve ser obrigatoriamente `SignalStatus.NONE`, exibindo o chip com o texto "Aguardando" e o indicador central de dBm com o traço `"—"`.

### FR-03 — Disparo de Evento de Medição
Ao acionar o botão de ação primária "Iniciar Verificação" (`onClick`), o aplicativo deve sortear um dos novos estados ativos (`STRONG`, `MEDIUM` ou `WEAK`) e atualizar a variável de estado.

### FR-04 — Reatividade Visual da Interface
A interface deve recompor dinamicamente reagindo à mudança de estado:
- O componente `RadarView` desenhado em `Canvas` deve exibir um setor circular translúcido na cor do status;
- O valor numérico em dBm e o ícone central devem refletir a nova medição;
- O chip de status inferior deve exibir o texto e a cor correspondentes ao nível de sinal obtido.

---

## 5. Restrições Técnicas

- **Tecnologias Obrigatórias:** Kotlin, Jetpack Compose, Material Design 3.
- **Escopo da Sprint:** Nesta Sprint 02, o valor do sinal é simulado via sorteio aleatório entre os estados válidos; leitura real via `WifiManager` do Android é restrita para sprints futuras (Sprint 06).
- **Sem Navegação ou Persistência:** Não incluir navegação entre múltiplas telas nem banco de dados nesta etapa.

---

## 6. Fora do Escopo (Out of Scope)

- Leitura de hardware nativo via `WifiManager` / permissões de localização (Sprint 06).
- Mapeamento e cadastro de cômodos (Sprint 04).
- Histórico persistido em banco de dados Room (Sprint 05).
- Navegação entre múltiplas telas (Sprint 03).

---

## 7. Critérios de Aceitação

### AC-01 — Variável de Estado Declarativa
**Requisito relacionado:** FR-01  
**Condição:** O código deve conter pelo menos uma variável de estado gerenciada com `remember { mutableStateOf(...) }`.

### AC-02 — Evento de Clique Atualiza Estado
**Requisito relacionado:** FR-03  
**Condição:** O clique no botão "Iniciar Verificação" deve invocar o tratador de evento e atribuir um novo valor ao estado.

### AC-03 — UI Reage Visivelmente
**Requisito relacionado:** FR-04  
**Condição:** O radar em Canvas, o texto em dBm e o chip de status devem atualizar sua renderização imediatamente após o clique.

### AC-04 — Estado Inicial Adequado
**Requisito relacionado:** FR-02  
**Condição:** Ao iniciar o app, o radar não deve conter setor colorido e o chip deve indicar "Aguardando".

### AC-05 — Preservação de Funcionalidades Anteriores
**Requisitos relacionados:** FR-01, FR-02, FR-03, FR-04  
**Condição:** A identidade visual, nome do app, slogan e botão da Sprint 01 devem permanecer presentes e funcionais.

### AC-06 — Estabilidade na Execução
**Requisitos relacionados:** FR-01, FR-03  
**Condição:** Cliques sucessivos e repetidos no botão devem alternar os estados sem causar congelamentos ou falhas de execução (*no crash*).

---

## 8. Rastreabilidade dos Requisitos

| Requisito | Implementado Em | Critério de Aceitação | Evidência |
| :--- | :--- | :--- | :--- |
| **FR-01** | `MainActivity.kt` (`var signalStatus`) | AC-01 | `MainActivity.kt` |
| **FR-02** | `MainActivity.kt` (`SignalStatus.NONE`) | AC-04 | `evidence/sprint-02/before-interaction.png` |
| **FR-03** | `MainActivity.kt` (`Button(onClick = ...)`) | AC-02 | `MainActivity.kt` |
| **FR-04** | `MainActivity.kt` (`RadarView`, `StatusChip`) | AC-03 | `evidence/sprint-02/after-interaction.png` |

---

## 9. Plano de Implementação

### Componentes Criados/Modificados:
- `SignalStatus` (enum): Encapsula `label`, `dbm`, `sectorColor` e `textColor`.
- `RadarView` (Composable): Desenha a grade com círculos concêntricos e linhas de mira via `Canvas`, renderizando o setor translúcido quando ativo.
- `WifiIcon` (Composable): Desenho vetorial leve dos arcos de Wi-Fi em `Canvas`.
- `WifiMapperHomeScreen` (Composable): Integração do header, radar, chip e botão reativo.

### Fluxo de Interação:
```text
[Estado Inicial: NONE] ("Aguardando", "— DBM", radar sem setor)
               ↓
[Ação do Usuário] (Toque em "Iniciar Verificação")
               ↓
[Evento onClick] (signalStatus = options.random())
               ↓
[Recomposição Compose] (Radar iluminado, novo dBm, chip colorido)
```

---

## 10. Resultados da Validação

| Critério | Resultado | Observações |
| :--- | :--- | :--- |
| **AC-01** | **PASS** | `remember { mutableStateOf(SignalStatus.NONE) }` implementado. |
| **AC-02** | **PASS** | Tratador de clique sorteia entre STRONG, MEDIUM e WEAK. |
| **AC-03** | **PASS** | Radar, dBm e chip alteram cores e textos de forma síncrona. |
| **AC-04** | **PASS** | Inicialização limpa com status "Aguardando" comprovada na evidência. |
| **AC-05** | **PASS** | Nome, slogan e estrutura da tela preservados e aprimorados. |
| **AC-06** | **PASS** | Cliques repetidos alternam os estados de forma fluida e estável. |

### Ambiente de Validação:
- **Dispositivo:** Emulador Android (Google Pixel 6 / Pixel 8)
- **Versão do Android:** API 34
- **Resultado da Execução:** PASS (sem travamentos ou vazamentos de memória)

---

## 11. Evidências de Validação

- **Antes da Interação:** `projects/team-03/evidence/sprint-02/before-interaction.png`
- **Depois da Interação:** `projects/team-03/evidence/sprint-02/after-interaction.png`

---

## 12. Desenvolvimento com Apoio de IA

- **Ferramentas Utilizadas:** Claude e Gemini (Antigravity AI).
- **Finalidade:** Modelagem da máquina de estados com enum class, cálculo trigonométrico dos arcos do radar em `Canvas` e estruturação da tabela de rastreabilidade.
- **Revisão Humana:** Calibração das dimensões em `dp`/`sp`, ajuste fino do contraste no tema escuro, testes visuais e conferência da conformidade com as regras da Sprint 02.

---

## 13. Status da Especificação

- [x] Contexto e objetivo alinhados ao escopo da Sprint 02.
- [x] Requisitos funcionais FR-01 a FR-04 completos e testáveis.
- [x] Critérios de aceitação AC-01 a AC-06 mensuráveis.
- [x] Matriz de rastreabilidade preenchida.
- [x] Evidências antes/depois organizadas na pasta padrão.
- [x] Integrantes aptas a explicar a implementação e o ciclo de recomposição.
