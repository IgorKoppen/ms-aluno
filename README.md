# ms-aluno

## Descrição

O projeto **ms-aluno** é um microsserviço para gerenciamento de informações de alunos. Ele fornece uma API RESTful para criar, buscar, atualizar e deletar registros de alunos em um banco de dados.

## Estrutura do Projeto

- **controller**: Contém a classe `AlunoController`, responsável por gerenciar as requisições HTTP e direcioná-las para o serviço apropriado.
- **dto**: Contém a classe `AlunoDTO`, responsável por transferir dados entre as camadas do aplicativo.
- **exception**: Contém classes para tratamento de exceções, como `DatabaseException` e `ResourceNotFoundException`.
- **model**: Inclui classes que representam o domínio, como `Aluno`, e enumerações, como `Status`.
- **repository**: Contém a interface `AlunoRepository`, que estende `JpaRepository` para gerenciamento de persistência.
- **service**: Inclui a classe `AlunoService`, responsável pela lógica de negócios.

## Funcionalidades

- **Inserir Aluno**: Adiciona um novo aluno.
- **Buscar Aluno por ID**: Retorna informações de um aluno específico pelo ID.
- **Listar Todos os Alunos**: Retorna uma lista de todos os alunos.
- **Atualizar Aluno**: Atualiza os dados de um aluno existente.
- **Deletar Aluno**: Remove um aluno do sistema.

## Tecnologias Utilizadas

- Java
- Spring Boot
- JPA/Hibernate
- Banco de Dados (H2, PostgreSQL, etc., dependendo da configuração)

## Como executar

1. Clone o repositório.
2. Certifique-se de ter o Java e o Maven instalados.
3. Execute o comando `mvn spring-boot:run` no diretório raiz do projeto.
4. Acesse a API em `http://localhost:8080/alunos`.

## Endpoints

- `POST /alunos` : Cria um novo aluno.
- `GET /alunos/{id}` : Retorna o aluno com o ID especificado.
- `GET /alunos` : Retorna todos os alunos.
- `PUT /alunos/{id}` : Atualiza o aluno com o ID especificado.
- `DELETE /alunos/{id}` : Remove o aluno com o ID especificado.

## Tratamento de Erros

O projeto possui um mecanismo de tratamento de erros para gerenciar exceções comuns, retornando mensagens apropriadas e códigos HTTP.

- `ResourceNotFoundException`: Retorna 404 quando um recurso não é encontrado.
- `DatabaseException`: Retorna 409 em caso de violações de integridade de dados.
- `MethodArgumentNotValidException`: Retorna 422 para dados de entrada inválidos.
