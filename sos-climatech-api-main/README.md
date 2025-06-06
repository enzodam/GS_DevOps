# SOS ClimaTech API 🌪️

## Projeto: Cadastro e Gestão de Respostas a Eventos Climáticos

Bem-vindo à API SOS ClimaTech! Esta aplicação robusta, desenvolvida com **Java 17 e Spring Boot 3.x**, oferece uma solução centralizada para o **cadastro e gerenciamento de informações cruciais durante eventos climáticos extremos**, como enchentes, deslizamentos ou secas.

O objetivo principal é fornecer uma ferramenta eficaz para **prefeituras, ONGs e equipes de defesa civil**, permitindo um registro ágil e organizado de:

*   **Eventos Climáticos:** Detalhes sobre o tipo, localidade e data do desastre.
*   **Pessoas Afetadas:** Informações essenciais dos cidadãos impactados.
*   **Recursos Necessários:** Mapeamento de suprimentos e ajuda requerida.
*   **Registros de Ajuda:** Controle da distribuição de recursos e assistência.

Através de uma **API RESTful bem estruturada**, seguindo as melhores práticas de desenvolvimento e princípios SOLID, o SOS ClimaTech visa otimizar a resposta a emergências e facilitar a coordenação de ajuda humanitária.

---

## 🎯 Funcionalidades Principais

O SOS ClimaTech oferece um conjunto completo de operações CRUD (Criar, Ler, Atualizar, Deletar) para as seguintes entidades:

*   **Eventos Climáticos:** `/api/eventos`
*   **Pessoas Afetadas:** `/api/pessoas`
*   **Localidades:** `/api/localidades`
*   **Recursos:** `/api/recursos`
*   **Registros de Ajuda:** `/api/registros-ajuda`

Além disso, funcionalidades chave incluem:

*   **Relacionamentos Claros:** Mapeamento entre eventos, pessoas, localidades e recursos.
*   **Validação de Dados:** Uso de Jakarta Bean Validation para garantir a consistência das informações.
*   **Tratamento de Erros:** Exceções personalizadas e respostas padronizadas para uma melhor experiência do desenvolvedor.
*   **Documentação Interativa:** Geração automática de documentação via **Swagger (OpenAPI)** para fácil exploração e teste dos endpoints.

---

## ✨ Proposta Inovadora: Status de Assistência Detalhado

Como diferencial, a entidade `PessoaAfetada` incorpora um campo `statusAssistência`, permitindo um acompanhamento granular do ciclo de atendimento ao cidadão impactado. Os status possíveis são:

*   `REGISTRADO`: Cadastro inicial, aguardando triagem.
*   `EM_ASSISTENCIA`: Cidadão recebendo suporte ativo.
*   `ASSISTENCIA_CONCLUIDA`: Necessidades emergenciais supridas.
*   `NECESSITA_ACOMPANHAMENTO`: Indica a necessidade de suporte pós-emergencial.

Este status pode ser atualizado através de um endpoint dedicado (`PUT /api/pessoas/{id}/status?status=NOVO_STATUS`), fornecendo às equipes de campo uma visão clara do progresso da assistência.

---

## 🛠️ Tecnologias e Ferramentas

*   **Linguagem:** Java 17
*   **Framework Principal:** Spring Boot 3.2.6
*   **Persistência:** Spring Data JPA / Hibernate
*   **Banco de Dados:** Oracle (Conectado à instância da FIAP)
*   **Validação:** Jakarta Bean Validation
*   **Documentação API:** Springdoc OpenAPI v2.5.0 (Swagger UI)
*   **Build e Gerenciamento:** Apache Maven
*   **Containerização (Opcional/Deploy):** Docker
*   **Plataforma de Deploy:** Render

---

## 🚀 Executando o Projeto Localmente

**Pré-requisitos:**

*   JDK 17 ou superior instalado e configurado.
*   Apache Maven instalado e configurado.

**Passos:**

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/enzodam/sos-climatech-api
    cd sos-climatech-api-main
    ```
2.  **Verifique as Credenciais:** Certifique-se de que o arquivo `src/main/resources/application.properties` contém as credenciais corretas para o banco de dados Oracle da FIAP (`spring.datasource.username` e `spring.datasource.password`).
3.  **Compile e Execute com Maven:**
    ```bash
    mvn spring-boot:run
    ```
4.  **Acesso:** A API estará rodando em `http://localhost:8080`. Acesse a documentação Swagger em `http://localhost:8080/swagger-ui.html` para interagir com os endpoints.

---

## 💻 Usando a API no Render (Online)

Você pode interagir e testar a API diretamente no ambiente online do Render:

1.  **Acesse o Swagger UI:** A forma mais fácil de usar a API é através da documentação interativa Swagger. Abra o seguinte link no seu navegador, substituindo o placeholder pela URL real da sua aplicação no Render:
    ```
    https://sos-climatech-api.onrender.com/swagger-ui/index.html
    ```
---

## 🔗 Links Importantes

*   **Repositório GitHub:** `https://github.com/enzodam/sos-climatech-api`

---

## 🎬 Vídeos de Entrega:

1.  **Vídeo Demonstração:** FALAFLLAFLAFLFLALTA ISSOSOSO
2.  **Vídeo Pitch:** FALTA ISOSOSOSOSISO

---

## 👨‍💻 Desenvolvedores

| Nome                          | RM      | GitHub |
|-------------------------------|---------|--------|
| Enzo Dias Alfaia Mendes       | 558438  | [@enzodam](https://github.com/enzodam) |
| Matheus Henrique Germano Reis | 555861  | [@MatheusReis48](https://github.com/MatheusReis48) |
| Luan Dantas dos Santos        | 559004  | [@lds2125](https://github.com/lds2125) |

