# Guia de estudo e apresentação — Ritmo

Este roteiro é material de preparação. Cada integrante deve executar os passos e explicar o código com suas próprias palavras. Não é uma declaração de revisão já realizada.

## Demonstração em aproximadamente cinco minutos

1. Apresente o problema: pouco tempo livre e dificuldade para começar uma sessão de estudo. Mostre nome, slogan, descrição e ação principal.
2. Abra SPEC-001 e localize FR-01. Mostre `Brand()` em `WelcomeScreen.kt` e `first-screen.png` da sprint 1.
3. Na versão atual, mostre 25 minutos inicialmente; toque 15 e 45. Explique o que muda no estado, no chip e no resumo.
4. Abra `RitmoApp.kt`: identifique `selectedMinutes`, `mutableStateOf`, `rememberSaveable` e o callback `onSelect`. Na branch da sprint 2, esse estado estava dentro de `WelcomeScreen`; na sprint 3 foi elevado para ser compartilhado.
5. Toque em Planejar meu estudo. Mostre Seu plano, a duração escolhida e as três orientações.
6. Demonstre Voltar da tela, Ajustar duração e Voltar do Android. A escolha permanece ao retornar. Não há cronômetro nesta entrega.
7. Mostre `NavHost`, as rotas `inicio` e `plano`, `navigate`, `launchSingleTop` e `popBackStack`.
8. Abra o relatório da sprint e os resultados dos testes. Explique que build gera o APK, run executa no Android e testes verificam cenários específicos.
9. Mostre a branch e o PR. A revisão e o merge cabem ao professor.

## Perguntas que ambos devem conseguir responder

| Pergunta | Onde estudar |
| --- | --- |
| Como a Activity mostra Compose? | `MainActivity.onCreate` → `setContent` → `RitmoTheme` → `RitmoApp` |
| O que é uma função `@Composable`? | Função que descreve a interface a partir de entradas; ver as duas telas |
| Por que usar `Scaffold` e seu padding? | Espaço para barras do sistema e estrutura da tela |
| O que fazem `Column`, `Row`, `Spacer` e `Modifier`? | Organização vertical/horizontal, espaço e configuração do componente |
| Por que uma variável comum não basta? | Alterá-la não notifica o Compose; `mutableStateOf` torna o valor observável |
| Qual a diferença entre `remember` e `rememberSaveable`? | Ambos retêm valor entre recomposições; o segundo também usa restauração de estado compatível com o tipo salvo |
| Onde ocorre evento → estado → UI? | `FilterChip.onClick` → `onSelect` → atribuição em `RitmoApp` → novo resumo e seleção |
| Por que elevar o estado? | As duas telas precisam da mesma duração; há uma única fonte do valor |
| Qual a função do NavController? | Controlar destinos e pilha de navegação |
| Qual a função do NavHost? | Associar rotas às telas e mostrar o destino atual |
| O que faz `launchSingleTop`? | Evita adicionar outra cópia da rota já no topo |
| Por que voltar com `popBackStack`? | Remove o destino atual e recupera a tela anterior |
| O app salva histórico? | Não. Estado de interface restaurável não é armazenamento permanente |
| A duração é um cronômetro? | Não. É uma escolha usada no plano de estudo |
| Como foi usada IA? | Na implementação e documentação; ver a declaração em cada relatório |

## Leitura do projeto

Os arquivos abaixo são relativos a `app/app/src/main/java/br/unemat/ritmo/`:

| Arquivo | Responsabilidade |
| --- | --- |
| `MainActivity.kt` | Entrada Android e conteúdo Compose |
| `ui/RitmoApp.kt` | Estado compartilhado e navegação |
| `ui/WelcomeScreen.kt` | Apresentação e ação principal |
| `ui/DurationPicker.kt` | Opções exclusivas e resumo |
| `ui/PlanScreen.kt` | Plano e ações de retorno |
| `ui/Theme.kt` | Cores e tipografia |

Os testes estão em `app/app/src/androidTest/java/br/unemat/ritmo/`: `WelcomeTest`, `DurationTest` e `NavigationTest`. Eles executam no Android e cobrem apresentação, estado inicial, repetição, recriação da Activity, conteúdo do destino e retorno.

## Exercícios para conferir compreensão

1. Mude uma frase da tela inicial, compile e mostre o resultado.
2. Identifique todos os lugares afetados caso uma opção de duração mude de 45 para 50 minutos: opções, critérios e testes.
3. Explique o que aconteceria se o estado fosse criado apenas dentro de `PlanScreen`.
4. Explique por que as capturas das sprints 1 e 2 têm comportamento anterior ao da sprint 3. Cada branch preserva um incremento, e suas SPECs descrevem aquele momento.

## Revisão antes de marcar o PR como pronto

Cada aluno deve ler as SPECs e funções, executar os critérios no emulador e registrar honestamente o que revisou ou alterou. Atualizem os campos de revisão humana nas SPECs, relatórios e PRs somente depois disso. Mantenham o mesmo PR e a mesma branch ao corrigir; não façam merge no repositório do professor.
