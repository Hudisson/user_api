# API de Autenticação e Usuários

### Descrição
Este projeto oferece uma API REST para autenticação e gerenciamento de usuários, incluindo login, registro, edição e exclusão de usuários. A API foi construída usando **Spring Boot 3.5.9**, **Spring Security**, **JWT (JSON Web Tokens)**, **JPA/Hibernate** e **MySQL**.
___

## **Tecnologias Utilizadas**

- **Java 21**
    
- **Spring Boot 3.5.9**
    
- **Spring Security**
    
- **JPA/Hibernate**
    
- **JWT (jsonwebtoken 0.12.3)**
    
- **MySQL**
    
- **Lombok**
    
- **BCrypt para criptografia de senha**

- **Docker e docker-composer**
___

## Instalação

#### Requisitos

- Docker e docker-compose

- Java 21
    
- MySQL (ou outro banco de dados de sua escolha)
    
- Maven (para construção do projeto)

**OBS.:** 
A) Se desejar rodar apenas na sua máquina (host) só precisará do 
    **Java 21, MySQL 8 (ou outro banco de dados de sua escolha) e Maven (para construção do projeto) e cliente de banco de dados (ex.: PHPMyAdmin, MySQL Workbench etc)**
    <br>
B) Se deseja rodar diretamente em um container Docker só precisrá do **Docker e docker-compose**
___

### Passos para execução:

1. **Clone o repositório**:
```bash
git clone https://github.com/Hudisson/user_api
cd user-api
```
2. **Configuração do banco de dados**:

  Configure o banco de dados **MySQL** (ou outro de sua escolha) no arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/usuarios_api?useTimezone=true&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=user_db
spring.datasource.password=password_db
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

3. **Rodando o projeto localmente (sem Docker)**:

Execute o projeto utilizando Maven:
```bash
    mvn spring-boot:run
```


### Execução com Docker

Acesse o diretório do projeto e execulte o arquivo docker-compose-yml:
```bash
   docker-compose up -d --build
```
___

### Rotas da API

#### Autenticação

1) #### Rota para registrar um novo usuário. 
   **POST /auth/register**


`http://localhost:8080/auth/register`

- **Request Body** (JSON):
```json
{
    "name": "Nome do Usuário",
    "email": "email@example.com",
    "password": "senha123"
}
 ```
- **Response** (Status: 201 Created):
```json
{
    "id": "UUID-do-usuario",
    "name": "Nome do Usuário",
    "email": "email@example.com"
}
```
- **Erros**:
    
    - `409 Conflict`: Caso o e-mail já esteja em uso.
        
```json
{
    "status": 409,
    "message": "O e-mail informado já está em uso"
}
```

2) #### Rota para login de um usuário e obtenção de um token JWT.


   **POST /auth/login**

`http://localhost:8080/auth/login`

- **Request Body** (JSON):
    
```json
{
    "email": "email@example.com",
    "password": "senha123"
}
```
    
- **Response** (Status: 200 OK):
    
```json
{
    "token": "JWT_token_aqui"
}
```
    
- **Erros**:
    
    - `401 Unauthorized`: Caso o e-mail ou senha estejam incorretos.
        
```json
{
    "status": 401,
    "message": "Usuário ou senha inválidos"
}
```

3) #### Rota para realizar o logout de um usuário. Adiciona o token JWT à lista de blacklists.

     **POST /auth/logout**

`http://localhost:8080/auth/logout`

- **Request Header**:
    
    - `Authorization: Bearer {token}`
        
- **Response** (Status: 204 No Content):
    
    Não há corpo de resposta.
    
___

#### **Usuários**

4) #### Rota para obter o perfil do usuário autenticado. Retorna as informações do usuário atual.


  **GET /users/me**

`http://localhost:8080/users/me`

- **Request Header**:
    
    - `Authorization: Bearer {token}`
        
- **Response** (Status: 200 OK):
    
```json
{
    "id": "UUID-do-usuario",
    "name": "Nome do Usuário",
    "email": "email@example.com"
}
```
    
- **Erros**:
    
    - `401 Unauthorized`: Se o usuário não estiver autenticado.
        
```json
{
    "status": 401,
    "message": "Usuário não autenticado"
}
```

5) #### Rota para buscar um usuário pelo ID.

   **GET /users/{id}**

`http://localhost:8080/users/{UUID}`

- **Request Path**:
    
    - `{id}`: UUID do usuário
        
- **Response** (Status: 200 OK):
    
    ```json
    {
      "id": "UUID-do-usuario",
      "name": "Nome do Usuário",
      "email": "email@example.com"
    }
    ```
    
- **Erros**:
    
    - `404 Not Found`: Se o usuário não for encontrado.
        
        ```json
        {
          "status": 404,
          "message": "Usuário não encontrado"
        }
        ```

6) #### Rota para editar os dados do usuário autenticado.

   **PUT /users/edit**

  `http://localhost:8080/users/edit`

- **Request Body** (JSON):
    
    ```json
    {
      "id": "UUID-do-usuario",
      "name": "Novo Nome",
      "email": "novoemail@example.com",
      "password": "novasenha123"
    }
    ```
    
- **Response** (Status: 200 OK):
    
    ```json
    {
      "id": "UUID-do-usuario",
      "name": "Novo Nome",
      "email": "novoemail@example.com"
    }
    ```
    
- **Erros**:
    
    - `403 Forbidden`: Se o usuário tentar editar dados de outro usuário.
        
        ```json
        {
          "status": 403,
          "message": "Não autorizado a editar este usuário"
        }
        ```

7) ##### Rota para excluir um usuário.

    **POST /users/delete**

 `http://localhost:8080/users/delete-me`

- **Request Body** (JSON):

- **Request Header**:
    
    - `Authorization: Bearer {token}`
        
    
    ```json
    {
      "id": "UUID-do-usuario"
    }
    ```
    
- **Response** (Status: 204 No Content):
    
    Não há corpo de resposta.
    
- **Erros**:
    
    - `404 Not Found`: Se o usuário não for encontrado.
        
        ```json
        {
          "status": 404,
          "message": "Usuário não encontrado"
        }
        ```
___




