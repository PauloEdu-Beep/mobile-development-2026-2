# Team 08 — SPRINT 06

## External or Device Integration

### Team Information
- **Número da equipe:** Team 08
- **Membros da equipe:**
  - Ítalo Huan (@itimt)
  - Rayse Kawane (@Rays3107)

---

## 1. Objetivo da Sprint

Integrar o aplicativo **Mercadinho** a uma fonte de dados externa relevante para o objetivo do produto, implementando operações assíncronas com tratamento explícito de estados de interface (**Carregando**, **Sucesso** e **Erro/Offline**), respeitando a privacidade e segurança sem comprometer segredos ou credenciais privadas.

---

## 2. Escolha e Relevância da Integração Externa

### Serviço Escolhido:
- **API Pública REST de Receitas Brasileiras:** Catálogo estruturado em JSON com pratos brasileiros autênticos, fotos em alta definição e ingredientes típicos do dia a dia.
- **Endpoint Principal:** `https://raw.githubusercontent.com/itimt/mobile-development-2026-2/team-08/sprint-06/projects/team-08/receitas_brasileiras.json`

### Relevância para o Mercadinho:
Ao organizar as compras de supermercado, os usuários buscam ideias de pratos e receitas brasileiras clássicas para cozinhar durante a semana (como Macarrão ao Molho, Feijoada Tradicional, Estrogonofe de Frango, Moqueca Baiana, Pão de Queijo Mineiro, Pudim de Leite Condensado e Brigadeiro de Panela). A integração permite consultar pratos de diversas categorias, fotos dos pratos prontos e toda a lista de ingredientes com medidas brasileiras.

O usuário pode, com um simples toque em **"Adicionar Ingredientes à Lista"**, importar automaticamente todos os ingredientes da receita direto para a lista de compras persistida no Mercadinho.

---

## 3. Fluxo de Dados e Comportamento Assíncrono

```text
[Usuário seleciona categoria na UI]
                 ↓
[Lançamento de Coroutine (Dispatchers.IO) em RecipeRepository]
                 ↓
[UI exibe estado 'Loading' com CircularProgressIndicator]
                 ↓
[Requisição HTTP GET assíncrona para a API TheMealDB]
                 ↓
     ┌───────────────────────┴───────────────────────┐
     ↓                                               ↓
[Sucesso HTTP 200]                             [Falha de Rede / Erro 503]
     ↓                                               ↓
[Parsing JSON estruturado]                      [Captura da exceção]
     ↓                                               ↓
[UI renderiza estado 'Success']                 [UI renderiza estado 'Error']
[Cards com foto Coil + Ingredientes]            [Aviso visual + Botão Tentar Novamente]
```

---

## 4. Tratamento de Falhas e Modo Alternativo

Para garantir conformidade com o critério `AC-05`, o aplicativo trata tanto falhas reais de conectividade (como queda de Wi-Fi ou timeout no servidor) quanto oferece uma chave de **"Simular Sem Internet (Modo Offline)"** diretamente na tela. Quando a requisição falha:
1. A interface exibe um card de alerta em vermelho (`errorContainer`) com ícone indicativo de nuvem desconectada.
2. É exibida a mensagem explicativa da falha.
3. Um botão de ação **"Tentar Novamente"** permite reexecutar a requisição assim que o serviço for restabelecido.

---

## 5. Privacidade e Segurança (Proteção de Segredos)

- A API TheMealDB opera em camada pública aberta sem necessidade de chaves privadas (*API Keys*), tokens ou senhas (`AC-06`).
- Não foi commitado nenhum arquivo com credenciais confidenciais.
- A única permissão necessária no dispositivo é o acesso à rede (`android.permission.INTERNET`).

---

## 6. Uso de Ferramentas de IA (LLM)

| Item | Team response |
| --- | --- |
| **LLM/tool used** | Google Antigravity (Gemini 3.8 Flash) |
| **Task supported by the LLM** | Modelagem da arquitetura de integração externa (`RecipeRepository`), definição da `SPEC-006.md`, configuração do Coil Compose e elaboração do fluxo assíncrono com Coroutines |
| **Main suggestion received** | Isolar as chamadas de rede em `RecipeRepository` utilizando `Dispatchers.IO`, garantindo a separação entre rede e a UI reativa que consome o estado selado (`RecipeUiState`) |
| **What the team changed manually** | Seleção do nicho culinário e categorias de receitas integradas à lista de compras, ajuste de layout dos cards no Compose e validação em execução no emulador |
| **How the result was validated** | Execução no emulador Android Studio, validação das fotos e ingredientes baixados pela API (sucesso) e ativação do modo offline para comprovar o tratamento gracioso de erro |

---

## 7. Evidências de Execução

As imagens foram salvas na pasta `projects/team-08/evidence/sprint-06/`:

- **Sucesso da Integração (`integration-success.png`):** Exibição da tela de sugestões com receitas carregadas pela API, fotos dos pratos e lista de ingredientes.
- **Estado Alternativo / Erro (`integration-alternative-state.png`):** Exibição do estado de falha de conexão/offline com aviso amigável e botão de recarregar.
