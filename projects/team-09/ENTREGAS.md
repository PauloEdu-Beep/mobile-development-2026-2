# Entregas — Ritmo / Team 09

Preparação técnica concluída até a sprint 3 em 28/09/2026. Fork: [botist/mobile-development-2026-2](https://github.com/botist/mobile-development-2026-2/tree/team-09/sprint-03/projects/team-09).

| Sprint | Branch | Pull Request | Validação |
| --- | --- | --- | --- |
| 00 | `team-09/sprint-00` | [#19](https://github.com/brenofeliix/mobile-development-2026-2/pull/19) | Build e execução da aplicação mínima |
| 01 | `team-09/sprint-01` | [#20](https://github.com/brenofeliix/mobile-development-2026-2/pull/20) | Tela inicial e 1 teste instrumentado |
| 02 | `team-09/sprint-02` | [#21](https://github.com/brenofeliix/mobile-development-2026-2/pull/21) | Seleção reativa, restauração e 3 testes |
| 03 | `team-09/sprint-03` | [#22](https://github.com/brenofeliix/mobile-development-2026-2/pull/22) | Navegação, regressão, 7 testes e lint sem erros |

Todos os PRs apontam para `main` do professor, alteram apenas `projects/team-09/` e estão **prontos para avaliação do professor**. Nenhum foi integrado pela equipe.

## O que está pronto

Projeto Kotlin/Compose, três SPECs, relatórios das sprints 00–03, capturas reais de cada incremento, logs de compilação e testes, guia de apresentação e script de validação. A versão atual é 0.3.0 e está na branch `team-09/sprint-03`.

## Após a entrega pelo repositório

1. A explicação pelos integrantes será avaliada presencialmente ao final da disciplina, conforme esclarecido pela equipe. O [guia](docs/GUIA-DE-APRESENTACAO.md) serve de roteiro de estudo; essa etapa não bloqueia o envio pelo GitHub.
2. Conferir com o professor a identificação Team 09, escolhida porque 07 e 08 já estavam ocupadas.
3. Aguardar a revisão e integração dos PRs pelo professor em ordem. As branches seguintes contêm os incrementos anteriores; após cada integração, conferir o diff e resolver eventuais conflitos no mesmo PR.

O enunciado exige integração da sprint anterior antes da seguinte. A preparação adiantada até a sprint 3 foi feita a pedido da equipe; a revisão externa não foi simulada. Os PRs estão disponíveis para avaliação do professor; a avaliação presencial permanece separada da entrega no repositório.

## Abrir o projeto

No Android Studio, escolha a pasta `app/` ao lado deste arquivo. No computador utilizado:

```text
C:\Users\Fernando\Projects\mobiledev\mobile-development-2026-2\projects\team-09\app
```

SDK: `C:\Android\Sdk`. Emulador preparado: `Ritmo_API_35` (Pixel 6, Android 15/API 35). O APK de desenvolvimento da versão atual também foi copiado para `output/Ritmo-sprint-03.apk` na pasta de trabalho externa ao fork.

## Reproduzir a validação

Com um dispositivo/emulador conectado, execute no PowerShell a partir da pasta da equipe:

```powershell
.\scripts\validar.ps1 -ComEmulador
```

As evidências de cada sprint correspondem à sua branch histórica. Testes aprovados não provam compreensão dos alunos; essa parte deve ser demonstrada por ambos.
