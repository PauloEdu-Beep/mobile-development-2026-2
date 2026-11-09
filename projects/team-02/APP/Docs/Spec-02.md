
**Team:** Team 02  
**Members:**  
- Paulo Eduardo de Leon Fernandes Alves
- Pedro Gustavo Macena da Silva

# SPEC-002 — Estado e Interação do Usuário

## 1. Contexto

Após acessar o aplicativo TreinePlus, o usuário deverá ter a possibilidade de indicar qual modalidade de atividade física pretende praticar. Inicialmente, o aplicativo será direcionado a duas modalidades: calistenia e musculação (academia).

Além da escolha da modalidade, será necessário permitir que o usuário selecione uma área do corpo que deseja priorizar em seus treinamentos. Essa seleção ajudará a organizar a experiência de utilização e servirá como referência para as próximas etapas do aplicativo.

Dessa forma, esta Sprint tem como foco desenvolver os primeiros recursos de interação do TreinePlus, utilizando elementos visuais que permitam ao usuário identificar suas escolhas e avançar pelas etapas de configuração inicial.

## 2. Objetivo

Implementar mecanismos de interação que permitam ao usuário selecionar uma modalidade de atividade física e uma área de foco, apresentando respostas visuais às escolhas realizadas e possibilitando a continuidade da navegação no aplicativo.

## 3. Cenário do usuário

**Dado** que o usuário tenha iniciado o aplicativo e esteja na tela de seleção de modalidade, **quando** escolher entre calistenia e academia, **então** a opção selecionada deverá receber um destaque visual.

Após essa escolha, o usuário poderá avançar para a seleção de área de foco. Nessa etapa, deverá visualizar as opções disponíveis, selecionar uma delas e continuar para a próxima etapa do aplicativo, correspondente à programação semanal de treinos.

## 4. Requisitos funcionais

- **FR-01:** O aplicativo deve apresentar as opções de modalidade Calistenia e Academia.
- **FR-02:** O aplicativo deve permitir que o usuário selecione uma modalidade e apresentar um destaque visual para a opção escolhida.
- **FR-03:** O aplicativo deve apresentar as seguintes áreas de foco: Todo o corpo, Abdômen, Peito, Braço, Perna e Costas.
- **FR-04:** O aplicativo deve permitir a seleção de uma área de foco.
- **FR-05:** O aplicativo deve apresentar uma ação para continuar após a seleção da modalidade e da área de foco.
- **FR-06:** Após a seleção da área de foco e a confirmação da ação de continuidade, o aplicativo deve encaminhar o usuário para a programação semanal de treinos.

## 5. Restrições técnicas

- O desenvolvimento deverá utilizar Kotlin e Jetpack Compose.
- O controle das seleções deverá utilizar os mecanismos de gerenciamento de estado disponíveis no Compose, como `remember` e `mutableStateOf`.
- Os componentes de interface deverão permitir a identificação visual das opções selecionadas.
- A navegação deverá seguir a estrutura definida para o aplicativo.
- As seleções deverão ser mantidas durante a etapa atual de navegação, conforme necessário para o funcionamento da interface.
- Não será necessária a implementação de autenticação ou integração com serviços externos nesta Sprint.

## 6. Fora de escopo

Não fazem parte desta Sprint:

- Cadastro ou autenticação de usuários.
- Armazenamento permanente das preferências selecionadas.
- Sincronização das escolhas em nuvem.
- Personalização avançada de exercícios.
- Geração automática de treinos com inteligência artificial.
- Histórico de modalidades ou áreas de foco selecionadas.

Essas funcionalidades poderão ser consideradas em etapas posteriores, conforme a evolução do aplicativo.

## 7. Critérios de aceitação

- **AC-01 (FR-01):** As opções Calistenia e Academia deverão estar visíveis na tela de seleção de modalidade.
- **AC-02 (FR-02):** Ao selecionar uma modalidade, a interface deverá apresentar um destaque visual correspondente à opção escolhida.
- **AC-03 (FR-03):** A tela de seleção de foco deverá apresentar as seis áreas disponíveis.
- **AC-04 (FR-04):** O usuário deverá conseguir selecionar uma das áreas de foco, com atualização visual da opção escolhida.
- **AC-05 (FR-05):** A interface deverá disponibilizar uma ação para continuar após as seleções.
- **AC-06 (FR-06):** Após confirmar a seleção, o usuário deverá ser encaminhado para a programação semanal de treinos.
- **AC-07:** A navegação entre as telas deverá ocorrer sem encerramentos inesperados do aplicativo.
- **AC-08:** Deverão ser registradas capturas reais das telas de seleção de modalidade e de área de foco.

## 8. Rastreabilidade

| Requisito | Componente previsto | Critério | Evidência prevista |
|---|---|---|---|
| FR-01 | Tela de seleção de modalidade | AC-01 | `evidence/sprint-02/modality-selection.png` |
| FR-02 | Estado e destaque da modalidade | AC-02 | Captura da modalidade selecionada |
| FR-03 | Tela de seleção de foco | AC-03 | `evidence/sprint-02/focus-selection.png` |
| FR-04 | Estado e destaque da área de foco | AC-04 | Captura da área selecionada |
| FR-05 | Botão de continuidade | AC-05 | Captura da interface |
| FR-06 | Navegação para programação semanal | AC-06 | Captura ou gravação do fluxo |

## 9. Plano de implementação

O desenvolvimento desta etapa deverá seguir as seguintes atividades:

1. Desenvolver a tela de seleção de modalidade, apresentando as opções Calistenia e Academia.
2. Implementar o controle de estado para identificar a modalidade selecionada.
3. Criar um destaque visual para a opção escolhida.
4. Desenvolver a tela de seleção de área de foco.
5. Apresentar as seis opções de áreas disponíveis.
6. Implementar o controle de estado para identificar a área selecionada.
7. Adicionar uma ação para continuar a navegação.
8. Configurar a navegação para a tela de programação semanal.
9. Revisar o comportamento das seleções e a continuidade entre as telas.

## 10. Plano de validação

A equipe deverá realizar os seguintes procedimentos para verificar os critérios de aceitação:

1. Executar o aplicativo em um dispositivo ou emulador Android.
2. Acessar a tela de seleção de modalidade.
3. Verificar se as opções Calistenia e Academia estão disponíveis.
4. Selecionar cada modalidade e observar se o destaque visual é atualizado corretamente.
5. Avançar para a tela de seleção de área de foco.
6. Conferir se as seis áreas estão sendo exibidas.
7. Selecionar diferentes áreas e verificar a atualização visual da interface.
8. Acionar a opção de continuidade.
9. Confirmar se o aplicativo encaminha o usuário para a programação semanal.
10. Verificar se não ocorrem encerramentos inesperados durante a navegação.
11. Registrar capturas reais das telas e armazená-las no diretório `evidence/sprint-02/`.

Os resultados deverão ser registrados somente após a execução dos procedimentos de validação.

## 11. Resultados da validação

Esta seção deverá ser preenchida pela equipe após a implementação e execução dos testes.

Os critérios de aceitação deverão ser identificados como aprovados ou pendentes, de acordo com os resultados efetivamente observados.

Não deverão ser registrados resultados positivos sem a realização dos testes correspondentes.

## 12. Riscos e observações

- A seleção de modalidade e área de foco deverá apresentar respostas visuais claras para evitar dúvidas durante a utilização.
- O gerenciamento de estado deverá ser organizado para evitar inconsistências entre as opções selecionadas e os elementos exibidos na interface.
- A navegação deverá permitir a continuidade do fluxo sem perda indevida das informações selecionadas.
- A estrutura desenvolvida deverá permitir futuras melhorias, como o uso das preferências para organizar os treinos apresentados ao usuário.
- A equipe deverá verificar se as informações selecionadas precisam ser transmitidas entre as telas para utilização nas próximas etapas.

## 13. Uso de Inteligência Artificial

Ferramentas de inteligência artificial poderão ser utilizadas como apoio durante o planejamento, desenvolvimento, revisão de código e documentação do aplicativo.

A equipe deverá registrar as ferramentas utilizadas, as solicitações realizadas, as sugestões aproveitadas e as alterações feitas manualmente.

Todo código gerado ou sugerido deverá ser revisado e compreendido pelos integrantes da equipe antes de sua inclusão no projeto.

## 14. Entregáveis

Ao final desta Sprint, estão previstos os seguintes entregáveis:

- Telas de seleção de modalidade e área de foco.
- Implementação do gerenciamento de estado das seleções.
- Destaques visuais para as opções selecionadas.
- Navegação para a programação semanal de treinos.
- Este documento de especificação (`SPEC-002.md`).
- Relatório de desenvolvimento (`SPRINT-02.md`).
- Evidências reais da implementação, armazenadas em `evidence/sprint-02/`.

## 15. Status

**Status da especificação:** Elaborada.

**Status da implementação:** Prevista para a Sprint 02.

**Status da validação:** Pendente de execução.

A conclusão da Sprint deverá ser registrada após a implementação dos requisitos, execução dos testes e produção das evidências correspondentes.
