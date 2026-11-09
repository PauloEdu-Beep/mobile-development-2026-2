# Team 08 — SPRINT 03

## Navigation & Multiple Screens

### Team Information
- **Número da equipe:** Team 08
- **Membros da equipe:**
  - Ítalo Huan (@itimt)
  - Rayse Kawane (@Rays3107)

---

## 1. Objetivo da Sprint

Evoluir o aplicativo **Mercadinho** de um protótipo de tela única para uma aplicação Android multi-telas utilizando o **Jetpack Navigation Compose**, com separação clara de responsabilidades entre Composables, gerenciamento de rotas e pilha de navegação (back stack).

---

## 2. Telas Desenvolvidas

O aplicativo agora conta com duas telas organizadas em arquivos independentes:

1. **`WelcomeScreen` (Screen A - Rota: `"welcome"`):**
   - Apresenta o nome do aplicativo ("Mercadinho") e o slogan explicativo.
   - Fornece um botão de ação primária ("Ver Lista de Compras") para iniciar o fluxo de navegação.

2. **`ShoppingListScreen` (Screen B - Rota: `"shopping_list"`):**
   - Exibe a lista de compras do usuário para consulta e gerenciamento no dia a dia.
   - Permite interagir com os itens (marcar/desmarcar itens como comprados), preservando o comportamento de manipulação de estado em Jetpack Compose da Sprint 02.
   - Possui uma barra superior (`TopAppBar`) com ícone de retorno para voltar à tela inicial.

---

## 3. Grafo de Navegação

A navegação foi centralizada no componente `AppNavHost`:
- **`NavController`**: Gerencia o histórico e a pilha de navegação do Compose.
- **`NavHost`**: Define a rota inicial (`AppDestinations.WELCOME`) e o mapeamento das rotas.
- **Navegação de Ida (Forward):** O botão na `WelcomeScreen` dispara `navController.navigate("shopping_list")`.
- **Navegação de Retorno (Back):** O botão de seta na barra superior da `ShoppingListScreen` aciona `navController.popBackStack()`, retirando a tela atual da pilha e retornando à tela de boas-vindas. O botão/gesto nativo de voltar do Android também foi validado com o mesmo comportamento.

```text
[ WelcomeScreen ] ── (clique no botão "Ver Lista") ──> [ ShoppingListScreen ]
        ▲                                                      │
        └──────────── (popBackStack / Voltar) ─────────────────┘
```

---

## 4. Estrutura de Arquivos

Conforme os critérios de aceitação e boas práticas, o projeto segue o padrão oficial da disciplina:

```text
projects/team-08/
├── SPRINT-03.md
├── docs/
│   └── specs/
│       └── SPEC-003.md
├── evidence/
│   └── sprint-03/
│       ├── screen-a.png
│       └── screen-b.png
└── app/
    ├── build.gradle.kts
    ├── settings.gradle.kts
    ├── gradle.properties
    └── app/
        └── src/main/java/com/example/mercadinho/
            ├── MainActivity.kt
            └── ui/
                ├── navigation/
                │   └── AppNavHost.kt
                └── screens/
                    ├── WelcomeScreen.kt
                    └── ShoppingListScreen.kt
```

---

## 5. Uso de Ferramentas de IA (LLM)

| Item | Team response |
| --- | --- |
| **LLM/tool used** | Google Antigravity (Gemini 3.8 Flash) |
| **Task supported by the LLM** | Inclusão do Navigation Compose nas dependências Gradle, modularização das telas em arquivos separados, reorganização de diretórios e elaboração da `SPEC-003.md` |
| **Main suggestion received** | Criação de `AppNavHost.kt` para centralizar as rotas e desacoplar `WelcomeScreen` e `ShoppingListScreen` em arquivos composables dedicados |
| **What the team changed manually** | Definição das rotas e textos específicos do Mercadinho, modelagem dos itens de compras do dia a dia e ajuste da barra de retorno |
| **How the result was validated** | Compilação com Gradle (`./gradlew assembleDebug`), execução no emulador Android Studio, validação do clique e teste de retorno |

---

## 6. Evidências de Execução

As capturas de tela foram registradas na pasta `projects/team-08/evidence/sprint-03/`:

- **Screen A (Welcome Screen):** `evidence/sprint-03/screen-a.png`  
- **Screen B (Shopping List Screen):** `evidence/sprint-03/screen-b.png`  
