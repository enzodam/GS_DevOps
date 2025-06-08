# GS DevOps - SOS Climatech API com Docker e Oracle

Este repositório contém o projeto da Global Solution de DevOps Tools & Cloud Computing, que consiste na conteinerização de uma API Java Spring Boot (SOS Climatech) e um banco de dados Oracle XE utilizando Docker e Docker Compose.

## Objetivo

O objetivo principal é demonstrar a capacidade de criar um ambiente de desenvolvimento e teste isolado e reproduzível para a API SOS Climatech, utilizando containers Docker para a aplicação e para o banco de dados, seguindo as boas práticas de infraestrutura como código e os requisitos da disciplina.

## Tecnologias Utilizadas

*   **Java 17 & Spring Boot:** Para o desenvolvimento da API REST (SOS Climatech).
*   **Maven:** Para gerenciamento de dependências e build do projeto Java.
*   **Oracle Database Express Edition (XE) 21c:** Banco de dados relacional utilizado para persistência dos dados.
*   **Docker:** Plataforma de conteinerização para empacotar a API e o banco de dados.
*   **Docker Compose:** Ferramenta para definir e orquestrar aplicações Docker multi-container.

## Estrutura do Repositório

```
/
├── Dockerfile.app             # Dockerfile para construir a imagem da API Java
├── docker-compose.yml         # Arquivo Docker Compose para orquestrar os containers
├── Dockerfile.oracle          # Dockerfile (opcional/backup) para o Banco Oracle
├── payload_create.json        # Exemplo de payload para criar uma Localidade (POST)
├── payload_update.json        # Exemplo de payload para atualizar uma Localidade (PUT)
├── run_crud_tests.sh          # Script para executar testes CRUD básicos via cURL
└── sos-climatech-api-main/    # Código-fonte da API Java Spring Boot
    ├── pom.xml
    └── src/
```

## Destaques da Implementação

*   **Container da Aplicação (`Dockerfile.app`):**
    *   Construído via Dockerfile personalizado.
    *   Utiliza multi-stage build para otimização.
    *   Define um diretório de trabalho (`WORKDIR /app`).
    *   **Executa com usuário não-root (`appuser`)** para maior segurança.
    *   Expõe a porta da aplicação (8080).
    *   Utiliza variáveis de ambiente definidas no `docker-compose.yml` para configuração.
*   **Container do Banco de Dados (`docker-compose.yml`):**
    *   Utiliza imagem oficial do Oracle XE (`container-registry.oracle.com/database/express:21.3.0-xe`), conforme permitido pelos requisitos.
    *   Configura um **volume nomeado (`oracle_data`)** para persistência dos dados.
    *   Define pelo menos uma **variável de ambiente** (`ORACLE_PWD`, `CUSTOM_DB_INFO`).
    *   **Expõe a porta** do banco (1521).
*   **Geral:**
    *   Ambos os containers são executados em **background** (`-d`).
    *   A comunicação entre containers é feita via rede Docker (`sos-network`).
    *   O `docker-compose.yml` inclui `healthcheck` para o banco e `depends_on` para garantir a ordem de inicialização.
    *   As evidências (logs, testes) devem ser obtidas via **terminal**, sem interface gráfica.

**Observação sobre `Dockerfile.oracle`:**

Conforme confirmado pelos slides oficiais, o Dockerfile personalizado é obrigatório apenas para a aplicação. O banco de dados pode usar imagem oficial. O arquivo `Dockerfile.oracle` é mantido neste repositório como registro da análise inicial e para demonstrar a capacidade de criá-lo, caso fosse estritamente necessário.

## Pré-requisitos

*   Docker instalado (Docker Desktop para Windows/Mac ou Docker Engine + Docker Compose para Linux).
*   Acesso à internet (para baixar as imagens Docker).
*   Um terminal (Prompt de Comando, PowerShell, Bash, etc.).
*   `curl` instalado (para executar o script `run_crud_tests.sh`). O Git Bash no Windows geralmente inclui o `curl`.

## Como Executar

1.  **Clone o repositório:**
    ```bash
    git clone <URL_DO_SEU_REPOSITORIO>
    cd <NOME_DA_PASTA_DO_REPOSITORIO>
    ```

2.  **Verifique o Docker Desktop (Importante!):**
    *   Antes de executar os comandos Docker, **certifique-se de que o Docker Desktop está aberto e rodando**. Procure o ícone da baleia 🐳 na barra de tarefas (geralmente perto do relógio). Ele deve estar visível e com status "verde" (rodando).
    *   Se não estiver rodando, inicie o Docker Desktop pelo Menu Iniciar.
    *   Se estiver com problemas (ícone vermelho/amarelo ou erros de conexão como `Cannot connect to the Docker daemon`), tente clicar com o botão direito no ícone da baleia e selecionar "Restart". Aguarde ele ficar verde novamente.

3.  **Construa as imagens e inicie os containers:**
    Abra um terminal na pasta raiz do projeto (onde está o `docker-compose.yml`) e execute:
    ```bash
    # No Linux/Mac (pode precisar de sudo)
    sudo docker compose up -d --build

    # No Windows (sem sudo)
    docker compose up -d --build
    ```
    *   O comando `-d` executa os containers em background.
    *   O comando `--build` força a reconstrução da imagem da API.
    *   **Aguarde!** O download da imagem Oracle e a inicialização do banco podem levar alguns minutos. O container da API só iniciará após o banco estar saudável (`healthy`).

4.  **Verifique o status dos containers (via terminal):**
    ```bash
    # No Linux/Mac (pode precisar de sudo)
    sudo docker compose ps

    # No Windows (sem sudo)
    docker compose ps
    ```

5.  **Acesse a API (Swagger UI):**
    Abra seu navegador e acesse: `http://localhost:8080/swagger-ui.html`

6.  **Execute os testes CRUD via script (Opcional - via terminal):**
    Se você tiver `curl` e `bash` (ou Git Bash no Windows), pode executar:
    ```bash
    bash run_crud_tests.sh
    ```
    A saída detalhada dos testes será salva no arquivo `crud_test_output.txt`.

7.  **Verifique os logs (via terminal):**
    ```bash
    # Logs da API
    docker compose logs sos-api

    # Logs do Banco Oracle
    docker compose logs oracle-db
    ```

8.  **Pare e remova os containers (via terminal):**
    Quando terminar, execute:
    ```bash
    docker compose down
    ```

## Vídeo Demonstrativo



## 👨‍💻 Desenvolvedores

| Nome                          | RM      | GitHub |
|-------------------------------|---------|--------|
| Enzo Dias Alfaia Mendes       | 558438  | [@enzodam](https://github.com/enzodam) |
| Matheus Henrique Germano Reis | 555861  | [@MatheusReis48](https://github.com/MatheusReis48) |
| Luan Dantas dos Santos        | 559004  | [@lds2125](https://github.com/lds2125) |



