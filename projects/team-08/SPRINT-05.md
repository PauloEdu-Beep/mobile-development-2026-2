# Team 08 — SPRINT 05

## Local Persistence & Data

### Team Information
- **Número da equipe:** Team 08
- **Membros da equipe:**
  - Ítalo Huan (@itimt)
  - Rayse Kawane (@Rays3107)

---

## 1. Objetivo da Sprint

Implementar uma camada de **persistência local de dados** no aplicativo **Mercadinho**, fazendo com que os dados estruturados de compras (itens da lista e registros de compras finalizadas) sobrevivam ao encerramento e reinício do aplicativo, dissociando o estado transitório de tela da persistência permanente.

---

## 2. Decisão de Persistência e Modelo de Dados

### Mecanismo de Armazenamento:
Foi implementado um repositório dedicado (`ShoppingRepository`) que gerencia o armazenamento local persistente no dispositivo, isolando a lógica de leitura e gravação dos componentes visuais.

### Estruturas de Dados Persistidas:
1. **`ShoppingItem` (Itens da Lista de Compras):**
   - `id`: Identificador numérico do item
   - `name`: Nome do produto cadastrado
   - `isChecked`: Estado de marcação (comprado ou pendente)

2. **`PurchaseRecord` (Histórico de Compras):**
   - `id`: Identificador único do registro
   - `marketName`: Nome do supermercado onde a compra foi realizada
   - `amount`: Valor total da compra em R$
   - `date`: Data do registro da compra
   - `itemsSummary`: Resumo dos itens que foram comprados nessa compra (visualizável no botão "Minhas Compras")

---

## 3. Fluxo de Salvamento e Leitura

```text
[Alteração na UI: Adição / Marcação / Exclusão / Finalização]
                          ↓
[ShoppingRepository salva os registros atualizados em disco]
                          ↓
─────── [Usuário encerra o app / Reinicia o processo] ───────
                          ↓
[Ao reabrir: ShoppingRepository lê os dados salvos do disco]
                          ↓
[Lista de compras e último registro exibidos intactos]
```

---

## 4. Uso de Ferramentas de IA (LLM)

| Item | Team response |
| --- | --- |
| **LLM/tool used** | Google Antigravity (Gemini 3.8 Flash) |
| **Task supported by the LLM** | Modelagem da camada de dados (`ShoppingRepository`), elaboração da `SPEC-005.md` e integração com a interface Compose |
| **Main suggestion received** | Criação de uma classe repositório isolada no pacote `data/` com métodos claros de leitura/gravação, evitando acoplamento de persistência dentro de botões da UI |
| **What the team changed manually** | Definição das regras de negócio do Mercadinho, adaptação dos dados iniciais padrão e teste manual de reinício do aplicativo |
| **How the result was validated** | Execução no emulador Android Studio, criação de novo item, encerramento forçado do processo do app e validação da restauração automática ao reabrir |

---

## 5. Evidências de Execução

As imagens foram salvas na pasta `projects/team-08/evidence/sprint-05/`:

- **Criação de Dados (`data-created.png`):** Exibição do aplicativo com itens criados e/ou compra finalizada.
- **Restauração de Dados (`data-restored.png`):** Exibição do aplicativo reaberto após o fechamento completo, comprovando a recuperação dos dados salvos no disco.
