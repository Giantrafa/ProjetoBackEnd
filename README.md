# ProjetoBackEnd

API Spring Boot do MedEvent com autenticação por JWT.

## Banco de dados

A aplicação está configurada para usar MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/medevent
spring.datasource.username=root
spring.datasource.password=
```

Esses valores podem ser alterados por variáveis de ambiente:

```txt
DB_URL
DB_USER
DB_PASSWORD
JWT_SECRET
JWT_EXPIRATION_HOURS
```

## Endpoint público

### Login

`POST /auth/login`

```json
{
  "email": "rafael@email.com",
  "senha": "123456"
}
```

Resposta:

```json
{
  "token": "jwt-gerado-pela-api",
  "usuario": {
    "id": 1,
    "nome": "Rafael",
    "email": "rafael@email.com",
    "perfil": "PACIENTE"
  }
}
```

## Como usar o token

Nas rotas protegidas, o front-end deve enviar:

```txt
Authorization: Bearer jwt-gerado-pela-api
```
