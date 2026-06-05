cd C:\Users\sergi\Desktop# AutoShop Pro — Backend API
Alunos do Projeto:

Vitor Ferreira

Rafael De Assis



API REST em Spring Boot com autenticação JWT para o sistema de gerenciamento de oficina mecânica.

## Tecnologias

- Java 17 + Spring Boot 4
- Spring Security (stateless, JWT manual HMAC-SHA256)
- Spring Data JPA + PostgreSQL (Supabase)
- Lombok

## Configuração

Crie um arquivo `.env` na raiz do projeto com base no `.env.example`:

```properties
DB_URL=jdbc:postgresql://db.<SEU-PROJECT-REF>.supabase.co:5432/postgres
DB_USER=postgres
DB_PASSWORD=sua-senha-supabase
JWT_SECRET=segredo-longo-e-aleatorio-aqui
JWT_EXPIRATION_HOURS=8
```

## Como rodar

```bash
./mvnw spring-boot:run
```

O servidor sobe em `http://localhost:8080`.

## Perfis de usuário (enum Perfil)

`ADMIN` | `GERENTE` | `MECANICO` | `RECEPCIONISTA` | `CLIENTE`

Novos cadastros recebem o perfil `CLIENTE` por padrão.

---

## Endpoints

### Autenticação (público)

#### `POST /auth/register`
```json
// Request
{ "name": "Rafael", "email": "rafael@email.com", "password": "minimo8chars" }

// Response 201 Created
{ "token": "eyJ...", "user": { "name": "Rafael", "email": "rafael@email.com", "role": "CLIENTE" } }
```

#### `POST /auth/login`
```json
// Request
{ "email": "rafael@email.com", "password": "minimo8chars" }

// Response 200 OK
{ "token": "eyJ...", "user": { "name": "Rafael", "email": "rafael@email.com", "role": "CLIENTE" } }
```

#### `POST /auth/forgot-password`
```json
// Request
{ "email": "rafael@email.com" }

// Response 204 No Content
```

#### `POST /auth/reset-password`
```json
// Request
{ "token": "uuid-do-email", "newPassword": "novasenha123" }

// Response 204 No Content
```

### Usuário autenticado

> Todas as rotas abaixo exigem o header:
> `Authorization: Bearer {token}`

#### `GET /auth/me`
```json
// Response 200 OK
{ "name": "Rafael", "email": "rafael@email.com", "role": "CLIENTE" }
```

#### `GET /usuarios`
```json
// Response 200 OK
[ { "name": "Rafael", "email": "rafael@email.com", "role": "CLIENTE" }, ... ]
```

#### `GET /usuarios/{id}`
```json
// Response 200 OK
{ "name": "Rafael", "email": "rafael@email.com", "role": "CLIENTE" }
```

#### `PUT /usuarios/{id}`
```json
// Request (campos opcionais)
{ "name": "Rafael Silva", "role": "GERENTE" }

// Response 200 OK
{ "name": "Rafael Silva", "email": "rafael@email.com", "role": "GERENTE" }
```

#### `DELETE /usuarios/{id}`
```
// Response 204 No Content
```

---

## Documentação interativa

Com o servidor rodando, acesse:
`http://localhost:8080/swagger-ui.html`


## Módulos gerados

Foram adicionados os módulos do plano da imagem:

- Cliente: `/api/v1/clientes`
- Veículo: `/api/v1/veiculos`
- Serviço: `/api/v1/servicos`
- Peça/Estoque: `/api/v1/pecas`
- Ordem de Serviço: `/api/v1/ordens-servico`
- Dashboard: `/api/v1/dashboard/resumo`
- Strategy de preço: `PADRAO`, `DESCONTO_10` e `URGENCIA_20`

Todos os endpoints acima exigem login JWT, exceto `/auth/login`, `/auth/register`, `/auth/forgot-password` e `/auth/reset-password`.
