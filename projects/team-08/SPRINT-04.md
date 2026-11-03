# Team 08 — SPRINT 04

## User Input, Forms & Validation

### Team Information
- **Número da equipe:** Team 08
- **Membros da equipe:**
  - Ítalo Huan (@itimt)
  - Rayse Kawane (@Rays3107)

---

## 1. Objetivo da Sprint

Implementar a coleta de dados de forma controlada através de formulário em **Jetpack Compose**, estabelecendo regras de validação claras para cenários válidos e inválidos, com feedback visual imediato de erro para o usuário.

---

## 2. Formulário Implementado

No aplicativo **Mercadinho**, implementamos o formulário de **"Finalizar Compra"**, acionado a partir da tela de lista de compras.

### Campos do Formulário:
1. **Nome do Supermercado (Obrigatório):**
   - Regra: Deve conter no mínimo 3 caracteres não vazios.
   - Cenário Inválido: Campo vazio ou menor que 3 letras $\to$ borda vermelha e mensagem *"Informe o nome do supermercado (mínimo 3 caracteres)"*.
2. **Valor Total Gasto em R$ (Opcional):**
   - Regra: Se preenchido, deve ser um número decimal válido (ex: `149.90`).
   - Cenário Inválido: Letras ou formato incorreto $\to$ mensagem *"Informe um valor numérico válido"*.
3. **Data da Compra:**
   - Exibição da data atual da transação.

---

## 3. Fluxo de Validação e Feedback

- **Cenário Inválido (`invalid-input.png`):**  
  O usuário tenta clicar no botão "Confirmar Compra" sem preencher o nome do supermercado. O Compose atualiza o estado com erro, bloqueia a finalização e exibe o feedback visual em vermelho.

- **Cenário Válido (`valid-input.png`):**  
  O usuário digita o nome do supermercado (ex: "Supermercado Big Mais") e opcionalmente o valor. Ao clicar em "Confirmar Compra", o app valida os dados, exibe a confirmação de compra realizada e reinicia a lista desmarcando os itens para as próximas compras.

---

## 4. Uso de Ferramentas de IA (LLM)

| Item | Team response |
| --- | --- |
| **LLM/tool used** | Google Antigravity (Gemini 3.8 Flash) |
| **Task supported by the LLM** | Estruturação da `SPEC-004.md`, implementação do formulário com estado controlado em Compose e regras de validação com mensagens de erro |
| **Main suggestion received** | Utilização de um modal `AlertDialog` estilizado contendo `OutlinedTextField` com parâmetros `isError` e `supportingText` para feedback visual explícito |
| **What the team changed manually** | Definição das regras de negócio do Mercadinho (mínimo de 3 caracteres para o nome do mercado, valor numérico decimal opcional) e limpeza da lista após finalização |
| **How the result was validated** | Execução no emulador Android Studio, validação da tentativa com campo vazio (cenário inválido) e com preenchimento correto (cenário válido) |

---

## 5. Evidências de Execução

As imagens foram salvas na pasta `projects/team-08/evidence/sprint-04/`:

- **Cenário Inválido (Feedback de erro visível):** `evidence/sprint-04/invalid-input.png`  
- **Cenário Válido (Formulário aceito com sucesso):** `evidence/sprint-04/valid-input.png`  
