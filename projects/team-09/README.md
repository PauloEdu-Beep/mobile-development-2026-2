# Ritmo | Team 09

**Estude no seu ritmo. Uma sessão de cada vez.**

[Entregas e Pull Requests](ENTREGAS.md) · [Guia de apresentação](docs/GUIA-DE-APRESENTACAO.md)

Aplicativo nativo Android para estudantes que precisam transformar o tempo disponível em um plano simples de estudo. Desenvolvido para FALECT-CC-040, UNEMAT/AIA, 2026.2, professor Breno Felix de Sousa.

## Equipe

| Integrante | Matrícula |
| --- | --- |
| Fernando Henrique Cobianchi | 20220059497 |
| João Victor R. Peres | 20230079303 |

Responsável pelo envio: Fernando, conta GitHub `botist`. A equipe escolheu o número 09 nesta preparação porque as pastas 07 e 08 já pertenciam a outros grupos; a associação administrativa deve ser conferida com o professor.

## Abrir e executar

1. Abra a pasta `app/` (que contém `settings.gradle.kts`) no Android Studio.
2. Instale SDK Platform 35, Build Tools 35.0.0 e Platform Tools pelo SDK Manager.
3. Use JDK 17 ou 21 e aguarde o Gradle Sync. O wrapper baixa o Gradle 8.11.1.
4. Execute o módulo `app` em dispositivo API 24+; a validação usa Pixel 6 virtual, Android 15/API 35.

No PowerShell, a partir de `app/`:

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat connectedDebugAndroidTest
.\gradlew.bat lintDebug
```

`local.properties` é específico da máquina e não é versionado. Neste computador, o SDK está em `C:\Android\Sdk`.

## Incrementos

| Branch | Entrega |
| --- | --- |
| `team-09/sprint-00` | Ambiente e aplicação mínima |
| `team-09/sprint-01` | Produto, SPEC-001 e tela inicial |
| `team-09/sprint-02` | SPEC-002 e escolha reativa de duração |
| `team-09/sprint-03` | SPEC-003 e navegação para o plano |

Os incrementos foram preparados em sequência localmente, antes da revisão do professor. Por isso as branches posteriores incluem os commits anteriores. A regra oficial de integração da sprint anterior ainda depende do professor; nenhum merge será feito pela equipe. Os PRs devem permanecer em rascunho até revisão humana do código e da documentação.

## Escopo

Até a sprint 3: apresentação, seleção de 15/25/45 minutos, resumo reativo e plano de sessão com retorno. Não há cronômetro, cadastro, login, banco, histórico, notificações ou rede. O plano orienta uma sessão; não executa contagem de tempo.

## Uso de IA e aprendizagem

O Codex auxiliou na especificação, implementação, configuração, testes e documentação. As verificações executadas pela ferramenta não substituem a leitura e a demonstração pelos dois alunos. Não se afirma que houve revisão humana ou compreensão individual sem essa confirmação. Antes de apresentar, cada integrante deve executar o app e explicar os requisitos e funções correspondentes.

## Referências

Para revisar e apresentar, consulte o [guia de apresentação](docs/GUIA-DE-APRESENTACAO.md). O script `scripts/validar.ps1 -ComEmulador` executa build, lint e os testes com um dispositivo conectado.

As SPECs e evidências de sprints anteriores registram suas respectivas branches. Na sprint 3, a ação principal passou a navegar e o estado da sprint 2 foi elevado a `RitmoApp`. A entrega atual usa a branch `team-09/sprint-03`.

- [Repositório da disciplina](https://github.com/brenofeliix/mobile-development-2026-2)
- PDF fornecido: `Sprint_01_Compose_BCN_Move.pdf`, especialmente slides 5–10 e 23–28. BCN Move é exemplo didático; Ritmo é o produto escolhido pela equipe.
- [Estado em Compose](https://developer.android.com/develop/ui/compose/state)
- [Navigation Compose](https://developer.android.com/develop/ui/compose/navigation)
