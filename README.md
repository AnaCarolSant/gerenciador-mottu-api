# 👩‍💻 Participantes

- Ana Carolina Reis Santana - RM556219
- Letícia Zago de Souza - RM558464
- Celina Alcântara do Carmo - RM558090


# 🏍️ Gerenciador de Motos no Pátio

Sistema RESTful desenvolvido com Spring Boot para gerenciar o fluxo de **motos em pátios**, organizadas em **setores**, com controle de entrada e saída.

---

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot 3.x
  - Spring Web
  - Spring Data JPA
  - Bean Validation
- H2 Database (em memória)
- Lombok
- DTOs
---

## 📦 Entidades e Relacionamentos

- **Pátio**: contém uma lista de setores.
- **Setor**: pertence a um pátio e contém uma lista de motos.
- **Moto**: pertence a um setor, com data de entrada e saída.

### Relacionamentos:
- `Pátio 1:N Setores`
- `Setor 1:N Motos`

---

## 🎯 Funcionalidades

- Criar, listar e consultar pátios
- Criar e listar setores vinculados a um pátio
- Registrar entrada e saída de motos em um setor
- Listar motos por setor ou por pátio
- API limpa com uso de DTOs para evitar dados desnecessários

---

# 📡 Endpoints para Testes - Banco H2

Este projeto utiliza o banco de dados em memória H2.

## 🎯 Console H2
- URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:patio`
- User Name: `SA`
- Password: *(deixe em branco)*

---

## 🏢 PÁTIOS

### 🔹 Listar todos os pátios
```http
GET http://localhost:8080/patios
```

### 🔹 Buscar pátio por ID
```http
GET http://localhost:8080/patios/1
```

### 🔹 Criar novo pátio
```http
POST http://localhost:8080/patios
Content-Type: application/json

{
  "nome": "Pátio Central"
}
```

### 🔹 Atualizar pátio
```http
PUT http://localhost:8080/patios/1
Content-Type: application/json

{
  "nome": "Pátio Atualizado"
}
```

### 🔹 Deletar pátio
```http
DELETE http://localhost:8080/patios/1
```

---

## 🧱 SETORES

### 🔹 Listar todos os setores
```http
GET http://localhost:8080/setores
```

### 🔹 Buscar setor por ID
```http
GET http://localhost:8080/setores/1
```

### 🔹 Criar novo setor
```http
POST http://localhost:8080/setores
Content-Type: application/json

{
  "nome": "Setor A",
  "capacidadeMaxima": 10,
  "patioId": 1
}
```

### 🔹 Atualizar setor
```http
PUT http://localhost:8080/setores/1
Content-Type: application/json

{
  "nome": "Setor A - Atualizado",
  "capacidadeMaxima": 12,
  "patioId": 1
}
```

### 🔹 Deletar setor
```http
DELETE http://localhost:8080/setores/1
```

---

## 🏍️ MOTOS

### ➕ Registrar entrada da moto
```http
POST http://localhost:8080/motos/entrada
Content-Type: application/json

{
  "modelo": "Honda CG 160",
  "iotIdentificador": "IOT123459",
  "setorId": 1
}
```

### 📋 Listar todas as motos
```http
GET http://localhost:8080/motos
```

### 🔍 Buscar moto por ID
```http
GET http://localhost:8080/motos/1
```

### ✏️ Registrar saída da moto
```http
PATH http://localhost:8080/motos/saida/4
```
> **Nota:** O ID da moto é passado como path parameter (`PATH /motos/saida/{id}`), não é necessário enviar corpo na requisição. A saída será registrada no momento da chamada.

### 🟢 Listar motos ativas (no pátio)
```http
GET http://localhost:8080/motos/ativas
```

### ❌ Deletar moto
```http
DELETE http://localhost:8080/motos/1
```
