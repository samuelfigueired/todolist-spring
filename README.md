# ✅ To-Do List API - Spring Boot  

🚀 **Projeto de uma API REST para gerenciamento de tarefas (To-Do List), utilizando Spring Boot, Docker, banco de dados H2 e autenticação básica.**  

---

## 📌 Descrição do Projeto  

Essa API permite que os usuários **cadastrem, atualizem e listem tarefas**, garantindo segurança e autenticação através de um filtro de requisições. O banco de dados utilizado é **H2**, permitindo testes rápidos sem necessidade de configuração adicional.  

---

## 🛠 Tecnologias Utilizadas  

✔ **Java 17** - Linguagem principal do projeto  
✔ **Spring Boot 3** - Framework para desenvolvimento da API  
✔ **Spring Data JPA** - Facilita a integração com o banco de dados  
✔ **H2 Database** - Banco de dados em memória para testes  
✔ **Docker** - Para facilitar a execução e deploy da aplicação  
✔ **Maven** - Gerenciador de dependências  
✔ **BCrypt** - Para criptografia de senhas  
✔ **JWT (JSON Web Token)** - Implementado para autenticação  

---

## 📦 Como Rodar o Projeto?  

### 🔹 1. Clonar o repositório  
git clone https://github.com/seu-usuario/seu-repositorio.git

🔹 2. Acessar o diretório do projeto
cd todolist

🔹 3. Configurar o ambiente
Caso utilize o Docker, você pode rodar o seguinte comando para subir a aplicação:
docker-compose up --build

Caso queira rodar sem Docker, basta usar o Maven:
mvn spring-boot:run

📂 Estrutura do Projeto
📦 todolist
 ┣ 📂 src/main/java/br/com/battcompany/todolist
 ┃ ┣ 📂 errors → Tratamento de exceções
 ┃ ┣ 📂 filter → Filtro para autenticação nas requisições
 ┃ ┣ 📂 task → CRUD de tarefas (Tasks)
 ┃ ┣ 📂 user → Gerenciamento de usuários
 ┃ ┣ 📂 utils → Métodos utilitários
 ┃ ┗ 📜 TodolistApplication.java → Classe principal do projeto
 ┣ 📂 src/main/resources
 ┃ ┣ 📜 application.properties → Configurações do Spring Boot e Banco de Dados H2
 ┣ 📜 pom.xml → Dependências do projeto
 ┗ 📜 Dockerfile → Configuração do container Docker
🚀 Endpoints da API
🔐 Autenticação
Antes de acessar os endpoints de tarefas, é necessário criar um usuário e se autenticar via Basic Auth.


1️⃣ Criar um usuário
POST /users/
Corpo da requisição:
{
    "username": "usuario123",
    "password": "minhaSenhaSegura"
}
📌 Observação: A senha será armazenada de forma segura com BCrypt.


📝 CRUD de Tarefas
2️⃣ Criar uma Tarefa
POST /tasks/
Headers: Authorization (Basic Auth)
Corpo da requisição:
{
    "title": "Estudar Spring Boot",
    "description": "Aprender a criar APIs REST",
    "startAt": "2024-03-01T10:00:00",
    "endAt": "2024-03-05T18:00:00"
}
📌 Validações:

A data de início e término devem ser futuras.
A data de início deve ser menor que a data de término.


3️⃣ Listar Tarefas do Usuário
GET /tasks/
Headers: Authorization (Basic Auth)
Retorno esperado:

[
    {
        "id": "123e4567-e89b-12d3-a456-426614174000",
        "title": "Estudar Spring Boot",
        "description": "Aprender a criar APIs REST",
        "startAt": "2024-03-01T10:00:00",
        "endAt": "2024-03-05T18:00:00"
    }
]


4️⃣ Atualizar uma Tarefa
PUT /tasks/{id}
Headers: Authorization (Basic Auth)
Corpo da requisição:

{
    "title": "Estudar Spring Security",
    "description": "Aprender sobre autenticação e autorização"
}
📌 Validação: Apenas o dono da tarefa pode alterá-la.

🛡 Segurança e Autenticação
Esta API utiliza Basic Authentication, o que significa que você precisa enviar o usuário e senha no cabeçalho da requisição para acessar os endpoints protegidos.

🔑 Exemplo de cabeçalho de autenticação:
Authorization: Basic base64(username:password)
📌 O usuário precisa estar cadastrado previamente no sistema.

🐞 Tratamento de Erros
Caso ocorra um erro na requisição, a API retorna uma mensagem amigável.
Exemplo de erro ao cadastrar uma tarefa com título muito grande:

{
    "error": "A propriedade 'title' deve ter no máximo 50 caracteres."
}
