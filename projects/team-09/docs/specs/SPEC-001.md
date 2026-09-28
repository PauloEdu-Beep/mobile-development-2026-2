# SPEC-001 — Apresentação do Ritmo

> **Team:** Team 09  
> **Sprint:** Sprint 01  
> **Status:** Validada tecnicamente; revisão humana pendente  
> **Related Sprint:** [SPRINT-01.md](../../SPRINT-01.md)

## 1. Context

**Problema:** Estudantes com pouco tempo disponível podem adiar o estudo por não saberem como organizar uma sessão curta.  
**Usuários:** Estudantes universitários, incluindo alunos da UNEMAT.  
**Contexto:** Intervalos entre atividades em que o estudante quer planejar um período de concentração.

## 2. Objective

Apresentar o propósito do produto e uma ação principal em uma tela inicial organizada.

## 3. User Scenario

Dado que o estudante deseja organizar um período de estudo, ao abrir o app, o estudante encontra nome, slogan, descrição e o botão Planejar meu estudo.

## 4. Functional Requirements

### FR-01

Exibir o nome Ritmo.

### FR-02

Exibir o slogan Estude no seu ritmo. e uma descrição do objetivo.

### FR-03

Exibir o botão principal Planejar meu estudo.

### FR-04

Organizar conteúdo com Compose, tema Material, margens e rolagem em telas menores.

## 5. Constraints

Kotlin, Jetpack Compose, Material 3, Android Studio, SDK mínimo 24 e compilação com SDK 35. Alterar somente `projects/team-09/`. Nenhuma pasta de outra equipe nem arquivos da disciplina devem ser alterados. Não solicitar permissões de dispositivo ou rede. Dependências com versões fixas.

## 6. Out of Scope

Interação, navegação, cronômetro, banco, login, notificações, APIs e formulários.

## 7. Acceptance Criteria

### AC-01

**Related requirement:** FR-01  
**Condition:** O texto Ritmo é visível na abertura.

### AC-02

**Related requirement:** FR-02  
**Condition:** O slogan Estude no seu ritmo. e a descrição Uma sessão de cada vez. estão visíveis.

### AC-03

**Related requirement:** FR-03  
**Condition:** Planejar meu estudo está visível; o toque não navega nem causa erro nesta sprint.

### AC-04

**Related requirement:** FR-04  
**Condition:** Tela executa sem crash, respeita as barras do sistema e permite alcançar o conteúdo por rolagem.

## 8. Requirement Traceability

| Requirement | Implemented In | Acceptance Criterion | Evidence |
| --- | --- | --- | --- |
| FR-01 | `app/src/main/java/br/unemat/ritmo/ui/WelcomeScreen.kt / Brand` (dentro de `app/`) | AC-01 | [Validação](../../evidence/sprint-01/validation.md) e capturas abaixo |
| FR-02 | `app/src/main/java/br/unemat/ritmo/ui/WelcomeScreen.kt / WelcomeScreen` (dentro de `app/`) | AC-02 | [Validação](../../evidence/sprint-01/validation.md) e capturas abaixo |
| FR-03 | `app/src/main/java/br/unemat/ritmo/ui/WelcomeScreen.kt / Button` (dentro de `app/`) | AC-03 | [Validação](../../evidence/sprint-01/validation.md) e capturas abaixo |
| FR-04 | `app/src/main/java/br/unemat/ritmo/ui/Theme.kt e WelcomeScreen.kt / Column` (dentro de `app/`) | AC-04 | [Validação](../../evidence/sprint-01/validation.md) e capturas abaixo |

## 9. Implementation Plan

Separar Activity, tema e WelcomeScreen; usar Column, Text, Surface, Button, Spacer e Modifier.

**Estado e fluxo:** Sem estado mutável ou navegação; o botão possui callback vazio nesta etapa.

## 10. Validation Plan

Compilar com `gradlew.bat assembleDebug`, instalar no emulador e executar:

1. Instalar e abrir o APK.
2. Verificar nome, slogan e descrição.
3. Localizar e tocar Planejar meu estudo; nesta sprint não há comportamento associado.
4. Comparar a tela com os requisitos e capturar first-screen.png.

## 11. Validation Results

Resultados executados e registrados em [validation.md](../../evidence/sprint-01/validation.md).

### Environment Used for Validation

Pixel 6 virtual, Android 15/API 35, x86_64, Windows/WHPX. Compilação Gradle 8.11.1, JDK 21. Logs de build e testes em `evidence/sprint-01/`.

## 12. Evidence

- [first-screen.png](../../evidence/sprint-01/first-screen.png)

## 13. AI-Assisted Development

**Ferramenta:** Codex. Auxiliou na compreensão do enunciado, especificação, código Kotlin, configuração, testes, depuração e documentação. O pedido foi implementar este incremento do Ritmo segundo os FRs e limites acima. O código e a documentação deste incremento foram gerados com essa assistência.

**Human Review and Changes:** A revisão humana e alterações manuais pelos alunos ainda não foram confirmadas. A validação técnica pela ferramenta é descrita separadamente nas evidências; não equivale à compreensão dos integrantes. Cada aluno deve ler, executar e explicar as funções antes da apresentação.

- [ ] Conteúdo revisado pelos alunos.
- [ ] Ambos compreendem a implementação.
- [ ] Critérios validados pessoalmente pelos alunos.
- [x] Escopo limitado à especificação desta sprint.

## 14. Suggested Prompt for AI Assistance

“Explique a implementação do FR-01 da SPEC-001, identifique estado, componente e callback envolvidos, proponha apenas a mudança necessária e descreva como verificar o AC-01. Respeite os itens fora de escopo.”

## 15. Deliverables

Projeto Android atualizado, esta SPEC, SPRINT-01.md, capturas de execução e resultados de validação em evidence/sprint-01/.

## 16. Specification Status

- [x] Contexto, objetivo, FRs, limites e critérios mensuráveis definidos.
- [x] Plano de implementação e de validação definidos.
- [x] Implementação e evidências verificadas pela ferramenta; ver resultados.
- [ ] Cada integrante consegue explicar a funcionalidade.

## Final Check

Localize um FR, a função indicada na rastreabilidade e sua evidência; demonstre o critério no emulador sem auxílio da IA.
