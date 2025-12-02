# Game-Library:

Uma aplicação desenvolvida em **Java** que funciona como uma **biblioteca virtual de jogos**, onde o usuário pode cadastrar, visualizar e gerenciar os jogos que já jogou ou pretende jogar.

---

# Desenvolvido por:

- Lucas Caetano Reis - 1953 - GEC
- Vinícus Heringer
- Bissacot
- Eduardo Souza

---

# Visão geral:

O **Game-Library** foi criado com o objetivo de ajudar jogadores a **organizar e acompanhar sua coleção de jogos**.  
Por meio de uma API chamada RAWG, o sistema permite registrar informações sobre cada jogo, como:

- Nome  
- Plataforma  
- Data de lançamento  
- Gênero  
- Desenvolvedora e Publisher

---

# Funcionalidades:

- Cadastro de jogos

- Listagem de jogos registrados

- Consulta por título

- Atualização e exclusão de registros

---

# Tecnologias habilitadoras:

- **Java 21**
- **Maven 3.9** (gerenciamento de dependências)
- **IDE IntelliJ IDEA**  
- **JUnit5** (execução de testes unitários)
- **Mockito** (criação de testes mock da API)
- **Git / GitHub** (controle de versão)
- **Jenkins** (CI/CD)
- **RAWG** (busca de dados dos jogos)
  
---

# Estrutura do projeto:

- .github/           # Configurações de workflows e CI/CD para o Github Actions
- Game-Library/      # Diretório principal, contendo o código fonte, testes, arquivos de dependências, relatórios e um executável .JAR
- scripts/           # Scripts para notificação por e-mail para o Github Actions
- Jenkinsfile.txt    # Pipeline oficial do projeto
- README.md          # Documentação do projeto

# Como executar o projeto:

- Ter o Java JDK 21 instalado na sua máquina

- Ter uma IDE compatível (ex: IntelliJ IDEA)

- Usar o Git para clonar o repositório na sua máquina:

- git clone https://github.com/C14-2025/Game-Library.git

- Abra o projeto na sua IDE.

- Compile e execute o arquivo main.java.

- O sistema abrirá um terminal para cadastrar e visualizar os dados dos jogos.

---

# Como gerar o relatório de testes:

- Tenha o maven 3.9 instalado na sua máquina.

- Abra um prompt de comando e dê entre no nível do seu pom.xl.

- Rode os comandos:
- mvn clean install -DskipTests
- mvn test site

- Vá na pasta target, entre no diretório site e abra o arquivo summmary.html

---
