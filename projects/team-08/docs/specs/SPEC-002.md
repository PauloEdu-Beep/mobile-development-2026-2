# SPEC-002 — State & User Interaction

## 1. Identificação

- **Equipe:** Team 08
- **Sprint:** SPRINT 02
- **Funcionalidade:** Interação com botão e atualização de estado

## 2. Objetivo

Adicionar uma interação simples à interface do aplicativo, permitindo que
uma ação realizada pelo usuário altere o estado da tela e apresente uma
resposta visual.

## 3. Estado inicial

Ao abrir a tela, o aplicativo apresenta a mensagem:

> Pronto para começar.

O estado inicial da interação será `false`.

## 4. Ação do usuário

O usuário deverá tocar no botão apresentado na tela.

## 5. Mudança de estado

Ao tocar no botão, o estado será alterado de `false` para `true`.

A alteração será realizada utilizando `remember` e `mutableStateOf`
do Jetpack Compose.

## 6. Resultado esperado

Quando o estado for `false`, a tela deverá apresentar:

> Pronto para começar.

Quando o estado for `true`, a tela deverá apresentar:

> Ação realizada com sucesso!

## 7. Requisitos

- Utilizar estado no Jetpack Compose.
- Utilizar `remember`.
- Utilizar `mutableStateOf`.
- O evento do botão deve alterar o estado.
- A interface deve reagir automaticamente à alteração do estado.
- A funcionalidade não deve causar erros ou travamentos.

## 8. Critérios de aceitação

### AC-01
O arquivo SPEC-002 deve existir no diretório especificado.

### AC-02
A interação deve estar definida e relacionada à interface do aplicativo.

### AC-03
O aplicativo deve possuir pelo menos um valor de estado.

### AC-04
O evento do botão deve alterar o estado.

### AC-05
A interface deve apresentar uma mudança visual após a interação.

### AC-06
O estado inicial e o estado atualizado devem funcionar corretamente.

### AC-07
As funcionalidades existentes da Sprint 01 devem continuar funcionando.

### AC-08
O aplicativo deve compilar e executar sem travamentos.

### AC-09
Devem existir evidências da tela antes e depois da interação.

### AC-10
Os integrantes da equipe devem conseguir explicar o fluxo:

Estado inicial → ação do usuário → alteração do estado → atualização da interface.
