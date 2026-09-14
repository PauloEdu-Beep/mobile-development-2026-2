# SPEC-001 — Tela de Login e Apresentação Institucional do SARC


## 1. Context

Na Escola Estadual Cívico-Militar Maria de Lima Cadidé, os professores em sala de aula precisam de um meio de comunicação ágil e confiável para solicitar apoio da Coordenação Militar. A tela inicial funciona como a porta de entrada institucional e acesso ao sistema, apresentando a identidade da escola, campos de login e caminhos para solicitação de acesso a novos usuários.

## 2. Task

Desenvolver a interface gráfica da tela inicial de Login e Acesso do aplicativo SARC utilizando Kotlin e Jetpack Compose com Material Design 3. A tela deve apresentar a identidade visual da Escola Cadidé, campos de entrada para E-mail e Senha, botão de ação primária destacado ("Entrar"), botão secundário informativo ("Sobre o SARC") com o resumo das diretrizes e prioridades da escola, e um botão de acesso ao cadastro ("Solicitar Cadastro"), preparando a interface para o fluxo de novos usuários nas sprints seguintes.

## 3. Functional Requirements

- **FR-01 —** Exibir no cabeçalho o nome SARC, subtítulo da Escola Cadidé e slogan institucional.

- **FR-02 —** Disponibilizar campos de texto para inserção de E-mail e Senha com rótulos visíveis e com visualização protegida de caracteres para a senha.

- **FR-03 —** Fornecer botão de ação primária ("Entrar") destacado para autenticação do usuário.

- **FR-04 —** Disponibilizar botão informativo ("Sobre o SARC") que aciona uma caixa de diálogo modal (`AlertDialog`) exibindo as diretrizes de uso e o resumo visual das 4 faixas de prioridade escolar.

- **FR-05 —** Fornecer botão de acesso ("Solicitar Cadastro") que direcione para a criação de novos usuários com aprovação do gestor.

## 4. Constraints

- **Tecnologias:** Kotlin 2.x, Jetpack Compose com Material Design 3.
- **Versão do Android:** `minSdk = 24`, `compileSdk = 37`, `targetSdk = 37`.
- **Isolamento de Diretório:** Alterações restritas exclusivamente à pasta `projects/team-05/`.
- **Restrição de Escopo:** O foco desta sprint é a interface visual de Login; a segunda tela de cadastro e a validação com banco de dados/servidor serão implementadas nas próximas sprints.

## 5. Acceptance Criteria

- **AC-01 —** O cabeçalho com o nome SARC, identificação da Escola Cadidé e slogan institucional estão visíveis no topo da tela.

- **AC-02 —** Os campos de entrada de E-mail e Senha estão visíveis e permitem digitação interativa do usuário.

- **AC-03 —** O botão primário ("Entrar") possui destaque visual, contraste adequado e responde ao toque.

- **AC-04 —** O botão ("Sobre o SARC") está visível na interface e permite consultar, por meio de diálogo informativo, as orientações e o resumo visual das prioridades da escola.

- **AC-05 —** O botão ("Solicitar Cadastro") está presente na tela para novos usuários.

- **AC-06 —** O layout utiliza Column, Spacer e Modifier sem sobreposição de componentes ou cortes na tela.

- **AC-07 —** O projeto compila no Android Studio / Gradle sem erros e executa no emulador/dispositivo sem travamentos.

## 6. Validation

1. Compilar o projeto no Gradle;
2. Executar no emulador AVD ou celular físico; 
3. Digitar nos campos de E-mail e Senha;
4. Testar o clique no botão 'Entrar', 'Sobre o SARC' e 'Solicitar Cadastro';
5. Tirar screenshot e salvar em `projects/team-05/evidence/sprint-01/first-screen.png`.

## 7. Requirement Traceability

| Requirement | Implemented In | Acceptance Criterion | Evidence |
| --- | --- | --- | --- |
| FR-01 | `WelcomeScreen.kt` (Header Column, Image, Text) | AC-01 | `first-screen.png` |
| FR-02 | `WelcomeScreen.kt` (OutlinedTextFields email e password) | AC-02 | `first-screen-interactive.png` |
| FR-03 | `WelcomeScreen.kt` (Button "Entrar") | AC-03 | `first-screen.png` |
| FR-04 | `WelcomeScreen.kt` (OutlinedButton "Sobre o SARC" + AlertDialog) | AC-04 | `first-screen-about-dialog.png` |
| FR-05 | `WelcomeScreen.kt` (TextButton "Solicitar Cadastro" + AlertDialog) | AC-05 | `first-screen-register-dialog.png` |

## 8. Validation Results

| Acceptance Criterion | Result | Notes                                                                                                 |
| -------------------- | ------ | ----------------------------------------------------------------------------------------------------- |
| AC-01                | PASS   | Cabeçalho visível com brasão oficial, título SARC, escola e slogan.                                   |
| AC-02                | PASS   | Campos de E-mail e Senha funcionais com digitação e máscara de senha.                                 |
| AC-03                | PASS   | Botão ("Entrar") destacado com alto contraste e clique funcional.                                     |
| AC-04                | PASS   | Botão ("Sobre o SARC") abre modal com informações gerais do SARC e as 4 faixas de prioridade escolar. |
| AC-05                | PASS   | Botão ("Solicitar Cadastro") visível e abre modal informativo que vai ir para outras sprints.         |
| AC-06                | PASS   | Layout estruturado com Column e Spacer, sem sobreposição ou corte de componentes na tela              |
| AC-07                | PASS   | Compilação com Gradle OK (`./gradlew assembleDebug`) e execução sem erros no emulador                 |

### Environment Used for Validation

- **Device:** Android Studio Emulator Pixel 8
- **Android version / API:** API 37
- **Build result:** PASS (`./gradlew assembleDebug` - BUILD SUCCESSFUL)
- **Application execution:** PASS (Execução contínua sem crash)

## 9. AI Usage

### Summary Table

| Item                           | Team response                                                                                                             |
| ------------------------------ | ------------------------------------------------------------------------------------------------------------------------- |
| LLM/tool used                  | Google Antigravity (Gemini).                                                                                              |
| Task supported by the LLM      | Estruturação finicial da especificação SPEC-001 adaptada para tela de Login.                                              |
| Main suggestion received       | Formulação técnica dos requisitos funcionais (FR-01 a FR-05) e critérios de aceitação mensuráveis (AC-01 a AC-07).        |
| What the team changed manually | Definição da interface de login, inclusão do botão de sobre/prioridades da Escola Cadidé e botão para solicitar cadastro. |
| How the result was validated   | Validação visual e funcional dos requisitos no emulador Android Studio Pixel 8.                                           |

### Tool
Google Antigravity (Gemini).

### Purpose
Foi usada como auxiliadora e orientadora na estruturação formal da especificação SPEC-001 adaptada para tela de Login institucional com botão informativo e botão de cadastro.

### Generated Content
A IA sugeriu o rascunho estrutural da especificação formal (SPEC-001) com base no template oficial da disciplina, além de propor a formulação técnica dos requisitos funcionais (FR-01 a FR-05) e critérios de aceitação (AC-01 a AC-07) para a tela de login. A equipe ajustou e refinou os requisitos, definindo componentes Material 3 e os pontos de acesso aos botões informativos ("Sobre o SARC" em diálogo modal) e de novo acesso ("Solicitar Cadastro").

### Human Changes
Definição e ideia da primeira tela ser a tela de Login com campos de E-mail e Senha, inclusão de botão informativo do SARC para os usuários saberem sobre o aplicativo e botão para solicitação de cadastro com aprovação da gestão escolar para inclusão de novos usuários e revisão e aprimoramento do rascunho estrutural da especificação formal (SPEC-001).

---

## 10. Out of Scope

- A tela secundária de cadastro funcional, banco de dados Room/SQLite, validação de login no servidor remoto e painel administrativo do gestor.

---

## 11. Deliverables

- `projects/team-05/SPRINT-01.md` — Documento de Definição de Produto e objetivos da Sprint;
- `projects/team-05/docs/specs/SPEC-001.md` — Arquivo de especificação formal da tela inicial;
- `projects/team-05/app/` — Código-fonte da tela implementada em Kotlin e Jetpack Compose;
- `projects/team-05/evidence/sprint-01/first-screen.png` — Screenshot da tela em execução (padrão do curso).

---

## 12. Status

- [x] Specification completed
- [x] Implementation completed
- [x] Acceptance criteria validated
- [x] Code reviewed
- [x] Commit created
- [x] Pull Request submitted
