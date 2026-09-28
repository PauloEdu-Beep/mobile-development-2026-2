# SPRINT 01 — Product Definition & First Screen

## Equipe

Team 09 — Fernando Henrique Cobianchi (20220059497) e João Victor R. Peres (20230079303). Disciplina FALECT-CC-040, UNEMAT/AIA, 2026.2; professor Breno Felix de Sousa.

## Produto

**Nome:** Ritmo.  
**Problema:** Estudantes com tempo limitado podem adiar o estudo por não saberem estruturar uma sessão curta.  
**Público:** Universitários, especialmente alunos que conciliam aulas e outras atividades.  
**Objetivo:** Ajudar o estudante a transformar minutos disponíveis em um plano simples de concentração.  
**Funcionalidades iniciais:** apresentação (sprint 1), escolha de duração (sprint 2) e plano com navegação (sprint 3).

## Objetivo e implementação desta sprint

Apresentar o produto em uma tela de boas-vindas.

Nome, slogan, descrição, cartão de foco e botão principal. Nesta branch, o botão não navega: comportamento ainda fora do escopo.

Especificação: [SPEC-001](docs/specs/SPEC-001.md), elaborada antes da implementação, seguindo as 16 seções do template da disciplina.

## Explicação da implementação

MainActivity chama setContent e aplica RitmoTheme. WelcomeScreen usa Scaffold para respeitar insets, Column para organização vertical, Modifier para margens e rolagem, Text para conteúdo e Button para a ação. @Preview permite inspecionar o layout no Android Studio.

Cada FR aponta para seu arquivo/função, AC e evidência na seção 8 da SPEC. As telas ficam em `app/app/src/main/java/br/unemat/ritmo/ui/`.

## Validação

Build e execução aprovados no emulador Pixel 6, Android 15/API 35. [Resultados por AC](evidence/sprint-01/validation.md), [log Gradle](evidence/sprint-01/build-and-tests.txt) e [testes instrumentados](evidence/sprint-01/instrumented-tests.xml).

Os critérios técnicos do enunciado foram verificados: especificação, comportamento desta sprint, regressão das funcionalidades anteriores, build, execução e capturas. O critério de explicação pelos integrantes está **pendente de revisão humana**, e não é comprovado pelos testes.

![first-screen.png](evidence/sprint-01/first-screen.png)

## Uso de IA

| Item | Resposta |
| --- | --- |
| LLM/tool used | Codex |
| Task supported by the LLM | SPEC, código, explicação, testes, build, capturas e relatório |
| Main suggestion received | Implementar somente o incremento especificado, com componentes separados e critérios verificáveis |
| What the team changed manually | Não declarado; revisão humana pelos alunos ainda pendente |
| How the result was validated | Compilação, testes instrumentados no Android, interação por ADB e inspeção visual pela ferramenta |

## Limitações

Sem cronômetro, banco, login, rede ou histórico. O app orienta o planejamento, não mede o tempo de estudo. Estado salvo de interface não equivale a persistência permanente. O conteúdo está em português e o tema claro é fixo nesta entrega.

## Git e entrega

Branch `team-09/sprint-01`, criada antes das alterações. Fork `botist/mobile-development-2026-2`; destino do PR: `brenofeliix/mobile-development-2026-2`, branch `main`. Nenhum merge é feito pela equipe.

As sprints foram preparadas em sequência antes da revisão do professor, por solicitação da equipe. A branch inclui os incrementos anteriores; os PRs posteriores devem aguardar a integração dos anteriores. A exigência oficial de confirmar o merge antes de iniciar a sprint seguinte ainda não foi cumprida; isso está explicitado, sem simular aprovação.

## Definition of Done

- [x] Produto e escopo documentados; SPEC completa.
- [x] Funcionalidade implementada; compilação e execução verificadas.
- [x] Testes e evidências incluídos; uso de IA declarado.
- [ ] Revisão humana, validação pessoal e explicação pelos dois integrantes.
- [ ] Aprovação e integração pelo professor.

O código e os artefatos técnicos estão preparados; a conclusão acadêmica depende das etapas humanas acima.
