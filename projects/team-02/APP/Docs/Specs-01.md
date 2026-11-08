
> **Team:** Team 02  
> **Members:**  
   Paulo Eduardo de Leon Fernandes Alves  
   Pedro Gustavo Macena da Silva

# SPEC-001 — Produto e Primeira Tela

## 1. Contexto

A prática de atividades físicas, como calistenia e musculação, exige organização e acompanhamento para que os praticantes consigam manter uma rotina de exercícios. Entretanto, organizar treinos semanais, consultar exercícios e acompanhar períodos de atividade pode exigir o uso de diferentes ferramentas.

Diante dessa necessidade, a equipe propõe o desenvolvimento do TreinePlus, um aplicativo móvel voltado à organização de treinos e ao acompanhamento de atividades físicas. A proposta inicial é reunir, em um único ambiente, opções de modalidades, planejamento semanal de exercícios, informações sobre treinos, sugestões de alimentação e ferramentas de apoio à prática esportiva.

Nesta primeira etapa, o foco será definir a identidade do produto e desenvolver sua tela inicial, que servirá como ponto de entrada para as demais funcionalidades planejadas.

## 2. Objetivo

Definir a identidade inicial do aplicativo TreinePlus e desenvolver uma primeira tela que apresente o produto ao usuário, permitindo iniciar a navegação e acessar a seleção de modalidade esportiva.

## 3. Cenário do usuário

**Dado** que o usuário tenha instalado o aplicativo TreinePlus em seu dispositivo Android, **quando** abrir o aplicativo, **então** deverá visualizar uma tela inicial com o nome do produto, uma breve apresentação de sua proposta e uma opção para iniciar a utilização.

Ao selecionar a opção de início, o usuário deverá ser encaminhado para a tela de seleção de modalidade.

## 4. Requisitos funcionais

- **FR-01:** O aplicativo deve apresentar o nome TreinePlus em sua tela inicial.
- **FR-02:** O aplicativo deve apresentar uma breve descrição de sua finalidade.
- **FR-03:** O aplicativo deve disponibilizar um botão ou ação para iniciar a navegação.
- **FR-04:** Ao acionar a opção de início, o aplicativo deve encaminhar o usuário para a tela de seleção de modalidade.

## 5. Restrições técnicas

- O desenvolvimento deverá utilizar Kotlin.
- O aplicativo deverá ser desenvolvido para a plataforma Android.
- A interface deverá utilizar Jetpack Compose.
- O projeto deverá ser desenvolvido e gerenciado por meio do Android Studio.
- A identidade visual inicial deverá utilizar tons de verde, transmitindo uma proposta relacionada à saúde e à atividade física.
- Nesta etapa, não será necessário implementar autenticação, cadastro de usuários ou integração com serviços externos.

## 6. Fora de escopo

Não fazem parte desta primeira Sprint:

- Cadastro e autenticação de usuários.
- Sincronização de dados em nuvem.
- Pagamentos ou planos de assinatura.
- Personalização de treinos por inteligência artificial.
- Persistência de dados do usuário.
- Implementação completa das funcionalidades de treinamento e alimentação.

Essas funcionalidades poderão ser consideradas em etapas posteriores do desenvolvimento, conforme o planejamento das Sprints.

## 7. Critérios de aceitação

- **AC-01 (FR-01):** O nome TreinePlus deverá estar visível na tela inicial.
- **AC-02 (FR-02):** A tela deverá apresentar uma descrição breve sobre a finalidade do aplicativo.
- **AC-03 (FR-03):** Deverá existir uma ação visível para iniciar a utilização.
- **AC-04 (FR-04):** A ação de início deverá encaminhar o usuário para a seleção de modalidade.
- **AC-05:** O aplicativo deverá compilar e iniciar em um dispositivo ou emulador Android compatível.
- **AC-06:** Deverá ser registrada uma captura real da tela inicial para comprovação visual da implementação.

## 8. Rastreabilidade

| Requisito | Componente previsto | Critério | Evidência prevista |
|---|---|---|---|
| FR-01 | Tela inicial | AC-01 | `evidence/sprint-01/home.png` |
| FR-02 | Texto de apresentação | AC-02 | Captura da tela inicial |
| FR-03 | Botão de início | AC-03 | Captura da tela inicial |
| FR-04 | Navegação entre telas | AC-04 | Captura ou gravação do fluxo |

## 9. Plano de implementação

O desenvolvimento desta etapa deverá seguir as seguintes atividades:

1. Criar o projeto Android utilizando Kotlin e Jetpack Compose.
2. Definir a estrutura inicial de pacotes e arquivos.
3. Configurar o tema visual do aplicativo, utilizando tons de verde.
4. Desenvolver a primeira tela com o nome TreinePlus e uma descrição de sua proposta.
5. Adicionar um botão para iniciar a experiência.
6. Preparar a navegação para a futura tela de seleção de modalidade.
7. Verificar se a estrutura inicial do aplicativo está funcionando corretamente.

## 10. Plano de validação

A equipe deverá realizar os seguintes procedimentos para verificar os critérios de aceitação:

1. Executar o aplicativo em um emulador ou dispositivo Android.
2. Verificar se o nome TreinePlus está sendo apresentado corretamente.
3. Conferir a descrição e os elementos visuais da tela inicial.
4. Verificar se o botão de início está visível e responde à interação.
5. Confirmar se a navegação encaminha o usuário para a seleção de modalidade.
6. Registrar uma captura real da tela inicial e armazená-la no diretório `evidence/sprint-01/`.

Os resultados deverão ser registrados somente após a execução dos procedimentos de validação.

## 11. Resultados da validação

Esta seção deverá ser preenchida pela equipe após a implementação e execução dos testes.

Os critérios de aceitação deverão ser identificados como aprovados ou pendentes de acordo com os resultados efetivamente observados.

Não deverão ser registrados resultados positivos sem a realização dos testes correspondentes.

## 12. Riscos e observações

- A primeira versão deverá priorizar simplicidade e facilidade de navegação.
- A estrutura inicial deverá permitir a inclusão de novas telas e funcionalidades nas próximas Sprints.
- Problemas relacionados à configuração do ambiente Android ou às dependências deverão ser identificados durante a implementação.
- A equipe deverá manter a documentação atualizada conforme o desenvolvimento evoluir.

## 13. Uso de Inteligência Artificial

Ferramentas de inteligência artificial poderão ser utilizadas como apoio durante o planejamento, desenvolvimento, revisão de código e documentação do aplicativo.

A equipe deverá registrar as ferramentas utilizadas, as solicitações realizadas, as sugestões aproveitadas e as alterações feitas manualmente.

Todo código gerado ou sugerido deverá ser revisado e compreendido pelos integrantes da equipe antes de sua inclusão no projeto.

## 14. Entregáveis

Ao final desta Sprint, estão previstos os seguintes entregáveis:

- Projeto Android inicial do TreinePlus.
- Primeira tela funcional do aplicativo.
- Configuração inicial do ambiente de desenvolvimento.
- Este documento de especificação (`SPEC-001.md`).
- Relatório de desenvolvimento (`SPRINT-01.md`).
- Evidências reais da implementação, armazenadas em `evidence/sprint-01/`.

## 15. Status

**Status da especificação:** Elaborada.

**Status da implementação:** Prevista para a Sprint 01.

**Status da validação:** Pendente de execução.

A conclusão da Sprint deverá ser registrada após a implementação dos requisitos, execução dos testes e produção das evidências correspondentes.
