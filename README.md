# API Respira Aripoka

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)

Uma API em Spring Boot para envio anônimo de denúncias, com persistência em MySQL e operações para gerenciamento, filtragem e estatísticas.

---

## Índice

- [Visão Geral](#visão-geral)
- [Principais Recursos](#principais-recursos)
- [Tecnologias](#tecnologias)
- [Endpoints da API](#endpoints-da-api)
- [Exemplo de Requisição](#exemplo-de-requisição)
- [Instalação e Execução](#instalação-e-execução)
- [Variáveis de Ambiente / Configuração](#variáveis-de-ambiente--configuração)
- [Testes](#testes)
- [Contribuição](#contribuição)
- [Licença](#licença)
- [Contato](#contato)

---

## Visão Geral

A "API Respira Aripoka" permite o envio de denúncias de forma anônima e oferece ferramentas para administradores analisarem, filtrarem e obterem estatísticas sobre os casos reportados. Foi pensada para ser leve, segura e fácil de integrar com front-ends ou serviços externos.

---

## Principais Recursos

- Envio anônimo de denúncias
- CRUD para denúncias (criação, leitura, atualização de status e exclusão)
- Filtros por período, tipo e status
- Paginação nas listagens de análise
- Endpoint de estatísticas para insights rápidos
- Integração com banco MySQL usando Spring Data JPA

---

## Tecnologias

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL
- Maven
- JUnit 5 (testes)

---

## Endpoints da API

Base path: /v1/denuncia

- POST /v1/denuncia  
  Criar nova denúncia

- GET /v1/denuncia/{id}  
  Buscar denúncia por ID

- GET /v1/denuncia/analise  
  Listar denúncias para análise (paginação)

- DELETE /v1/denuncia/gerenciar/{id}  
  Remover denúncia (operacional/administrativo)

- PUT /v1/denuncia/analise/{id}  
  Atualizar status de uma denúncia

- GET /v1/denuncia/analise/por-periodo  
  Filtrar por período (ex.: dataInicial, dataFinal, página, tamanho)

- GET /v1/denuncia/analise/por-tipo  
  Filtrar por tipo de denúncia

- GET /v1/denuncia/analise/por-periodo-tipo  
  Filtrar por período + tipo

- GET /v1/denuncia/analise/por-status  
  Filtrar por status (ex.: PENDENTE, EM_ANALISE, RESOLVIDA)

- GET /v1/denuncia/analise/estatisticas  
  Retorna estatísticas resumidas (contagem por tipo/status, tendências, etc.)

Observação: os parâmetros de query (p.ex. page, size, tipo, status, dataInicial, dataFinal) seguem convenções REST e devem ser documentados conforme a implementação.

---

## Exemplo de Requisição

Exemplo de criação com curl:

```bash
curl -X POST http://localhost:8080/v1/denuncia \
  -H "Content-Type: application/json" \
  -d '{
    "endereco": "Rua Example, 123",
    "coordenadasGeograficas": "-23.0000,-46.0000",
    "tipo": "AMBIENTAL",
    "descricao": "Descrição da denúncia de exemplo"
  }'
```

Exemplo de resposta (201 Created):

```json
{
  "id": 123,
  "endereco": "Rua Example, 123",
  "coordenadasGeograficas": "-23.0000,-46.0000",
  "tipo": "AMBIENTAL",
  "descricao": "Descrição da denúncia de exemplo",
  "status": "PENDENTE",
  "dataCriacao": "2025-10-10T00:00:00Z"
}
```

---

## Instalação e Execução

Pré-requisitos:
- Java 17
- Maven
- MySQL (ou outro banco compatível configurado)

Passos:

1. Clone o repositório
```bash
git clone https://github.com/AlexandreMacedo7/apirespiraaripoka.git
cd apirespiraaripoka
```

2. Configure o banco de dados (veja seção abaixo)

3. Build e execução
```bash
mvn clean install
mvn spring-boot:run
```

A aplicação ficará disponível em: http://localhost:8080

Sugestão: usar Docker/Docker Compose para levantar um MySQL local rapidamente (ex.: imagem mysql:8.0).

---

## Variáveis de Ambiente / Configuração

Edite src/main/resources/application.properties (ou utilize variáveis de ambiente) para apontar ao seu banco:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_name?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

Recomenda-se proteger credenciais em ambientes de produção (secrets manager, variáveis de ambiente, etc).

---

## Testes

Execute a suíte de testes com:

```bash
mvn test
```

Cobertura, testes de integração e dados de teste podem ser adicionados conforme necessidade.

---

## Contribuição

Obrigado por querer contribuir! Fluxo sugerido:

1. Fork do repositório
2. Criar branch com feature ou correção: git checkout -b feature/descritivo
3. Commit com mensagens claras
4. Abrir Pull Request descrevendo a mudança

Leia também CONTRIBUTING.md (se existir) para padrões de código e testes.

---

## Licença

Projeto licenciado sob MIT. Veja o arquivo LICENSE para detalhes.

---

## Contato

Desenvolvedor: Alexandre Macedo  
Repositório: https://github.com/AlexandreMacedo7/apirespiraaripoka
