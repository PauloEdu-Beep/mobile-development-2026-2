# SPRINT 03 — Navigation & Multiple Screens

## Equipe

Team 09 — Fernando Henrique Cobianchi (20220059497) e João Victor R. Peres (20230079303). Disciplina FALECT-CC-040, UNEMAT/AIA, 2026.2; professor Breno Felix de Sousa.

## Produto

**Nome:** Ritmo.  
**Problema:** Estudantes com tempo limitado podem adiar o estudo por não saberem estruturar uma sessão curta.  
**Público:** Universitários, especialmente alunos que conciliam aulas e outras atividades.  
**Objetivo:** Ajudar o estudante a transformar minutos disponíveis em um plano simples de concentração.  
**Funcionalidades iniciais:** apresentação (sprint 1), escolha de duração (sprint 2) e plano com navegação (sprint 3).

## Objetivo e implementação desta sprint

Navegar entre a escolha de duração e o plano de estudo.

Duas telas com responsabilidades distintas: inicio apresenta e configura; plano mostra a duração e orientações para preparar, concentrar e pausar.

Especificação: [SPEC-003](docs/specs/SPEC-003.md), elaborada antes da implementação, seguindo as 16 seções do template da disciplina.

## Explicação da implementação

RitmoApp cria rememberNavController e NavHost com inicio e plano. Planejar meu estudo chama navigate("plano") com launchSingleTop. O estado fica acima do NavHost e é passado às telas. Voltar e Ajustar duração chamam popBackStack(); o Navigation Compose integra o Voltar do Android. Na raiz, o Voltar encerra a Activity. O plano não é cronômetro.

Cada FR aponta para seu arquivo/função, AC e evidência na seção 8 da SPEC. As telas ficam em `app/app/src/main/java/br/unemat/ritmo/ui/`.

## Validação

Build e execução aprovados no emulador Pixel 6, Android 15/API 35. [Resultados por AC](evidence/sprint-03/validation.md), [log Gradle](evidence/sprint-03/build-and-tests.txt) e [testes instrumentados](evidence/sprint-03/instrumented-tests.xml).

Os critérios técnicos do enunciado foram verificados: especificação, comportamento desta sprint, regressão das funcionalidades anteriores, build, execução e capturas. O critério de explicação pelos integrantes está **pendente de revisão humana**, e não é comprovado pelos testes.

![screen-a.png](evidence/sprint-03/screen-a.png)
![screen-b.png](evidence/sprint-03/screen-b.png)
![back-preserved.png](evidence/sprint-03/back-preserved.png)

## Uso de IA

| Item | Resposta |
| --- | --- |
| LLM/tool used | Codex |
| Task supported by the LLM | SPEC, código, explicação, testes, build, capturas e relatório |
| Main suggestion received | Implementar somente o incremento especificado, com componentes separados e critérios verificáveis |
| What the team changed manually | Não declarado; revisão humana pelos alunos ainda pendente |
| How the result was validated | Compilação, testes instrumentados no Android, interação por ADB e inspeção visual pela ferramenta |

## Limitações

`lintDebug` aprovado com 0 erros e 9 avisos (atualizações de dependências e sugestão de configuração de backup). Foi corrigida a exigência de API 27 no tema base usando um recurso específico `values-v27`. Também foi verificado o alcance das ações por rolagem em tela de 360×640 dp.

Sem cronômetro, banco, login, rede ou histórico. O app orienta o planejamento, não mede o tempo de estudo. Estado salvo de interface não equivale a persistência permanente. O conteúdo está em português e o tema claro é fixo nesta entrega.

## Git e entrega

Branch `team-09/sprint-03`, criada antes das alterações. Fork `botist/mobile-development-2026-2`; destino do PR: `brenofeliix/mobile-development-2026-2`, branch `main`. Nenhum merge é feito pela equipe.

As sprints foram preparadas em sequência antes da revisão do professor, por solicitação da equipe. A branch inclui os incrementos anteriores; os PRs posteriores devem aguardar a integração dos anteriores. A exigência oficial de confirmar o merge antes de iniciar a sprint seguinte ainda não foi cumprida; isso está explicitado, sem simular aprovação.

## Definition of Done

- [x] Produto e escopo documentados; SPEC completa.
- [x] Funcionalidade implementada; compilação e execução verificadas.
- [x] Testes e evidências incluídos; uso de IA declarado.
- [ ] Revisão humana, validação pessoal e explicação pelos dois integrantes.
- [ ] Aprovação e integração pelo professor.

O código e os artefatos técnicos estão preparados; a conclusão acadêmica depende das etapas humanas acima.
