# SPRINT 02 — State & User Interaction

## Team Identification

- **Team:** Team 05
- **Project:** SARC (Sistema de Acionamento e Resposta Cívico-Militar)
- **Institution:** Escola Estadual Cívico-Militar Maria de Lima Cadidé
- **Members:**
  - Matheus Gabriel de Morais Barros ([@MatheusGabriel-25](https://github.com/MatheusGabriel-25))
  - Paulo Vitor ([@PAULOSANTOS1309](https://github.com/PAULOSANTOS1309))

---

## 1. Interaction Selection

### Selected Feature

Modais Institucionais Interativos: cartões de prioridade expansíveis no modal "Sobre o SARC e Prioridades" e guia de cadastro em 3 etapas no modal "Solicitação de Cadastro".

### Problem Solved

Na Sprint 01, os dois modais da tela de login mostravam todo o conteúdo de uma vez, em blocos de texto estáticos. No modal "Sobre", as descrições dos 4 níveis de prioridade apareciam juntas e os nomes dos níveis tinham pouco contraste sobre o fundo cinza-lilás padrão. No modal de cadastro, o professor recebia um único texto, sem saber quem pode solicitar acesso, como funciona a aprovação pela Coordenação Militar e o que fazer depois. Com estado, o professor abre só o nível que quer consultar e percorre o cadastro em etapas curtas, com indicação de progresso.

### Meaningful Value for the Product

É o primeiro uso de estado no SARC: a tela passa a reagir às escolhas do professor. Os níveis de prioridade, que serão usados para acionar a Coordenação Militar nas próximas sprints, ficam mais fáceis de consultar e de ler, e o fluxo de credenciamento fica claro antes mesmo de existir o formulário de cadastro. Tudo acontece dentro da mesma tela, sem antecipar a navegação da Sprint 03 nem os formulários da Sprint 04.

---

## 2. State & Compose Architecture

### State Definition

| | Modal "Sobre o SARC e Prioridades" | Modal "Solicitação de Cadastro" |
| --- | --- | --- |
| **State variable** | `expandedLevel` | `registerStep` |
| **State type** | `Int?` (1 a 4 = nível aberto; `null` = todos recolhidos) | `Int` (etapa atual: 1, 2 ou 3) |
| **Initial value** | `null` | `1` |
| **Compose mechanism** | `remember { mutableStateOf<Int?>(null) }` | `remember { mutableStateOf(1) }` |

Os dois estados são criados dentro do bloco de cada modal (`if (showAboutDialog)` e `if (showRegisterDialog)`). Assim, ao fechar o modal o valor é descartado e, ao reabrir, tudo recomeça do estado inicial.

### Event -> State -> UI Flow

```text
MODAL "SOBRE O SARC E PRIORIDADES"
[Estado inicial: expandedLevel = null → 4 cartões recolhidos, seta ▼]
      ↓
[Ação do usuário: toca no cartão "Nível 4 - Urgência Crítica"]
      ↓
[Evento onClick → alternarNivel(4): expandedLevel = 4 (ou null, se o 4 já estava aberto)]
      ↓
[Recomposição: o Nível 4 mostra a descrição (AnimatedVisibility), a borda fica com 2 dp
 na cor do nível, a seta vira ▲; qualquer outro cartão aberto é recolhido]

MODAL "SOLICITAÇÃO DE CADASTRO"
[Estado inicial: registerStep = 1 → "Etapa 1 de 3", barra 1/3, só o botão "Próximo"]
      ↓
[Ação do usuário: toca em "Próximo" (ou "Voltar")]
      ↓
[Evento onClick: registerStep++ (ou registerStep--)]
      ↓
[Recomposição: muda o título e o texto da etapa, o contador "Etapa X de 3" e a barra;
 "Voltar" aparece a partir da etapa 2; na etapa 3 o botão vira "Entendi" e fecha o modal]
```

### Visual Feedback

1. **Cartão aberto:** a descrição do nível aparece com uma animação suave, a seta muda de ▼ para ▲ e a borda engrossa para 2 dp na cor do nível.
2. **Um cartão por vez:** ao abrir outro nível, o anterior se recolhe sozinho; tocar de novo no cartão aberto recolhe todos.
3. **Guia de cadastro:** o contador "Etapa X de 3" e a barra de progresso verde acompanham a etapa atual.
4. **Botões do guia:** "Voltar" só aparece a partir da etapa 2; na última etapa, "Próximo" vira "Entendi".
5. **Legibilidade:** os dois modais passam a ter fundo branco (antes `#ECE6F0`) e os nomes dos níveis ficam em azul-marinho escuro, com contraste acima de 11:1.

---

## 3. Scope & Requirements

### Functional Requirements

Resumo dos requisitos detalhados em [`SPEC-002.md`](docs/specs/SPEC-002.md):

- **FR-01 — Guia em etapas:** o modal de cadastro mostra o conteúdo em 3 etapas, uma por vez.
- **FR-02 — Navegação entre etapas:** botões "Próximo" e "Voltar" ("Voltar" só a partir da etapa 2).
- **FR-03 — Indicador de progresso:** texto "Etapa X de 3" e barra de progresso.
- **FR-04 — Conclusão e reinício:** "Entendi" fecha o guia; ao reabrir, ele volta para a etapa 1.
- **FR-05 — Cartões expansíveis:** os 4 níveis começam recolhidos e abrem a descrição ao toque.
- **FR-06 — Um cartão aberto por vez:** abrir um nível recolhe o anterior.
- **FR-07 — Indicação visual:** seta ▼/▲ e borda destacada no cartão aberto.
- **FR-08 — Fundo dos modais:** cor de superfície branca do tema do SARC.
- **FR-09 — Nomes dos níveis legíveis:** azul-marinho escuro, contraste mínimo de 4,5:1.
- **FR-10 — Linguagem do usuário:** textos sem jargão interno do projeto.

### Non-Functional Requirements

- **NFR-01 — Performance de Recomposição:** a mudança de estado recompõe apenas o modal aberto, sem travamentos perceptíveis.
- **NFR-02 — Design System Material 3:** os componentes usam `Card`, `AlertDialog`, `LinearProgressIndicator` e as cores do tema do SARC.
- **NFR-03 — Compatibilidade e Regressão:** os campos de e-mail e senha, o botão de visibilidade da senha e os botões da Sprint 01 continuam funcionando.

---

## 4. Acceptance Criteria

- [x] **AC-01** — A especificação técnica `SPEC-002.md` existe e está preenchida no diretório `docs/specs/`.
- [x] **AC-02** — Uma interação significativa para o produto SARC foi claramente definida.
- [x] **AC-03** — O código Compose contém pelo menos um valor de estado gerenciado por `remember` e `mutableStateOf`.
- [x] **AC-04** — Um evento disparado pelo usuário altera o valor do estado.
- [x] **AC-05** — A interface visual (UI) reage visivelmente à mudança do estado.
- [x] **AC-06** — O estado inicial e os estados atualizados comportam-se corretamente em múltiplos cliques.
- [x] **AC-07** — Todas as funcionalidades da Sprint 01 continuam operando sem quebras (sem regressão).
- [x] **AC-08** — O aplicativo compila e executa no emulador sem falhas ou travamentos (`crashes`).
- [x] **AC-09** — As evidências visuais de antes e depois da interação estão salvas em `evidence/sprint-02/`.
- [x] **AC-10** — A equipe compreende e sabe explicar o fluxo de estado `Evento → Estado → Recomposição UI`.

Os 14 critérios específicos da funcionalidade (AC-01 a AC-14) e seus resultados estão na seção 11 da [`SPEC-002.md`](docs/specs/SPEC-002.md).

---

## 5. Regression Validation

1. **Campos de E-mail e Senha:** a digitação, a máscara da senha e o botão de visibilidade (mostrar/ocultar) continuam funcionando.
2. **Diálogos Modais da Sprint 01:** os botões "Sobre o SARC e Prioridades" e "Solicitar Cadastro" continuam abrindo e fechando seus modais ("Entendido", "Entendi", toque fora do modal e botão Voltar do celular).
3. **Brasão Institucional:** a identidade visual da Escola Cadidé permanece alinhada e nítida no topo da interface.
4. **Estabilidade:** 10 repetições seguidas de abrir e fechar os dois modais, sem travamentos e sem crash.

---

## 6. Evidence

### Before Interaction
![Before Interaction](evidence/sprint-02/before-interaction.png)
*Figura 1: Modal "Sobre o SARC e Prioridades" recém-aberto, com os 4 cartões recolhidos (`expandedLevel = null`) e fundo branco.*

### After Interaction
![After Interaction](evidence/sprint-02/after-interaction.png)
*Figura 2: Após o toque no Nível 4 (`expandedLevel = 4`): descrição visível, seta ▲ e borda destacada em vermelho.*

### Additional Evidence

| Arquivo | O que mostra |
| --- | --- |
| `initial-screen.png` | Tela de login (ponto de partida e não-regressão) |
| `about-priority-switch.png` | Nível 1 aberto e Nível 4 recolhido (um cartão por vez) |
| `about-priority-collapsed-again.png` | Todos recolhidos após novo toque no Nível 1 |
| `about-dialog-closed.png` | Tela de login após "Entendido" |
| `register-guide-step-1.png` | Guia na etapa 1, sem "Voltar" |
| `register-guide-step-2.png` | Guia na etapa 2, barra 2/3 e "Voltar" visível |
| `register-guide-step-3.png` | Guia na etapa 3, com o botão "Entendi" |
| `register-guide-closed.png` | Tela de login após "Entendi" |
| `register-guide-reopened.png` | Guia reaberto, de volta à etapa 1 |

---

## 7. AI Usage

### Tool
Claude Code (Claude Opus 5.5) e Google Antigravity (Gemini 3.8 Flash, modo de raciocínio alto).

### Purpose
Apoio no entendimento do estado declarativo no Jetpack Compose (`remember`, `mutableStateOf`, recomposição), na escrita da `SPEC-002.md`, na implementação dos dois estados e na captura das evidências no emulador.

### Generated Content
A IA sugeriu transformar o modal de cadastro em um guia de 3 etapas e os níveis de prioridade em cartões expansíveis, propôs a estrutura da SPEC-002 (FRs, ACs e plano de validação), mediu o fundo padrão dos modais (`#ECE6F0`) e o contraste dos nomes dos níveis, gerou o código de `expandedLevel` e `registerStep` com comentários explicativos e fez as capturas de tela via ADB.

### Human Changes
A equipe definiu o escopo (melhorar os dois modais existentes, sem formulário e sem navegação), escreveu o fluxo real de aprovação pela Coordenação Militar, ajustou os textos das etapas 2 e 3, pediu a separação dos ajustes visuais em FR-08, FR-09 e FR-10 e revisou o código e a documentação.

### Validation
A equipe compilou o app com `./gradlew assembleDebug` e testou manualmente no emulador Pixel 8 (API 37) todos os critérios da SPEC-002 (AC-01 a AC-14) e os critérios do curso (AC-01 a AC-10), incluindo a regressão da tela de login.

### Tabela Síntese (Padrão Oficial do Curso)

| Item | Team response |
| --- | --- |
| LLM/tool used | Claude Code (Claude Opus 5.5) e Google Antigravity (Gemini 3.8 Flash) |
| Task supported by the LLM | Escrita da SPEC-002, implementação dos estados `expandedLevel` e `registerStep` e captura das evidências |
| Main suggestion received | Cartões expansíveis com `remember { mutableStateOf<Int?>(null) }` (um aberto por vez) e guia de cadastro em 3 etapas com `LinearProgressIndicator` |
| What the team changed manually | Escopo da sprint, textos do fluxo de aprovação pela Coordenação Militar, divisão dos ajustes visuais (FR-08 a FR-10) e revisão final |
| How the result was validated | Build com `./gradlew assembleDebug` e testes manuais dos AC-01 a AC-14 da SPEC-002 no emulador Pixel 8 (API 37) |

---

## 8. Deliverables

- `projects/team-05/app/` — `WelcomeScreen.kt` com os estados `expandedLevel` e `registerStep`
- `projects/team-05/SPRINT-02.md` — relatório da Sprint 02
- `projects/team-05/docs/specs/SPEC-002.md` — especificação da interação com estado
- `projects/team-05/evidence/sprint-02/before-interaction.png` — estado inicial (obrigatório)
- `projects/team-05/evidence/sprint-02/after-interaction.png` — estado após a interação (obrigatório)
- `projects/team-05/evidence/sprint-02/` — 9 evidências adicionais listadas na seção 6
