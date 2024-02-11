API Respira Aripoka

A API Respira Aripoka é uma aplicação desenvolvida em Java, utilizando o framework Spring Boot e banco de dados MySQL. Ela permite aos usuários fazerem denúncias anonimamente, fornecendo uma plataforma segura e confiável para relatar incidentes.
Funcionalidades

A API oferece as seguintes funcionalidades:

    Criação de Denúncias: Permite aos usuários criar novas denúncias de forma anônima, fornecendo detalhes sobre o incidente.

    Consulta de Denúncias por ID: Permite aos usuários consultar uma denúncia específica com base no seu ID.

    Listagem de Denúncias: Fornece uma lista paginada de todas as denúncias disponíveis para análise.

    Exclusão de Denúncias: Permite aos administradores excluir denúncias do sistema.

    Atualização de Status de Denúncias: Permite aos administradores atualizar o status de uma denúncia, indicando se foi resolvida, em andamento, etc.

    Consulta de Denúncias por Período: Permite filtrar denúncias por um período específico, proporcionando insights sobre tendências ao longo do tempo.

    Consulta de Denúncias por Tipo: Permite filtrar denúncias por tipo específico, como ambiental, social, entre outros.

    Consulta de Denúncias por Período e Tipo: Permite combinar filtros de período e tipo para obter informações mais detalhadas.

    Consulta de Denúncias por Status: Permite filtrar denúncias por status, como pendente, resolvida, etc.

    Estatísticas de Denúncias: Fornece estatísticas sobre as denúncias registradas, como total de denúncias, média de tempo para resolução, etc.

Endpoints

A API expõe os seguintes endpoints:

    POST /v1/denuncia: Cria uma nova denúncia.

    GET /v1/denuncia/{id}: Consulta uma denúncia específica pelo seu ID.

    GET /v1/denuncia/analise: Retorna uma lista paginada de todas as denúncias disponíveis.

    DELETE /v1/denuncia/gerenciar/{id}: Exclui uma denúncia com base no seu ID.

    PUT /v1/denuncia/analise/{id}: Atualiza o status de uma denúncia específica.

    GET /v1/denuncia/analise/por-periodo: Filtra denúncias por um período específico.

    GET /v1/denuncia/analise/por-tipo: Filtra denúncias por tipo específico.

    GET /v1/denuncia/analise/por-periodo-tipo: Combina filtros de período e tipo para obter denúncias específicas.

    GET /v1/denuncia/analise/por-status: Filtra denúncias por status específico.

    GET /v1/denuncia/analise/estatisticas: Retorna estatísticas sobre as denúncias registradas.

Tecnologias Utilizadas

    Java
    Spring Boot
    MySQL

Configuração de Ambiente

A API está configurada para ser executada na porta padrão 8080.
Uso

Para utilizar a API, os usuários podem enviar requisições HTTP aos endpoints fornecidos, de acordo com a funcionalidade desejada.

Exemplo de requisição para criar uma nova denúncia:


POST /v1/denuncia

    Body:
    {
      "endereço": "Rua x",
      "cordenadasGeograficas": "**** **** ****",
      ...
    }

Contribuindo

Contribuições para melhorias na API são bem-vindas! Se você encontrar problemas ou tiver sugestões de novos recursos, sinta-se à vontade para abrir uma issue ou enviar um pull request.
